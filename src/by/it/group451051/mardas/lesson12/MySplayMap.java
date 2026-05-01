package by.it.group451051.mardas.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {

    private class Node {
        Integer key;
        String value;
        Node left,right,parent;
        Node(Integer key,String value,Node parent) {
            this.key=key;
            this.value=value;
            this.parent=parent;
        }
    }
    private Node root;
    private int size=0;

    private void rotate(Node node) {
        Node parent=node.parent;
        Node pparent=parent.parent;
        if (parent.left==node) {
            parent.left=node.right;
            if (node.right!=null) node.right.parent=parent;
            node.right=parent;
        } else {
            parent.right=node.left;
            if (node.left!=null) node.left.parent=parent;
            node.left=parent;
        }
        parent.parent=node;
        node.parent=pparent;
        if (pparent!=null) {
            if (pparent.left==parent) pparent.left=node;
            else pparent.right=node;
        } else {
            root=node;
        }
    }

    private void splay(Node node) {
        while (node.parent!=null) {
            Node parent=node.parent;
            Node pparent=parent.parent;
            if (pparent!=null) {
                if ((pparent.left==parent)==(parent.left==node)) rotate(parent);
                else rotate(node);
            }
            rotate(node);
        }
    }

    private Node find(Integer key) {
        Node vsi=root;
        Node prev=null;
        while (vsi!=null) {
            prev=vsi;
            if (key.equals(vsi.key)) {
                splay(vsi);
                return vsi;
            }
            if (key<vsi.key) vsi=vsi.left; else vsi=vsi.right;
        }
        if (prev!=null) splay(prev);
        return null;
    }

    @Override
    public String put(Integer key,String value) {
        if (root==null) {
            root=new Node(key,value,null);
            size++;
            return null;
        }
        Node node=find(key);
        if (node!=null) {
            String old=node.value;
            node.value=value;
            return old;
        }
        Node newNode=new Node(key,value,null);
        if (key<root.key) {
            newNode.right=root;
            newNode.left=root.left;
            if (root.left!=null) root.left.parent=newNode;
            root.left=null;
        } else {
            newNode.left=root;
            newNode.right=root.right;
            if (root.right!=null) root.right.parent=newNode;
            root.right=null;
        }
        root.parent=newNode;
        root=newNode;
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node node=find((Integer)key);
        if (node==null) return null;
        String value=node.value;
        Node left=node.left;
        Node right=node.right;
        if (left!=null) left.parent=null;
        if (right!=null) right.parent=null;
        if (left==null) root=right;
        else {
            root=left;
            Node max_left=left;
            while (max_left.right!=null) max_left=max_left.right;
            splay(max_left);
            max_left.right=right;
            if (right!=null) right.parent=max_left;
        }
        size--;
        return value;
    }

    @Override
    public String get(Object key) {
        Node node=find((Integer)key);
        if (node!=null) return node.value;
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return find((Integer)key)!=null;
    }

    @Override
    public boolean containsValue(Object value) {
        return checkVal(root,value);
    }

    private boolean checkVal(Node node,Object value) {
        if (node==null) return false;
        if (value==null&&node.value==null) return true;
        if (value!=null&&value.equals(node.value)) return true;
        return checkVal(node.left,value)||checkVal(node.right,value);
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
        root=null;
        size=0;
    }

    @Override
    public Integer firstKey() {
        Node vsi =root;
        while (vsi !=null&& vsi.left!=null) vsi = vsi.left;
        if (vsi ==null) return null;
        return vsi.key;
    }

    @Override
    public Integer lastKey() {
        Node vsi =root;
        while (vsi !=null&&vsi.right!=null) vsi=vsi.right;
        if (vsi==null) return null;
        return vsi.key;
    }

    @Override
    public Integer lowerKey(Integer key) {
        Node vsi=root;
        Node res=null;
        while (vsi!=null) {
            if (vsi.key<key) {
                res=vsi;
                vsi=vsi.right;
            }
            else vsi=vsi.left;
        }
        if (res==null) return null;
        return res.key;
    }

    @Override
    public Integer floorKey(Integer key) {
        Node vsi=find(key);
        if (vsi!=null) return vsi.key;
        return lowerKey(key);
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node vsi=find(key);
        if (vsi!=null) return vsi.key;
        return higherKey(key);
    }

    @Override
    public Integer higherKey(Integer key) {
        Node vsi=root;
        Node res=null;
        while (vsi!=null) {
            if (vsi.key>key) {
                res=vsi;
                vsi=vsi.left;
            }
            else vsi=vsi.right;
        }
        if (res==null) return null;
        return res.key;
    }

    @Override
    public String toString() {
        if (root==null) return "{}";
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

    @Override
    public NavigableMap<Integer, String> headMap(Integer key) {
        MySplayMap map=new MySplayMap();
        fillRange(root,map,null,key);
        return map;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer key) {
        MySplayMap map=new MySplayMap();
        fillRange(root,map,key,null);
        return map;
    }

    private void fillRange(Node node,MySplayMap map,Integer from,Integer to) {
        if (node==null) return;
        fillRange(node.left,map,from,to);
        if ((from==null||node.key>=from)&&(to==null||node.key<to)) map.put(node.key,node.value);
        fillRange(node.right,map,from,to);
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() {
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
