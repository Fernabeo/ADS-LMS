package by.it.group451051.mardas.lesson13;

import java.util.*;

public class GraphC {
    private static class Vertex {
        public String label;
        public boolean wasVisited;
        public Vertex(String lab) {
            label=lab;
            wasVisited=false;
        }
    }
    private final int MAX_VERTS=100;
    private Vertex[] vertexList;
    private int[][] adjMat;
    private int[][] revMat;
    private int nVerts;
    private String[] order;
    private int orderIdx;
    public GraphC() {
        vertexList=new Vertex[MAX_VERTS];
        adjMat=new int[MAX_VERTS][MAX_VERTS];
        revMat=new int[MAX_VERTS][MAX_VERTS];
        nVerts=0;
        order=new String[MAX_VERTS];
        orderIdx=0;
    }

    private int getIndex(String lab) {
        for(int i=0;i<nVerts;i++)
            if(vertexList[i].label.equals(lab)) return i;
        return -1;
    }

    public void addVertex(String lab) {
        if(getIndex(lab)==-1)
            vertexList[nVerts++]=new Vertex(lab);
    }

    public void addEdge(String start,String end) {
        int s=getIndex(start);
        int e=getIndex(end);
        if(s!=-1&&e!=-1) {
            adjMat[s][e]=1;
            revMat[e][s]=1;
        }
    }

    private void sortVertices() {
        for(int i=0;i<nVerts-1;i++)
            for(int j=i+1;j<nVerts;j++)
                if(vertexList[i].label.compareTo(vertexList[j].label)>0) {
                    Vertex temp=vertexList[i];
                    vertexList[i]=vertexList[j];
                    vertexList[j]=temp;
                }
    }

    private void dfs1(int v) {
        vertexList[v].wasVisited=true;
        for(int j=0;j<nVerts;j++)
            if(adjMat[v][j]==1&&!vertexList[j].wasVisited)
                dfs1(j);
        order[orderIdx++]=vertexList[v].label;
    }

    private void dfs2(int v,PriorityQueue<String> component) {
        vertexList[v].wasVisited=true;
        component.add(vertexList[v].label);
        for(int j=0;j<nVerts;j++)
            if(revMat[v][j]==1&&!vertexList[j].wasVisited)
                dfs2(j,component);
    }

    public void solve() {
        for(int i=0;i<nVerts;i++)
            if(!vertexList[i].wasVisited)dfs1(i);
        for(int i=0;i<nVerts;i++)vertexList[i].wasVisited=false;
        for(int i=orderIdx-1;i>=0;i--) {
            int v=getIndex(order[i]);
            if(!vertexList[v].wasVisited) {
                PriorityQueue<String> component=new PriorityQueue<>();
                dfs2(v,component);
                while(!component.isEmpty())System.out.print(component.poll());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        if(!sc.hasNextLine())return;
        String input=sc.nextLine();
        GraphC graph=new GraphC();
        String[] edges=input.split(", ");
        for(String edge:edges) {
            String[] parts=edge.split("->");
            graph.addVertex(parts[0].trim());
            graph.addVertex(parts[1].trim());
        }
        graph.sortVertices();
        for(String edge:edges) {
            String[] parts=edge.split("->");
            graph.addEdge(parts[0].trim(),parts[1].trim());
        }
        graph.solve();
    }
}
