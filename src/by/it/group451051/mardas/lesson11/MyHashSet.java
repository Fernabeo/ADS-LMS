package by.it.group451051.mardas.lesson11;

import java.util.*;

public class MyHashSet<E> implements Set<E> {

    private int max_size = 16;
    private Node<E>[] zapis;
    private int size = 0;

    // Внутренний класс для узла списка
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        zapis = (Node<E>[]) new Node[max_size];
    }

    private int getIndex(Object o) {
        return o.hashCode()%zapis.length;
    }

    @Override
    public boolean add(E e) {
        int index=getIndex(e);
        Node<E> vsi=zapis[index];
        while(vsi!=null) {
            if (Objects.equals(e,vsi.data)) return false;
            vsi=vsi.next;
        }
        zapis[index]=new Node<>(e,zapis[index]);
        size++;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("[");
        boolean first=true;
        for (int i=0;i<zapis.length;i++) {
            Node<E> vsi=zapis[i];
            while (true) {
                if (vsi==null) break;
                if (first==false) sb.append(", ");
                sb.append(vsi.data);
                first=false;
                vsi=vsi.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i=0;i<zapis.length;i++) zapis[i]=null;
        size=0;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean remove(Object o) {
        int index=getIndex(o);
        Node<E> vsi=zapis[index];
        Node<E> prev=null;
        while (vsi!=null) {
            if (Objects.equals(o,vsi.data)) {
                if (prev==null) zapis[index]=zapis[index].next;
                else prev.next=vsi.next;
                size--;
                return true;
            }
            prev=vsi;
            vsi=vsi.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index=getIndex(o);
        Node<E> vsi=zapis[index];
        while (vsi!=null) {
            if (Objects.equals(o,vsi.data)) return true;
            vsi=vsi.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }
}
