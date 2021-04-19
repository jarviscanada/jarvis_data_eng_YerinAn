package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Test;

public class DuplicateLinkedListNodeTest {
  @Test
  public void retrieve() {
    DuplicateLinkedListNode l = new DuplicateLinkedListNode();
    l.append(1);
    l.append(1);
    l.append(2);
    l.append(3);
    l.append(4);
    l.append(4);
    l.append(2);
    int test01 = l.retrieve();
    assertEquals(7, test01);
    l.removeDuplicate();
    int test02 = l.retrieve();
    assertEquals(4, test02);
  }
}