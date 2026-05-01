package by.it.group451051.mardas.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {
    private E[] elements=(E[]) new Object[10];
    private int size=0;

    public void sizex2() {
        E[] newElements=(E[]) new Object[size*2];
        System.arraycopy(elements,0,newElements,0,size);
        elements=newElements;
    }

    public void siftup(int index) {
        int parent;
        E uzel=elements[index];
        while (index>0) {
            parent=(index+1)/2-1;
            if (((Comparable<? super E>)uzel).compareTo(elements[parent])<0) {
                elements[index]=elements[parent];
                index=parent;
            }
            else break;
        }
        elements[index]=uzel;
    }

    public void siftdown(int index) {
        int potomok_left,potomok_right,potomok;
        E uzel=elements[index];
        while (index<size/2) {
            potomok_left=index*2+1;
            potomok_right=potomok_left+1;
            if (potomok_right<size&&((Comparable<? super E>)elements[potomok_right]).compareTo(elements[potomok_left])<0) potomok=potomok_right;
            else potomok=potomok_left;
            if (((Comparable<? super E>)uzel).compareTo(elements[potomok])<0) break;
            elements[index]=elements[potomok];
            index=potomok;
        }
        elements[index]=uzel;
    }

    @Override
    public String toString() {
        StringBuilder str=new StringBuilder("[");
        for (int i=0;i<size;i++) {
            str.append(elements[i]);
            if (i<size-1) str.append(", ");
        }
        str.append("]");
        return str.toString();
    }

    @Override
    public boolean add(E e) {
        if (size==elements.length) sizex2();
        elements[size++]=e;
        siftup(size-1);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i=0;i<size;i++) elements[i]=null;
        size=0;
    }

    @Override
    public E remove() {
        if (size==0) return null;
        E value=elements[0];
        elements[0]=elements[--size];
        elements[size]=null;
        siftdown(0);
        return value;
    }

    @Override
    public boolean contains(Object o) {
        for (int i=0;i<size;i++) if (o.equals(elements[i])) return true;
        return false;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E peek() {
        return elements[0];
    }

    @Override
    public E element() {
        return peek();
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item:c) if (!contains(item)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) return false;
        for (E item:c) add(item);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int oldSize=size;
        if (c.isEmpty()) return false;
        int kol=0;
        for (int i=0;i<size;i++) if (!c.contains(elements[i])) elements[kol++]=elements[i];
        size=kol;
        for (int i=kol/2;i>=0;i--) siftdown(i);
        return oldSize!=size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int kol=0;
        for (int i=0;i<size;i++) if (c.contains(elements[i])) elements[kol++]=elements[i];
        size=kol;
        for (int i=kol/2;i>=0;i--) siftdown(i);
        return true;
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
    public boolean remove(Object o) {
        return false;
    }
}
