package ca.jrvs.practice.data_structure.list;

public class LinkedJList<E> implements JList<E> {

  private int size = 0;
  /**
   * Pointer to first node.
   */
  transient Node<E> first;

  /**
   * Pointer to last node.
   */
  transient Node<E> last;

  /**
   * Constructs an empty list.
   */
  public LinkedJList() {
  }

  private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }
  }

  @Override
  public boolean add(E e) {
    return false;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public E get(int index) {
    return null;
  }

  @Override
  public E remove(int index) {
    return null;
  }

  @Override
  public void clear() {

  }
}
