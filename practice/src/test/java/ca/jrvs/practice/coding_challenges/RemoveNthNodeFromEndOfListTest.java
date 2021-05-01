package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import ca.jrvs.practice.coding_challenges.RemoveNthNodeFromEndOfList.ListNode;
import org.junit.Before;
import org.junit.Test;

public class RemoveNthNodeFromEndOfListTest {

  ListNode n1;

  @Before
  public void setUp() throws Exception {
    n1 = new ListNode(1, null);
  }

  @Test
  public void removeNthFromEnd() {
    ListNode test01 = RemoveNthNodeFromEndOfList.removeNthFromEnd(n1, 1);
    assertEquals(1, test01.val);
    ListNode n2 = new ListNode(2, null);
    n1.next = n2;
    ListNode test02 = RemoveNthNodeFromEndOfList.removeNthFromEnd(n1, 1);
    assertEquals(1, test02.val);
    ListNode n5 = new ListNode(5, null);
    ListNode n4 = new ListNode(4, n5);
    ListNode n3 = new ListNode(3, n4);
    n2.next = n3;
    ListNode result = RemoveNthNodeFromEndOfList.removeNthFromEnd(n1, 2);
    assertEquals(3, result.next.next.val);
    assertEquals(5, result.next.next.next.val);
  }
}