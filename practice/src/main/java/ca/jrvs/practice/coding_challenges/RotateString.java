package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Rotate-String-213dfd1073df490997d58b2c78f157bb
 * A shift on A consists of taking string A and moving the leftmost character to the rightmost position.
 * For example, if A = 'abcde', then it will be 'bcdea' after one shift on A.
 * Return True if and only if A can become B after some number of shifts on A.
 */
public class RotateString {

  /**
   * Big-O: O(n) time
   * string.contains : O(nm) time complexity
   * extended string A+A could check whether a compared number was rotated or not.
   */
  public static boolean rotateString(String a, String b) {
    return (a.length() == b.length() && (a+a).contains(b));
  }

  /**
   * Big-O: O(n) time
   * O(1) space
   * Create two empty strings.
   * one for saving strings after matching first character of b.
   * the other for saving strings before matching first character of b.
   */
  public static boolean rotateStringLoop(String a, String b) {
    if(a.length() != b.length()) return false;
    char first = b.charAt(0);
    String compare = "";
    String temp = "";
    boolean findFirstVal = false;
    for(int i = 0; i < a.length(); i++){
      if(a.charAt(i) == first)
        findFirstVal = true;
      if(findFirstVal)
        compare = compare + a.charAt(i);
      else
        temp = temp + a.charAt(i);
    }
    compare += temp;
    if(compare.equals(b))
      return true;
    return false;
  }

}
