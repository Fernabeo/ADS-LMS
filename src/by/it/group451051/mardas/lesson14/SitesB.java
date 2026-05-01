package by.it.group451051.mardas.lesson14;

import java.util.*;

public class SitesB {
    static class DSU {
        int[] parent;
        int[] rank;
        int[] size;

        public DSU(int n) {
            parent=new int[n];
            rank=new int[n];
            size=new int[n];
            for(int i=0;i<n;i++) {
                parent[i]=i;
                rank[i]=0;
                size[i]=1;
            }
        }

        public int find(int i) {
            if(parent[i]==i)return i;
            return parent[i]=find(parent[i]); // Эвристика 2: сжатие пути
        }

        public void union(int i,int j) {
            int rootI=find(i);
            int rootJ=find(j);
            if(rootI!=rootJ) {
                // Эвристика 1: объединение по рангу
                if(rank[rootI]<rank[rootJ]) {
                    parent[rootI]=rootJ;
                    size[rootJ]+=size[rootI];
                } else {
                    parent[rootJ]=rootI;
                    size[rootI]+=size[rootJ];
                    if(rank[rootI]==rank[rootJ])rank[rootI]++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Map<String,Integer> siteToId=new HashMap<>();
        List<String[]> connections=new ArrayList<>();
        int idCounter=0;
        while(sc.hasNextLine()) {
            String line=sc.nextLine();
            if(line.equals("end"))break;
            String[] sites=line.split("\\+");
            for(String s:sites) {
                if(!siteToId.containsKey(s)) {
                    siteToId.put(s,idCounter++);
                }
            }
            connections.add(sites);
        }
        DSU dsu=new DSU(idCounter);
        for(String[] pair:connections) {
            dsu.union(siteToId.get(pair[0]),siteToId.get(pair[1]));
        }
        List<Integer> clusterSizes=new ArrayList<>();
        for(int i=0;i<idCounter;i++) {
            if(dsu.parent[i]==i) {
                clusterSizes.add(dsu.size[i]);
            }
        }
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);
        for(int i=0;i<clusterSizes.size();i++) {
            System.out.print(clusterSizes.get(i));
            if (i!=clusterSizes.size()-1) System.out.print(" ");
        }
        System.out.println();
    }
}
