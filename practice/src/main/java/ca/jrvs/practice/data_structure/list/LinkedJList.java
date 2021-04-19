package ca.jrvs.practice.data_structure.list;

import java.util.Arrays;

public class LinkedJList<E> implements JList<E> {

  private int size = 0;

  transient Node<E> head;

  transient Node<E> tail;

  public LinkedJList() {
  }

  private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
      this.prev = prev;
      this.item = element;
      this.next = next;
    }
  }

  public void addToTail(E e){
    Node<E> t = tail;
    Node<E> newNode = new Node<>(t, e, null);
    tail = newNode;
    if (t == null)
      head = newNode;
    else
      t.next = newNode;
    size++;
  }

  public void addToHead(E e){
    Node<E> h = head;
    Node<E> newNode = new Node<>(null, e, h);
    head = newNode;
    if(h==null)
      tail = newNode;
    else
      h.prev = newNode;
    size++;
  }

  @Override
  public boolean add(E e) {
    addToTail(e);
    return true;
  }

  @Override
  public Object[] toArray() {
    Object[] result = new Object[size];
    Node<E> e = head;
    for (int i=0; i < size; i++){
      result[i] = e.item;
      e = e.next;
    }
    return result;
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }

  @Override
  public int indexOf(Object o) {
    Node<E> e=head;
    if (o == null) {
      for (int i=0; i < size; i++) {
        if (e.item == null)
          return i;
        e=e.next;
      }
    } else {
      for (int i=0; i < size; i++) {
        if (o.equals(e.item))
          return i;
        e=e.next;
      }
    }
    return -1;
  }

  @Override
  public boolean contains(Object o) {
    return this.indexOf(o) >= 0;
  }

  public Node<E> node(int index) {
    Node<E> x;
    if (index < (size >> 1)) {
      x = head;
      for (int i = 0; i < index; i++)
        x = x.next;
    } else {
      x = tail;
      for (int i = size - 1; i > index; i--)
        x = x.prev;
    }
    return x;
  }

  public E unlink(Node<E> x) {
    E e = x.item;
    Node<E> next = x.next;
    Node<E> prev = x.prev;
    if (prev == null) {
      head = next;
    } else {
      prev.next = next;
      x.prev = null;
    }
    if (next == null) {
      tail = prev;
    } else {
      next.prev = prev;
      x.next = null;
    }
    x.item = null;
    size--;
    return e;
  }

  @Override
  public E get(int index) {
    if(index < 0 || index >= size)
      throw new IndexOutOfBoundsException("ERROR: index is out of bounds");
    return node(index).item;
  }

  @Override
  public E remove(int index) {
    if(index < 0 || index >= size)
      throw new IndexOutOfBoundsException("ERROR: index is out of bounds");
    return unlink(node(index));
  }

  @Override
  public void clear() {
    for(Node<E> x=head; x != null; ){
      Node<E> next = x.next;
      x.item = null;
      x.prev = null;
      x.next = null;
      x = next;
    }
    head = tail = null;
    size = 0;
  }
}
