package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import ca.jrvs.practice.coding_challenges.LinkedListCycle.ListNode;
import org.junit.Before;
import org.junit.Test;

public class LinkedListCycleTest {

  ListNode n1;
  ListNode n4;

  @Before
  public void setUp() throws Exception {
    n4 = new ListNode(-4);
    ListNode n3 = new ListNode(0);
    ListNode n2 = new ListNode(2);
    n1 = new ListNode(3);
    n1.next = n2;
    n2.next = n3;
    n3.next = n4;
    n4.next = n2;
  }

  @Test
  public void hasCycle() {
    boolean result = LinkedListCycle.hasCycle(n1);
    assertTrue(result);
    n4.next = null;
    boolean result01 = LinkedListCycle.hasCycle(n1);
    assertFalse(result01);
  }
}