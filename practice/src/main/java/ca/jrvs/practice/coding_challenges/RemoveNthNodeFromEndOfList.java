package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Nth-Node-From-End-of-LinkedList-456e895183f144dba41d500d24a684a7
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * Follow up: Could you do this in one pass?
 */
public class RemoveNthNodeFromEndOfList {

  /**
   * Big-O: O(n) time; O(1) time;
   * make two pointer which is fast and slow.
   * make a Nth gap between fast and slow and always keep a gap.
   * if fast.next pointer's value is null then stop a loop.
   * and slow.next points the slow.next.next value
   * so it can remove the n-th node.
   */
  public static ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode fast = head;
    ListNode slow = head;

    for(int i = 0; i <= n; i++){
      if(fast.next != null)
        fast = fast.next;
    }

    while(fast != null){
      slow = slow.next;
      fast = fast.next;
    }

    if(slow != null && slow.next != null)
      slow.next = slow.next.next;
    return head;
  }

  public static void printString(ListNode node){
    while(node.next != null){
      System.out.print(node.val + " - ");
      node = node.next;
    }
    System.out.print(node.val);
  }

  public static class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}
