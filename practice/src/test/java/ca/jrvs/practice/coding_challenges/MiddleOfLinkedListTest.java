package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import ca.jrvs.practice.coding_challenges.utils.ListNode;
import org.junit.Before;
import org.junit.Test;

public class MiddleOfLinkedListTest {
  private ListNode n1;
  private ListNode n3;

  @Before
  public void setUp() throws Exception {
    n3 = new ListNode(3,null);
    ListNode n2 = new ListNode(2,n3);
    n1 = new ListNode(1,n2);
  }

  @Test
  public void middleNode() {
    assertEquals(2, MiddleOfLinkedList.middleNode(n1).getVal());
    ListNode n5 = new ListNode(5,null);
    ListNode n4 = new ListNode(4,n5);
    n3.setNext(n4);
    assertEquals(3, MiddleOfLinkedList.middleNode(n1).getVal());
    ListNode n9 = new ListNode(9,null);
    ListNode n8 = new ListNode(8,n9);
    ListNode n7 = new ListNode(7,n8);
    ListNode n6 = new ListNode(6,n7);
    n5.setNext(n6);
    assertEquals(5, MiddleOfLinkedList.middleNode(n1).getVal());
  }
}