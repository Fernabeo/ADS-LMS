package by.it.group451051.mardas.lesson14;

import java.util.*;

public class PointsA {
    static class DSU {
        int[] parent;
        int[] size;
        public DSU(int n) {
            parent=new int[n];
            size=new int[n];
            for(int i=0;i<n;i++) {
                parent[i]=i;
                size[i]=1;
            }
        }

        public int find(int i) {
            if(parent[i]==i) return i;
            return parent[i]=find(parent[i]);
        }

        public void union(int i,int j) {
            int rootI=find(i);
            int rootJ=find(j);
            if(rootI!=rootJ) {
                if(size[rootI]<size[rootJ]) {
                    parent[rootI]=rootJ;
                    size[rootJ]+=size[rootI];
                } else {
                    parent[rootJ]=rootI;
                    size[rootI]+=size[rootJ];
                }
            }
        }
    }

    static class Point {
        double x,y,z;
        Point(double x,double y,double z) {
            this.x=x;
            this.y=y;
            this.z=z;
        }
    }

    private static double getDist(Point p1,Point p2) {
        return Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2)+Math.pow(p1.z-p2.z,2));
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        if(!sc.hasNext())return;
        double d=sc.nextDouble();
        int n=sc.nextInt();
        Point[] points=new Point[n];
        for(int i=0;i<n;i++) {
            points[i]=new Point(sc.nextDouble(),sc.nextDouble(),sc.nextDouble());
        }
        DSU dsu=new DSU(n);
        for(int i=0;i<n;i++) {
            for(int j=i+1;j<n;j++) {
                if(getDist(points[i],points[j])<d) {
                    dsu.union(i,j);
                }
            }
        }
        List<Integer> clusterSizes=new ArrayList<>();
        for(int i=0;i<n;i++) {
            if(dsu.parent[i]==i) {
                clusterSizes.add(dsu.size[i]);
            }
        }
        clusterSizes.sort(Collections.reverseOrder());
        for(int i=0;i<clusterSizes.size();i++) {
            System.out.print(clusterSizes.get(i)+(i==clusterSizes.size()-1?"":" "));
        }
        System.out.println();
    }
}
