package ca.jrvs.practice.data_structure.stack_queue;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public class LinkedJListJDequeTest {

  @Test
  public void add() {
    JQueue<String> queue = new LinkedJListJDeque<>();
    queue.add("hello");
    Assert.assertEquals("hello", queue.peekQ());
  }

  @Test
  public void remove() {
    JQueue<String> queue = new LinkedJListJDeque<>();
    queue.add("hello");
    queue.add("world");
    Assert.assertEquals("hello", queue.remove());
    Assert.assertEquals("world", queue.remove());
    try{
      queue.remove();
    }catch (NoSuchElementException e){
      assertTrue(true);
    }
  }

  @Test
  public void peekQ() {
    JQueue<String> queue = new LinkedJListJDeque<>();
    Assert.assertEquals(null, queue.peekQ());
    queue.add("hello");
    queue.add("world");
    Assert.assertEquals("hello", queue.peekQ());
  }

  @Test
  public void pop() {
    JStack<String> stack = new LinkedJListJDeque<>();
    stack.push("hello");
    stack.push("world");
    Assert.assertEquals("world", stack.pop());
    Assert.assertEquals("hello", stack.pop());
    try{
      stack.pop();
    }catch (NoSuchElementException e){
      assertTrue(true);
    }
  }

  @Test
  public void push() {
    JStack<String> stack = new LinkedJListJDeque<>();
    stack.push("hello");
    Assert.assertEquals("hello", stack.peek());
    stack.push("world");
    Assert.assertEquals("world", stack.peek());
  }

  @Test
  public void peek() {
    JStack<String> stack = new LinkedJListJDeque<>();
    Assert.assertEquals(null, stack.peek());
    stack.push("hello");
    stack.push("world");
    Assert.assertEquals("world", stack.peek());
  }
}