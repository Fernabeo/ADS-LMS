package by.it.group451051.mardas.lesson10;

import java.util.*;

public class MyLinkedList<E> implements Deque<E> {
    private int size = 0;
    private Node<E> head=null;
    private Node<E> end=null;
    private class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;
        Node(E data,Node<E> prev,Node<E> next) {
            this.data=data;
            this.prev=prev;
            this.next=next;
        }
    }


    @Override
    public int size() {
        return size;
    }


    public E remove(int index) {
        Node<E> delete=head;
        while (index>0) {
            if (delete!=null) {
                delete=delete.next;
                index--;
            } else return null;
        }
        if (delete==null) return null;
        E value=delete.data;
        deleteElement(delete);
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("[");
        Node<E> tek=head;
        while (head!=null) {
            if (tek==null) break;
            sb.append(tek.data);
            if (tek.next!=null) sb.append(", ");
            tek=tek.next;
        }
        sb.append("]");
        return sb.toString();
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
        Node<E> newElement=new Node(e,null,head);
        size++;
        if (head!=null) head.prev=newElement;
        head=newElement;
    }

    @Override
    public void addLast(E e) {
        add(e);
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
        E value=end.data;
        deleteElement(end);
        return value;
    }

    @Override
    public E getFirst() {
        return element();
    }

    @Override
    public E getLast() {
        if (size==0) return null;
        return end.data;
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
        Node<E> t=end;
        Node<E> newElement=new Node(e,end,null);
        end=newElement;
        if (t==null) head=newElement;
            else t.next=newElement;
        size++;
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        E value=head.data;
        deleteElement(head);
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
        if (head!=null) return head.data;
        return null;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> tek=head,delete=null;
        while (tek.data!=null) {
            if (Objects.equals(o,tek.data)) {
                delete=tek;
                break;
            }
            if (tek.next!=null) tek=tek.next; else break;
        }
        if (delete==null) return false;
        deleteElement(delete);
        return true;
    }
    public void deleteElement(Node<E> delete) {
        if (delete==head) {
            head=delete.next;
            head.prev=null;
        } else if (delete==end) {
            end=delete.prev;
            end.next=null;
        } else {
            delete.prev.next=delete.next;
            delete.next.prev=delete.prev;
        }
        delete.data=null;
        delete.prev=null;
        delete.next=null;
        size--;
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
