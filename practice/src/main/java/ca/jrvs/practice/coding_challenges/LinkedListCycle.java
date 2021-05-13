package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/LinkedList-Cycle-bf0fdb081f634ae5928e9cc3e211a07d
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.
 * Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 */
public class LinkedListCycle {

  /**
   * Big-O: O(n) time
   * 1. create two pointers which are slow and fast
   * 2. fast moves 2 nodes, slow moves 1 node at the time.
   * if fast node reaches null or slow and fast are not meeting,
   * it means there is no cycle and return false.
   */
  public static boolean hasCycle(ListNode head) {
    if(head == null) return false;
    ListNode slow = head;
    ListNode fast = head.next;
    while(slow != fast){
      if(fast == null || fast.next == null)
        return false;
      slow = slow.next;
      fast = fast.next.next;
    }
    return true;
  }

  static class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
      val = x;
      next = null;
    }
  }
}
