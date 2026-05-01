package by.it.group451051.mardas.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {

    private class Node {
        Integer key;
        String value;
        Node left,right;
        int height;

        Node(Integer key,String value) {
            this.key=key;
            this.value=value;
            this.height=1;
        }
    }

    private Node root;
    private int size=0;

    private int height(Node node) {
        if (node==null) return 0;
        else return node.height;
    }

    private int getBalance(Node node) {
        if (node==null) return 0;
        else return height(node.left)-height(node.right);
    }

    private Node rightRotate(Node node) {
        Node node1=node.left;
        Node node2=node1.right;
        node1.right=node;
        node.left=node2;
        node.height=Math.max(height(node.left),height(node.right))+1;
        node1.height=Math.max(height(node1.left),height(node1.right))+1;
        return node1;
    }

    private Node leftRotate(Node node) {
        Node node1=node.right;
        Node node2=node1.left;
        node1.left=node;
        node.right=node2;
        node.height=Math.max(height(node.left),height(node.right))+1;
        node1.height=Math.max(height(node1.left),height(node1.right))+1;
        return node1;
    }

    @Override
    public String get(Object key) {
        Node vsi=root;
        while (vsi!=null) {
            if (vsi.key.equals(key)) return vsi.value;
            if ((Integer) key<vsi.key) vsi=vsi.left;
            else vsi=vsi.right;
        }
        return null;
    }

    @Override
    public String put(Integer key,String value) {
        String prev_value=get(key);
        root=put(root,key,value);
        if (prev_value==null) size++;
        return prev_value;
    }

    private Node put(Node node,Integer key,String value) {
        if (node==null) return new Node(key,value);
        if (key<node.key) node.left=put(node.left,key,value);
        else if (key>node.key) node.right=put(node.right,key,value);
        else {
            node.value=value;
            return node;
        }
        node.height=1+Math.max(height(node.left),height(node.right));
        int balance=getBalance(node);
        if (balance>1&&key<node.left.key) return rightRotate(node);
        if (balance<-1&&key>node.right.key) return leftRotate(node);
        if (balance>1&&key>node.left.key) {
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance<-1&&key<node.right.key) {
            node.right=rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    @Override
    public String remove(Object key) {
        String prev_value=get((Integer)key);
        if (prev_value!=null) {
            root=remove(root,(Integer)key);
            size--;
        }
        return prev_value;
    }

    private Node remove(Node root,Integer key) {
        if (root==null) return root;
        if (key<root.key) root.left=remove(root.left,key);
        else if (key>root.key) root.right=remove(root.right,key);
        else {
            if ((root.left==null)||(root.right==null)) {
                Node vsi;
                if (root.left!=null) vsi=root.left;
                else vsi=root.right;
                if (vsi==null) root=null;
                else root=vsi;
            } else {
                Node vsi=minValueNode(root.right);
                root.key=vsi.key;
                root.value=vsi.value;
                root.right=remove(root.right,vsi.key);
            }
        }
        if (root==null) return root;
        root.height=Math.max(height(root.left),height(root.right))+1;
        int balance=getBalance(root);
        if (balance>1&&getBalance(root.left)>=0) return rightRotate(root);
        if (balance>1&&getBalance(root.left)<0) {
            root.left=leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance<-1&&getBalance(root.right)<=0) return leftRotate(root);
        if (balance<-1&&getBalance(root.right)>0) {
            root.right=rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    private Node minValueNode(Node node) {
        Node vsi=node;
        while (vsi.left!=null) vsi=vsi.left;
        return vsi;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key)!=null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root=null;
        size=0;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public String toString() {
        if (root==null) return "{}";
        StringBuilder sb=new StringBuilder("{");
        toString(root,sb);
        sb.delete(sb.length()-2,sb.length());
        sb.append("}");
        return sb.toString();
    }

    private void toString(Node node,StringBuilder sb) {
        if (node!=null) {
            toString(node.left,sb);
            sb.append(node.key).append("=").append(node.value).append(", ");
            toString(node.right,sb);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }
}
