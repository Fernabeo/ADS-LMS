package by.it.group451051.mardas.lesson11;

import java.util.*;

public class MyTreeSet<E> implements Set<E> {

    private E[] elements = (E[]) new Object[10];
    private int size = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i=0;i<size;i++) {
            sb.append(elements[i]);
            if (i<size-1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
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
        for (int i=0;i<size;i++) elements[i]=null;
        size=0;
    }

    private int getIndex(Object o) {
        int low=0;
        int top=size-1;
        Comparable<? super E>key=(Comparable<? super E>)o;
        while (low<=top) {
            int mid=(low+top)/2;
            int cmp=key.compareTo(elements[mid]);
            if (cmp>0) low=mid+1;
            else if (cmp<0) top=mid-1;
            else return mid;
        }
        return -(low+1);
    }

    @Override
    public boolean contains(Object o) {
        return getIndex(o)>=0;
    }

    @Override
    public boolean add(E e) {
        int index=getIndex(e);
        if (index>=0) return false;
        int num_vstav=-(index+1);
        if (size==elements.length) {
            elements=Arrays.copyOf(elements,elements.length*2);
        }
        System.arraycopy(elements,num_vstav,elements,num_vstav+1,size-num_vstav);
        elements[num_vstav]=e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index=getIndex(o);
        if (index<0) return false;
        int num_remove=size-index-1;
        if (num_remove>0) {
            System.arraycopy(elements,index+1,elements,index,num_remove);
        }
        elements[--size]=null;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean res=false;
        for (E item:c) if (add(item)) res=true;
        return res;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item:c) if (!contains(item)) return false;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean res=false;
        for (Object item:c) if (remove(item)) res=true;
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int oldSize=size;
        int kol=0;
        for (int i=0;i<size;i++) {
            if (c.contains(elements[i])) {
                elements[kol++]=elements[i];
            }
        }
        for (int i=kol;i<size;i++) elements[i]=null;
        size=kol;
        return size!=oldSize;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }
}
