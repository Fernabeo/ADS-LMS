package by.it.group451051.mardas.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class SourceScannerC {

    static class FileData {
        String path;
        String content;

        FileData(String path, String content) {
            this.path = path;
            this.content = content;
        }
    }

    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<FileData> files = new ArrayList<>();

        try {
            Files.walk(Paths.get(src))
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(p -> {
                        try {
                            // Обработка MalformedInputException через ISO_8859_1 или замену символов
                            byte[] bytes = Files.readAllBytes(p);
                            String text = new String(bytes, StandardCharsets.UTF_8);

                            if (text.contains("@Test") || text.contains("org.junit.Test")) return;

                            String processed = processText(text);
                            if (!processed.isEmpty()) {
                                files.add(new FileData(p.toString(), processed));
                            }
                        } catch (IOException e) {
                            // Игнорируем проблемные файлы
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Сортировка путей для корректного вывода
        files.sort(Comparator.comparing(f -> f.path));

        Map<String, List<String>> copiesMap = new TreeMap<>();
        Set<String> alreadyCopy = new HashSet<>();

        for (int i = 0; i < files.size(); i++) {
            FileData f1 = files.get(i);
            if (alreadyCopy.contains(f1.path)) continue;

            for (int j = i + 1; j < files.size(); j++) {
                FileData f2 = files.get(j);

                // Оптимизация: если разница длин >= 10, расстояние Левенштейна точно >= 10
                if (Math.abs(f1.content.length() - f2.content.length()) < 10) {
                    if (levenshteinDist(f1.content, f2.content) < 10) {
                        copiesMap.computeIfAbsent(f1.path, k -> new ArrayList<>()).add(f2.path);
                        alreadyCopy.add(f2.path);
                    }
                }
            }
        }

        // Вывод
        copiesMap.forEach((original, copies) -> {
            System.out.println(original);
            copies.stream().sorted().forEach(System.out::println);
        });
    }

    private static String processText(String text) {
        // 1. Удаление package и import
        text = text.replaceAll("(?m)^\\s*package\\s+.*;", "");
        text = text.replaceAll("(?m)^\\s*import\\s+.*;", "");

        // 2. Удаление комментариев за O(n)
        StringBuilder sb = new StringBuilder();
        boolean multiLine = false;
        boolean singleLine = false;
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (multiLine) {
                if (chars[i] == '*' && i + 1 < chars.length && chars[i + 1] == '/') {
                    multiLine = false; i++;
                }
            } else if (singleLine) {
                if (chars[i] == '\n') singleLine = false;
            } else {
                if (chars[i] == '/' && i + 1 < chars.length && chars[i + 1] == '*') {
                    multiLine = true; i++;
                } else if (chars[i] == '/' && i + 1 < chars.length && chars[i + 1] == '/') {
                    singleLine = true; i++;
                } else {
                    sb.append(chars[i]);
                }
            }
        }

        // 3. Коды < 33 -> пробел, сжатие пробелов и trim
        String result = sb.toString();
        char[] resChars = result.toCharArray();
        StringBuilder finalSb = new StringBuilder();
        for (char c : resChars) {
            finalSb.append(c < 33 ? ' ' : c);
        }

        return finalSb.toString().replaceAll("\\s+", " ").trim();
    }

    private static int levenshteinDist(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 0; j <= m; j++) prev[j] = j;

        for (int i = 1; i <= n; i++) {
            curr[0] = i;
            for (int j = 1; j <= m; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            // Оптимизация: если в строке нет ни одного значения < 10, можно прервать
            int minInRow = Integer.MAX_VALUE;
            for(int val : curr) minInRow = Math.min(minInRow, val);
            if (minInRow >= 10) return 11;

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[m];
    }
}