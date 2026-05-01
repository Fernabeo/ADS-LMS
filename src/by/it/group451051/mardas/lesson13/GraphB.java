package by.it.group451051.mardas.lesson13;

import java.util.*;

public class GraphB {
    private static final int MAX_VERTS=100;
    private static String[] vertexList=new String[MAX_VERTS];
    private static int[][] adjMat=new int[MAX_VERTS][MAX_VERTS];
    private static int nVerts=0;

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input=scanner.nextLine();
        String[] mas_edges=input.split(", ");
        for (String edge:mas_edges) {
            String[] parts=edge.split(" -> ");
            addVertex(parts[0]);
            addVertex(parts[1]);
        }
        Arrays.sort(vertexList,0,nVerts);
        for (String edge:mas_edges) {
            String[] parts=edge.split(" -> ");
            addEdge(parts[0],parts[1]);
        }
        boolean result=false;
        int vsi=nVerts;
        for (int i=0;i<vsi;i++) {
            int currentVertex=noSuccessors();
            if (currentVertex==-1) {
                result=true;
                break;
            }
            deleteVertex(currentVertex);
        }
        if (result==false) System.out.println("no");
        else System.out.println("yes");
    }

    private static void addVertex(String lab) {
        for (int i=0;i<nVerts;i++) {
            if (vertexList[i].equals(lab)) return;
        }
        vertexList[nVerts++]=lab;
    }

    private static void addEdge(String start,String end) {
        int s=-1,e=-1;
        for (int i=0;i<nVerts;i++) {
            if (vertexList[i].equals(start)) s=i;
            if (vertexList[i].equals(end)) e=i;
        }
        if (s!=-1&&e!=-1) adjMat[s][e]=1;
    }

    private static int noSuccessors() {
        for (int row=0;row<nVerts;row++) {
            boolean isEdge=false;
            for (int col=0;col<nVerts;col++) {
                if (adjMat[row][col]>0) {
                    isEdge=true;
                    break;
                }
            }
            if (!isEdge) return row;
        }
        return -1;
    }

    private static void deleteVertex(int delVert) {
        if (delVert!=nVerts-1) {
            for (int j=delVert;j<nVerts-1;j++)
                vertexList[j]=vertexList[j+1];
            for (int row=delVert;row<nVerts-1;row++)
                moveRowUp(row,nVerts);
            for (int col=delVert;col<nVerts-1;col++)
                moveColLeft(col,nVerts-1);
        }
        nVerts--;
    }

    private static void moveRowUp(int row,int length) {
        for (int col=0;col<length;col++)
            adjMat[row][col]=adjMat[row+1][col];
    }

    private static void moveColLeft(int col,int length) {
        for (int row=0;row<length;row++)
            adjMat[row][col]=adjMat[row][col+1];
    }
}
