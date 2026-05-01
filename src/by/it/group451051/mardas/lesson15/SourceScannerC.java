package by.it.group451051.mardas.lesson15;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SourceScannerC {
    static class FileData {
        String path;
        String content;
        FileData(String path,String content){
            this.path=path;
            this.content=content;
        }
    }

    public static void main(String[]args)throws IOException {
        String src=System.getProperty("user.dir")+File.separator+"src"+File.separator;
        File rootDir=new File(src);
        ArrayList<FileData>files=new ArrayList<>();
        scan(rootDir,rootDir,files);

        Collections.sort(files,(a,b)->a.path.compareTo(b.path));

        Map<Integer,List<Integer>>copiesMap=new HashMap<>();
        for(int i=0;i<files.size();i++){
            for(int j=i+1;j<files.size();j++){
                if(isCopy(files.get(i).content,files.get(j).content)){
                    copiesMap.computeIfAbsent(i,k->new ArrayList<>()).add(j);
                }
            }
        }

        for(int i=0;i<files.size();i++){
            if(copiesMap.containsKey(i)){
                System.out.println(files.get(i).path);
                for(int copyIdx:copiesMap.get(i))System.out.println(files.get(copyIdx).path);
            }
        }
    }

    private static boolean isCopy(String s1,String s2){
        if(Math.abs(s1.length()-s2.length())>=10)return false;
        int[]prev=new int[s2.length()+1];
        int[]curr=new int[s2.length()+1];
        for(int j=0;j<=s2.length();j++)prev[j]=j;

        for(int i=1;i<=s1.length();i++){
            curr[0]=i;
            int minInRow=curr[0];
            for(int j=1;j<=s2.length();j++){
                int cost=(s1.charAt(i-1)==s2.charAt(j-1))?0:1;
                curr[j]=Math.min(Math.min(curr[j-1]+1,prev[j]+1),prev[j-1]+cost);
                if(j==1||curr[j]<minInRow)minInRow=curr[j];
            }
            if(minInRow>=10)return false;
            int[]temp=prev;prev=curr;curr=temp;
        }
        return prev[s2.length()]<10;
    }

    private static void scan(File root,File curr,ArrayList<FileData>list){
        File[]fs=curr.listFiles();
        if(fs==null)return;
        for(File f:fs){
            if(f.isDirectory())scan(root,f,list);
            else if(f.getName().endsWith(".java"))process(root,f,list);
        }
    }

    private static void process(File root,File f,ArrayList<FileData>list){
        try{
            byte[]bytes=new byte[(int)f.length()];
            try(FileInputStream fis=new FileInputStream(f)){fis.read(bytes);}
            String text=new String(bytes,StandardCharsets.UTF_8);
            if(text.contains("@Test")||text.contains("org.junit.Test"))return;

            text=text.replaceAll("//.*|/\\*(?s).*?\\*/","");

            int pos=-1;
            String[]kws={"class ","interface ","enum ","record "};
            for(String kw:kws){
                int idx=text.indexOf(kw);
                if(idx!=-1&&(pos==-1||idx<pos))pos=idx;
            }
            if(pos!=-1)text=text.substring(pos);

            text=text.replaceAll("[\\x00-\\x20]+"," ").trim();

            String rp=f.getAbsolutePath().replace(root.getAbsolutePath(),"");
            if(rp.startsWith(File.separator))rp=rp.substring(1);
            list.add(new FileData(rp,text));
        }catch(Exception e){}
    }
}
