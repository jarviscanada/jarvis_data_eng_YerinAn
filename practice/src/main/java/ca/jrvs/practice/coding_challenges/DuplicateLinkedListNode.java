package ca.jrvs.practice.coding_challenges;

/**
 * Remove duplicate nodes in an unsorted integer linked list
 * Unsorted list
 * space: O(1)
 * time: O(N^2)
 */
public class DuplicateLinkedListNode {
  Node header;
  static class Node{
    int data;
    Node next = null;
  }

  public DuplicateLinkedListNode(){
    header = new Node();
  }

  public void append(int data){
    Node end = new Node();
    end.data = data;
    Node n = header;
    while(n.next != null){
      n=n.next;
    }
    n.next=end;
  }

  public int retrieve(){
    Node n = header.next;
    int num = 1;
    while(n.next != null){
      System.out.print(n.data + " -> ");
      n=n.next;
      num ++;
    }
    System.out.println(n.data);
    return num;
  }

  public void removeDuplicate(){
    Node n = header;
    while (n != null && n.next != null){
      Node r = n;
      while (r.next != null){
        if(n.data == r.next.data)
          r.next = r.next.next;
        else
          r = r.next;
      }
      n=n.next;
    }
  }
}