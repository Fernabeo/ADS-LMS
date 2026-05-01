package by.it.group451051.mardas.lesson13;

import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Карта для хранения списков смежности: Вершина -> Список соседей
        Map<String, List<String>> adj = new TreeMap<>();
        // Карта для хранения степени захода (in-degree) каждой вершины
        Map<String, Integer> inDegree = new TreeMap<>();

        // Парсинг строки вида: 0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            String from = parts[0];
            String to = parts[1];

            adj.putIfAbsent(from, new ArrayList<>());
            adj.putIfAbsent(to, new ArrayList<>());
            adj.get(from).add(to);

            inDegree.putIfAbsent(from, 0);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        }

        // Приоритетная очередь для лексикографического порядка
        PriorityQueue<String> queue = new PriorityQueue<>();
        for (String node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                queue.add(node);
            }
        }

        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            result.append(curr).append(" ");

            List<String> neighbors = adj.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        System.out.println(result.toString().trim());
    }
}
