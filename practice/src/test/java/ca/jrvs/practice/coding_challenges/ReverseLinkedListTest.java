package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import ca.jrvs.practice.coding_challenges.ReverseLinkedList.ListNode;
import org.junit.Before;
import org.junit.Test;

public class ReverseLinkedListTest {

  ListNode n1;

  @Before
  public void setUp() throws Exception {
    ListNode n5 = new ListNode(5, null);
    ListNode n4 = new ListNode(4, n5);
    ListNode n3 = new ListNode(3, n4);
    ListNode n2 = new ListNode(2, n3);
    n1 = new ListNode(1, n2);
  }

  @Test
  public void iteration() {
    ListNode result = ReverseLinkedList.iteration(n1);
    assertEquals(5, result.val);
    assertEquals(4, result.next.val);
    assertEquals(3, result.next.next.val);
    assertEquals(2, result.next.next.next.val);
  }

  @Test
  public void recursion() {
    ListNode result = ReverseLinkedList.recursion(n1);
    assertEquals(5, result.val);
    assertEquals(4, result.next.val);
    assertEquals(3, result.next.next.val);
    assertEquals(2, result.next.next.next.val);
  }
}