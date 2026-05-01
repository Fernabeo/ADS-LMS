package by.it.group451051.mardas.lesson13;

import java.util.*;

public class GraphA {
    private static final int MAX_VERTS=100;
    private static String[] vertexList=new String[MAX_VERTS];
    private static int[][] adjMat=new int[MAX_VERTS][MAX_VERTS];
    private static int nVerts=0;
    private static String[] sortedArray=new String[MAX_VERTS];

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine();
        String[] mas_edges=input.split(", ");
        for (String edge:mas_edges) {
            String[] str=edge.split(" -> ");
            addVertex(str[0]);
            addVertex(str[1]);
            addEdge(str[0],str[1]);
        }
        int orig_nVerts=nVerts;
        while (nVerts>0) {
            int currentVertex=noSuccessors();
            if (currentVertex==-1) {
                System.out.println("Error: Graph has cycles");
                return;
            }
            sortedArray[nVerts-1]=vertexList[currentVertex];
            deleteVertex(currentVertex);
        }
        for (int j=0;j<orig_nVerts;j++) {
            System.out.print(sortedArray[j]);
            if (j<orig_nVerts-1) System.out.print(" ");
        }
        System.out.println();
    }

    private static void addVertex(String lab) {
        for (int i=0;i<nVerts;i++) {
            if (vertexList[i].equals(lab)) return;
        }
        vertexList[nVerts++]=lab;
        Arrays.sort(vertexList,0,nVerts);
    }

    private static void addEdge(String start,String end) {
        int s=-1,e=-1;
        for (int i=0;i<nVerts;i++) {
            if (vertexList[i].equals(start)) s=i;
            if (vertexList[i].equals(end)) e=i;
        }
        adjMat[s][e] = 1;
    }

    private static int noSuccessors() {
        for (int row=nVerts-1;row>=0;row--) {
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
