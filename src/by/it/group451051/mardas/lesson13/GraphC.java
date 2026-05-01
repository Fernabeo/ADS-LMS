package by.it.group451051.mardas.lesson13;

import java.util.*;

public class GraphC {
    static Map<String, List<String>> adj = new TreeMap<>();
    static Map<String, List<String>> rev = new TreeMap<>();
    static List<String> order = new ArrayList<>();
    static Set<String> visited = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input = scanner.nextLine();

        // Парсинг графа
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            String from = parts[0].trim();
            String to = parts[1].trim();

            adj.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            rev.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
            adj.putIfAbsent(to, new ArrayList<>());
            rev.putIfAbsent(from, new ArrayList<>());
        }

        // 1. Прямой DFS для определения порядка завершения
        for (String node : adj.keySet()) {
            if (!visited.contains(node)) dfs1(node);
        }

        // 2. Обратный DFS по инвертированному графу
        Collections.reverse(order);
        visited.clear();
        for (String node : order) {
            if (!visited.contains(node)) {
                PriorityQueue<String> component = new PriorityQueue<>();
                dfs2(node, component);

                // Вывод компонента в алфавитном порядке без пробелов
                StringBuilder sb = new StringBuilder();
                while (!component.isEmpty()) sb.append(component.poll());
                System.out.println(sb);
            }
        }
    }

    static void dfs1(String v) {
        visited.add(v);
        for (String neighbor : adj.get(v)) {
            if (!visited.contains(neighbor)) dfs1(neighbor);
        }
        order.add(v);
    }

    static void dfs2(String v, PriorityQueue<String> component) {
        visited.add(v);
        component.add(v);
        for (String neighbor : rev.get(v)) {
            if (!visited.contains(neighbor)) dfs2(neighbor, component);
        }
    }
}
