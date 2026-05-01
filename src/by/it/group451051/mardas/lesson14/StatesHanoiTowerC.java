package by.it.group451051.mardas.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
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

    static int stepIdx;
    static int[] maxHeights;
    static int[] currentHeights;

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        if(!sc.hasNextInt()) return;
        int n=sc.nextInt();
        int totalSteps=(1<<n)-1;
        stepIdx=0;
        maxHeights=new int[totalSteps];
        currentHeights=new int[3];
        currentHeights[0]=n;
        currentHeights[1]=0;
        currentHeights[2]=0;

        solveHanoi(n,0,1,2);

        DSU dsu=new DSU(totalSteps);
        int[] lastPos=new int[n+1];
        for(int i=0;i<=n;i++)lastPos[i]=-1;

        for(int i=0;i<totalSteps;i++) {
            int h=maxHeights[i];
            if(lastPos[h]!=-1)dsu.union(i,lastPos[h]);
            lastPos[h]=i;
        }

        int[] resSizes=new int[totalSteps];
        int count=0;
        for(int i=0;i<totalSteps;i++) {
            if(dsu.parent[i]==i)resSizes[count++]=dsu.size[i];
        }

        for(int i=0;i<count-1;i++) {
            for(int j=0;j<count-i-1;j++) {
                if(resSizes[j]>resSizes[j+1]) {
                    int temp=resSizes[j];
                    resSizes[j]=resSizes[j+1];
                    resSizes[j+1]=temp;
                }
            }
        }

        for(int i=0;i<count;i++) {
            System.out.print(resSizes[i]+(i==count-1?"":" "));
        }
        System.out.println();
    }

    static void solveHanoi(int n,int from,int to,int aux) {
        if(n==0)return;
        solveHanoi(n-1,from,aux,to);
        currentHeights[from]--;
        currentHeights[to]++;
        int m=currentHeights[0];
        if(currentHeights[1]>m)m=currentHeights[1];
        if(currentHeights[2]>m)m=currentHeights[2];
        maxHeights[stepIdx++]=m;
        solveHanoi(n-1,aux,to,from);
    }
}
