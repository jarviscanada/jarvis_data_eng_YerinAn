package ca.jrvs.practice.data_structure.stack_queue;

import ca.jrvs.practice.data_structure.list.LinkedJList;
import java.util.NoSuchElementException;

public class LinkedJListJDeque<E> implements JDeque<E> {

  LinkedJList<E> deque;

  public LinkedJListJDeque(){
    deque = new LinkedJList();
  }

//  public static void main(String[] args) {
//    JQueue<Integer> queue = new LinkedJListJDeque<>();
//    JStack<Integer> stack = new LinkedJListJDeque<>();
//    stack.push(1);
//    stack.push(2);
//    stack.push(3);
//    stack.push(4);
//    System.out.println(stack.pop());
//    System.out.println(stack.pop());
//    System.out.println(stack.peek());
//    System.out.println(stack.pop());
//    queue.add(1);
//    queue.add(2);
//    queue.add(3);
//    queue.add(4);
//    System.out.println(queue.remove());
//    System.out.println(queue.remove());
//    System.out.println(queue.peekQ());
//    System.out.println(queue.remove());
//    System.out.println(queue.remove());
//  }

  @Override
  public boolean add(E e) {
    if(e == null)
      throw new NullPointerException();
    try{
      deque.add(e);
      return true;
    }catch (IllegalArgumentException exception){
      throw new IllegalStateException();
    }
  }

  @Override
  public E remove() {
    if(deque.isEmpty())
      throw new NoSuchElementException();
    return deque.remove(0);
  }

  @Override
  public E peekQ() {
    if(deque.isEmpty())
      return null;
    return deque.get(0);
  }

  @Override
  public E pop() {
    if(deque.isEmpty())
      throw new NoSuchElementException();
    int index = deque.size()-1;
    return deque.remove(index);
  }

  @Override
  public void push(E e) {
    if(e == null)
      throw new NullPointerException();
    deque.addToTail(e);
  }

  @Override
  public E peek() {
    if(deque.isEmpty())
      return null;
    int index = deque.size()-1;
    return deque.get(index);
  }
}
