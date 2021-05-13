package ca.jrvs.practice.coding_challenges;

import ca.jrvs.practice.coding_challenges.utils.ListNode;

/**
 * https://www.notion.so/jarvisdev/Middle-of-the-Linked-List-97730fb1750547d881f7a0cd81a91d7d
 * Given a non-empty, singly linked list with head node head, return a middle node of linked list.
 * If there are two middle nodes, return the second middle node.
 */
public class MiddleOfLinkedList {

  /**
   * Big-O: O(n) time; O(1) space
   * O(N/2) = O(n)
   * traversing half of the elements
   * using 2 pointer which are here(h), tortoise(t) and moves 2 nodes at the same time.
   * when the h pointer reaches the end of list, the t pointer points to middle node.
   * @param head
   * @return
   */
  public static ListNode middleNode(ListNode head) {
    ListNode h = head;
    ListNode t = head;
    while(h != null && h.getNext() != null){
      h = h.getNext().getNext();
      t = t.getNext();
    }
    return t;
  }
}
