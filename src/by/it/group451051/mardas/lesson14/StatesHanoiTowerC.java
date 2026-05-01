package by.it.group451051.mardas.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    static int[] parent;
    static int[] sizeDSU;
    static int[] maxHeights;
    static int stepCount;
    static int[] currentRodHeights = new int[3];

    static int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]);
    }

    static void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ) {
            if (sizeDSU[rootI] < sizeDSU[rootJ]) {
                parent[rootI] = rootJ;
                sizeDSU[rootJ] += sizeDSU[rootI];
            } else {
                parent[rootJ] = rootI;
                sizeDSU[rootI] += sizeDSU[rootJ];
            }
        }
    }

    static void hanoi(int n, int from, int to, int aux) {
        if (n == 0) return;
        hanoi(n - 1, from, aux, to);

        currentRodHeights[from]--;
        currentRodHeights[to]++;

        int max = currentRodHeights[0];
        if (currentRodHeights[1] > max) max = currentRodHeights[1];
        if (currentRodHeights[2] > max) max = currentRodHeights[2];

        maxHeights[stepCount++] = max;

        hanoi(n - 1, aux, to, from);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        int totalMoves = (1 << n) - 1;
        parent = new int[totalMoves];
        sizeDSU = new int[totalMoves];
        maxHeights = new int[totalMoves];
        stepCount = 0;

        for (int i = 0; i < totalMoves; i++) {
            parent[i] = i;
            sizeDSU[i] = 1;
        }

        currentRodHeights[0] = n;
        currentRodHeights[1] = 0;
        currentRodHeights[2] = 0;

        hanoi(n, 0, 1, 2);

        // ОПТИМИЗАЦИЯ: Группировка за O(M) вместо O(M^2)
        int[] firstOccur = new int[n + 1];
        for (int i = 0; i <= n; i++) firstOccur[i] = -1;

        for (int i = 0; i < totalMoves; i++) {
            int h = maxHeights[i];
            if (firstOccur[h] == -1) {
                firstOccur[h] = i;
            } else {
                union(i, firstOccur[h]);
            }
        }

        int[] res = new int[n + 1]; // Количество корней не превысит N+1
        int count = 0;
        for (int i = 0; i < totalMoves; i++) {
            if (parent[i] == i) res[count++] = sizeDSU[i];
        }

        // Сортировка малого массива (N элементов)
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (res[j] > res[j + 1]) {
                    int t = res[j];
                    res[j] = res[j + 1];
                    res[j + 1] = t;
                }
            }
        }

        for (int i = 0; i < count; i++) {
            System.out.print(res[i] + (i == count - 1 ? "" : " "));
        }
    }
}
