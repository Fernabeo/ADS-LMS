package by.it.group451051.mardas.lesson15;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SourceScannerB {
    static class Item {
        long size;
        String path;
        Item(long size,String path){
            this.size=size;
            this.path=path;
        }
    }

    public static void main(String[]args)throws IOException {
        String src=System.getProperty("user.dir")+File.separator+"src"+File.separator;
        File rootDir=new File(src);
        ArrayList<Item>list=new ArrayList<>();
        findJavaFiles(rootDir,rootDir,list);

        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.size()-1;j++){
                Item a=list.get(j),b=list.get(j+1);
                boolean swap=false;
                if(a.size>b.size)swap=true;
                else if(a.size==b.size&&a.path.compareTo(b.path)>0)swap=true;
                if(swap){
                    list.set(j,b);
                    list.set(j+1,a);
                }
            }
        }
        for(Item item:list)System.out.println(item.size+" "+item.path);
    }

    private static void findJavaFiles(File startDir,File current,ArrayList<Item>list){
        File[]files=current.listFiles();
        if(files==null)return;
        for(File f:files){
            if(f.isDirectory())findJavaFiles(startDir,f,list);
            else if(f.getName().endsWith(".java"))processFile(startDir,f,list);
        }
    }

    private static void processFile(File root,File f,ArrayList<Item>list){
        try{
            byte[]bytes=new byte[(int)f.length()];
            try(FileInputStream fis=new FileInputStream(f)){fis.read(bytes);}
            String content=new String(bytes,StandardCharsets.UTF_8);
            if(content.contains("@Test")||content.contains("org.junit.Test"))return;

            content=content.replaceAll("//.*|/\\*(?s).*?\\*/","");

            int classPos=-1;
            String[]keywords={"class ","interface ","enum ","record "};
            for(String kw:keywords){
                int index=content.indexOf(kw);
                if(index!=-1&&(classPos==-1||index<classPos))classPos=index;
            }
            if(classPos!=-1)content=content.substring(classPos);

            String[]lines=content.split("\n");
            StringBuilder sb=new StringBuilder();
            for(String line:lines){
                if(!line.trim().isEmpty())sb.append(line).append("\n");
            }
            content=sb.toString();

            int start=0;
            while(start<content.length()&&content.charAt(start)<33)start++;
            int end=content.length();
            while(end>start&&content.charAt(end-1)<33)end--;
            if(start>=end)return;
            content=content.substring(start,end);

            String relPath=f.getAbsolutePath().replace(root.getAbsolutePath(),"");
            if(relPath.startsWith(File.separator))relPath=relPath.substring(1);
            list.add(new Item(content.getBytes(StandardCharsets.UTF_8).length,relPath));
        }catch(Exception e){}
    }
}
