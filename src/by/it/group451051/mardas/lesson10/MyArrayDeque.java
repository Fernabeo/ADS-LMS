package by.it.group451051.mardas.lesson10;

import java.util.*;

public class MyArrayDeque<E>implements Deque<E> {

    private E[] elements = (E[]) new Object[10];
    private int size = 0;

    public void sizex2() {
        E[] newElements = (E[]) new Object[size*2];
        System.arraycopy(elements,0,newElements,0,size);
        elements=newElements;
    }
    @Override
    public int size() {
        return size;
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
    public boolean isEmpty() {
        return false;
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
    public void clear() {

    }

    @Override
    public void addFirst(E e) {
        if (size+1==elements.length) sizex2();
        System.arraycopy(elements,0,elements,1,size);
        elements[0]=e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (size+1==elements.length) sizex2();
        elements[size++]=e;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        return poll();
    }

    @Override
    public E pollLast() {
        E value=elements[size-1];
        elements[size-1]=null;
        size--;
        return value;
    }

    @Override
    public E getFirst() {
        return elements[0];
    }

    @Override
    public E getLast() {
        return elements[size-1];
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        if (size==elements.length) sizex2();
        elements[size++]=e;
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        E value=elements[0];
        System.arraycopy(elements,1,elements,0,size-1);
        size--;
        return value;
    }



    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E element() {
        return elements[0];
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

}
