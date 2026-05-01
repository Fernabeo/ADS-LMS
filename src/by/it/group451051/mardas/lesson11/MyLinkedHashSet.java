package by.it.group451051.mardas.lesson11;

import java.util.*;

public class MyLinkedHashSet<E> implements Set<E> {

    private int max_size=16;
    private Node<E>[] zapis = (Node<E>[]) new Node[max_size];
    private int size = 0;
    private Node<E> head = null;
    private Node<E> end = null;
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> nextGlobal;
        Node<E> prevGlobal;
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }



    private int getIndex(Object o) {
        return o.hashCode()%zapis.length;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("[");
        Node<E> tek=head;
        while (tek!=null) {
            sb.append(tek.data);
            if (tek.nextGlobal!=null) sb.append(", ");
            tek=tek.nextGlobal;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        int index=getIndex(e);
        Node<E> vsi=zapis[index];
        while (vsi!=null) {
            if (Objects.equals(vsi.data,e)) return false;
            vsi=vsi.next;
        }
        Node<E> newNode=new Node<>(e,zapis[index]);
        zapis[index]=newNode;
        if (head==null) {
            head=end=newNode;
        } else {
            end.nextGlobal=newNode;
            newNode.prevGlobal=end;
            end=newNode;
        }
        size++;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i=0;i<zapis.length;i++) zapis[i]=null;
        head=end=null;
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
            if (Objects.equals(vsi.data,o)) {
                if (prev==null) zapis[index]=vsi.next;
                else prev.next=vsi.next;
                size--;
                vsi=head;
                while (vsi!=null) {
                    if (Objects.equals(vsi.data,o)) {
                        if (vsi==head) head=vsi.nextGlobal;
                        else vsi.prevGlobal.nextGlobal=vsi.nextGlobal;
                        if (vsi==end) end=vsi.prevGlobal;
                        else vsi.nextGlobal.prevGlobal=vsi.prevGlobal;
                        break;
                    }
                    vsi=vsi.nextGlobal;
                }
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
            if (Objects.equals(vsi.data,o)) return true;
            vsi=vsi.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item:c) if (!contains(item)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean res=false;
        for (E item:c) if (add(item)) res=true;
        return res;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean res=false;
        for (Object item:c) if (remove(item)) res=true;
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean res=false;
        Node<E> vsi=head;
        while (vsi!=null) {
            if (!c.contains(vsi.data)) {
                remove(vsi.data);
                res=true;
            }
            vsi=vsi.nextGlobal;
        }
        return res;
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
}
