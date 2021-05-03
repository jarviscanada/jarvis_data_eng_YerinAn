package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Reverse-Linked-List-dcb201b1488c4b76b454d07ed66c4a06
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 */
public class ReverseLinkedList {

  /**
   * Big-O: O(n) time; O(1) space.
   * iterate whole list
   * @param head
   * @return
   */
  public static ListNode iteration(ListNode head) {
    ListNode curr = head;
    ListNode prev = null;
    while(curr != null){
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static void printString(ListNode node){
    while(node.next != null){
      System.out.print(node.val + " - ");
      node = node.next;
    }
    System.out.print(node.val);
  }

  /**
   * Big-O: O(n) time; O(n) space.
   * make head and newHead
   * 1. giving next value of newHead as head
   * 2. head needs to disconnect with newHead
   * origin node: A->B->C
   * newHead: C->B  head: B -> C (newHead) : B ... C(disconnect)
   * so newHead can return recursive ListNode
   */
  public static ListNode recursion(ListNode head) {
    if(head == null || head.next == null)
      return head;
    ListNode newHead = recursion(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
  }

  public static class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
}
