package by.it.group451051.mardas.lesson13;

import java.util.*;

public class GraphB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input = scanner.nextLine();

        // Построение списка смежности
        Map<String, List<String>> adj = new HashMap<>();
        Set<String> nodes = new HashSet<>();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            String from = parts[0];
            String to = parts[1];
            adj.putIfAbsent(from, new ArrayList<>());
            adj.get(from).add(to);
            nodes.add(from);
            nodes.add(to);
        }

        // Множества для отслеживания посещенных вершин и стека рекурсии
        Set<String> visited = new HashSet<>();
        Set<String> recursionStack = new HashSet<>();

        boolean hasCycle = false;
        for (String node : nodes) {
            if (isCyclic(node, adj, visited, recursionStack)) {
                hasCycle = true;
                break;
            }
        }

        System.out.println(hasCycle ? "yes" : "no");
    }

    private static boolean isCyclic(String node, Map<String, List<String>> adj,
                                    Set<String> visited, Set<String> recursionStack) {
        if (recursionStack.contains(node)) return true;
        if (visited.contains(node)) return false;

        visited.add(node);
        recursionStack.add(node);

        List<String> neighbors = adj.get(node);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (isCyclic(neighbor, adj, visited, recursionStack)) return true;
            }
        }

        recursionStack.remove(node);
        return false;
    }
}
