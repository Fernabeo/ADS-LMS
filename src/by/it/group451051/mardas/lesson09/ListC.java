package by.it.group451051.mardas.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {
    private E[] elements=(E[]) new Object[10];
    private int size=0;
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
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
        if (size==elements.length) {
            E[] newElements=(E[]) new Object[elements.length*2];
            System.arraycopy(elements,0,newElements,0,size);
            elements=newElements;
        }
        elements[size++]=e;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index<0||index>=size) return null;
        E removed=elements[index];
        System.arraycopy(elements,index+1,elements,index,size-index-1);
        elements[--size]=null;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (size==elements.length) {
            E[] newElements=(E[])new Object[elements.length*2];
            System.arraycopy(elements,0,newElements,0,size);
            elements=newElements;
        }
        System.arraycopy(elements,index,elements,index+1,size-index);
        size++;
        elements[index]=element;
    }

    @Override
    public boolean remove(Object o) {
        int index=indexOf(o);
        if (index>=0&&index<size) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index<0||index>=size) return null;
        E old=elements[index];
        elements[index]=element;
        return old;
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

    @Override
    public int indexOf(Object o) {
        for (int i=0;i<size;i++)
            if (o.equals(elements[i])) return i;
        return -1;
    }

    @Override
    public E get(int index) {
        if (index<0||index>=size) return null;
        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o)!=-1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i=size-1;i>=0;i--)
            if (o.equals(elements[i])) return i;
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item:c) {
            if (!contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item:c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified=false;
        for (E item:c) {
            add(index++,item);
            modified=true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean modified = false,vsb;
        for (Object item:c) {
            vsb=true;
            while (vsb) {
                vsb = remove(item);
                if (modified == false) modified = vsb;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modif=false;
        for (int i=0;i<size;i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                modif=true;
                i--;
            }
        }
        return modif;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
