package ca.jrvs.practice.data_structure.list;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class LinkedJListTest {

  @Test
  public void add() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    Assert.assertEquals("hello", l.get(0));
  }

  @Test
  public void toArray() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    l.add("world");
    String[] arr = {"hello", "world"};
    Assert.assertArrayEquals(arr, l.toArray());
    Assert.assertEquals(2, l.size());
  }

  @Test
  public void isEmpty() {
    JList<String> l = new LinkedJList<>();
    Assert.assertTrue(l.isEmpty());
    Assert.assertEquals(0, l.size());
    l.add("hello");
    Assert.assertFalse(l.isEmpty());
  }

  @Test
  public void indexOf() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    l.add("world");
    Assert.assertEquals(0, l.indexOf("hello"));
    Assert.assertEquals(-1, l.indexOf("hahaha"));
  }

  @Test
  public void contains() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    l.add("world");
    Assert.assertTrue(l.contains("hello"));
    Assert.assertFalse(l.contains("hahaha"));
  }

  @Test
  public void get() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    l.add("world");
    Assert.assertEquals("hello", l.get(0));
    try{
      l.get(3);
    }catch (IndexOutOfBoundsException e){
      assertTrue(true);
    }
  }

  @Test
  public void remove() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    l.add("world");
    l.add("java");
    Assert.assertEquals("world", l.remove(1));
    String[] arr = {"hello", "java"};
    Assert.assertArrayEquals(arr, l.toArray());
    try{
      l.remove(4);
    }catch (IndexOutOfBoundsException e){
      assertTrue(true);
    }
  }

  @Test
  public void clear() {
    JList<String> l = new LinkedJList<>();
    l.add("hello");
    l.add("world");
    l.add("java");
    Assert.assertEquals(3, l.size());
    l.clear();
    Assert.assertEquals(0, l.size());
    Assert.assertFalse(l.contains("java"));
  }
}