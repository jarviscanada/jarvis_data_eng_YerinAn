package ca.jrvs.practice.data_structure.list;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ArrayJListTest {

  @Test
  public void add() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    Assert.assertEquals("hello", list.get(0));
  }

  @Test
  public void toArray() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    list.add("world");
    String[] arr = {"hello", "world"};
    Assert.assertArrayEquals(arr, list.toArray());
  }

  @Test
  public void size() {
    JList<String> list = new ArrayJList<>();
    Assert.assertEquals(0, list.size());
    list.add("hello");
    list.add("world");
    Assert.assertEquals(2, list.size());
  }

  @Test
  public void isEmpty() {
    JList<String> list = new ArrayJList<>();
    Assert.assertTrue(list.isEmpty());
    list.add("hello");
    list.add("world");
    Assert.assertFalse(list.isEmpty());
  }

  @Test
  public void indexOf() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    list.add("world");
    Assert.assertEquals(0, list.indexOf("hello"));
    Assert.assertEquals(1, list.indexOf("world"));
    Assert.assertEquals(-1, list.indexOf("failed check"));
  }

  @Test
  public void contains() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    list.add("world");
    Assert.assertEquals(true, list.contains("hello"));
    Assert.assertEquals(false, list.contains("hhh"));
  }

  @Test
  public void get() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    list.add("world");
    Assert.assertEquals("hello", list.get(0));
    try{
      list.get(3);
    }catch (IndexOutOfBoundsException e){
      assertTrue(true);
    }
  }

  @Test
  public void remove() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    list.add("world");
    Assert.assertEquals(2, list.size());
    Assert.assertEquals("hello", list.remove(0));
    Assert.assertEquals(1, list.size());
  }

  @Test
  public void clear() {
    JList<String> list = new ArrayJList<>();
    list.add("hello");
    list.add("world");
    list.clear();
    Assert.assertEquals(0, list.size());
    Assert.assertFalse(list.contains("hello"));
  }
}