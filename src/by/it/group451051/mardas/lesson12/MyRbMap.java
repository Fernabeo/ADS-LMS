package by.it.group451051.mardas.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private static final boolean RED=true;
    private static final boolean BLACK=false;
    private class Node {
        Integer key;
        String value;
        Node left,right,parent;
        boolean color;
        Node(Integer key,String value,boolean color,Node parent) {
            this.key=key;
            this.value=value;
            this.color=color;
            this.parent=parent;
        }
    }
    private Node root;
    private int size=0;

    private void leftRotate(Node node) {
        Node node1=node.right;
        node.right=node1.left;
        if (node1.left!=null) node1.left.parent=node;
        node1.parent=node.parent;
        if (node.parent==null) root=node1;
        else if (node==node.parent.left) node.parent.left=node1;
        else node.parent.right=node1;
        node1.left=node;
        node.parent=node1;
    }

    private void rightRotate(Node node) {
        Node node1=node.left;
        node.left=node1.right;
        if (node1.right!=null) node1.right.parent=node;
        node1.parent=node.parent;
        if (node.parent==null) root=node1;
        else if (node==node.parent.left) node.parent.left=node1;
        else node.parent.right=node1;
        node1.right=node;
        node.parent=node1;
    }

    @Override
    public String put(Integer key,String value) {
        if (root==null) {
            root=new Node(key,value,BLACK,null);
            size++;
            return null;
        }
        Node n=root;
        Node parent;
        do {
            parent=n;
            if (key<n.key) n=n.left;
            else if (key>n.key) n=n.right;
            else {
                String old=n.value;
                n.value=value;
                return old;
            }
        } while (n!=null);
        Node newNode=new Node(key,value,RED,parent);
        if (key<parent.key) parent.left=newNode;
        else parent.right=newNode;
        newNode.color=RED;
        while (newNode!=null&&newNode!=root&&newNode.parent.color==RED) {
            if (newNode.parent==newNode.parent.parent.left) {
                Node node=newNode.parent.parent.right;
                if (node!=null&&node.color==RED) {
                    newNode.parent.color=BLACK;
                    node.color=BLACK;
                    newNode.parent.parent.color=RED;
                    newNode=newNode.parent.parent;
                } else {
                    if (newNode==newNode.parent.right) {
                        newNode=newNode.parent;
                        leftRotate(newNode);
                    }
                    newNode.parent.color=BLACK;
                    newNode.parent.parent.color=RED;
                    rightRotate(newNode.parent.parent);
                }
            } else {
                Node node=newNode.parent.parent.left;
                if (node!=null&&node.color==RED) {
                    newNode.parent.color=BLACK;
                    node.color=BLACK;
                    newNode.parent.parent.color=RED;
                    newNode=newNode.parent.parent;
                } else {
                    if (newNode==newNode.parent.left) {
                        newNode=newNode.parent;
                        rightRotate(newNode);
                    }
                    newNode.parent.color=BLACK;
                    newNode.parent.parent.color=RED;
                    leftRotate(newNode.parent.parent);
                }
            }
        }
        root.color=BLACK;
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node node=findNode((Integer)key);
        if (node==null) return null;
        String old=node.value;
        deleteNode(node);
        size--;
        return old;
    }

    private void deleteNode(Node node) {
        if (node.left!=null&&node.right!=null) {
            Node node1=node.right;
            while (node1.left!=null) node1=node1.left;
            node.key=node1.key;
            node.value=node1.value;
            node=node1;
        }
        Node zamena;
        if (node.left!=null) zamena=node.left;
        else zamena=node.right;
        if (zamena!=null) {
            zamena.parent=node.parent;
            if (node.parent==null) root=zamena;
            else if (node==node.parent.left) node.parent.left=zamena;
            else node.parent.right=zamena;
        } else if (node.parent==null) {
            root=null;
        } else {
            if (node==node.parent.left) node.parent.left=null;
            else node.parent.right=null;
        }
    }

    @Override
    public String get(Object key) {
        Node node=findNode((Integer)key);
        if (node!=null) return node.value;
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key)!=null;
    }

    @Override
    public boolean containsValue(Object value) {
        return checkValue(root,value);
    }

    private boolean checkValue(Node node, Object val) {
        if (node==null) return false;
        if (val==null) {
            if (node.value==null) return true;
        } else if (val.equals(node.value)) {
            return true;
        }
        return checkValue(node.left,val)||checkValue(node.right,val);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Integer firstKey() {
        Node node=root;
        while (node.left!=null) node=node.left;
        return node.key;
    }

    @Override
    public Integer lastKey() {
        Node node=root;
        while (node.right!=null) node=node.right;
        return node.key;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer key) {
        MyRbMap map=new MyRbMap();
        fillRange(root,map,null,key);
        return map;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer key) {
        MyRbMap map=new MyRbMap();
        fillRange(root,map,key,null);
        return map;
    }

    private void fillRange(Node node,MyRbMap map,Integer from,Integer to) {
        if (node==null) return;
        fillRange(node.left,map,from,to);
        if ((from==null||node.key>=from)&&(to==null||node.key<to)) map.put(node.key,node.value);
        fillRange(node.right,map,from,to);
    }

    @Override
    public String toString() {
        if (root == null) return "{}";
        StringBuilder sb=new StringBuilder("{");
        toString(root,sb);
        return sb.substring(0,sb.length()-2)+"}";
    }

    private void toString(Node node,StringBuilder sb) {
        if (node==null) return;
        toString(node.left,sb);
        sb.append(node.key).append("=").append(node.value).append(", ");
        toString(node.right,sb);
    }

    private Node findNode(Integer key) {
        Node vsi=root;
        while (vsi!=null) {
            if (key.equals(vsi.key)) return vsi;
            if (key<vsi.key) vsi=vsi.left; else vsi=vsi.right;
        }
        return null;
    }

    @Override public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer from, Integer to) {
        return null;
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

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }
}