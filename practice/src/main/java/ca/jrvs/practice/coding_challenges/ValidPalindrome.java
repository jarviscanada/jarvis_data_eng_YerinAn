package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Valid-Palindrome-8b630320cb824417ad5e33b740781495
 *  Given a string s, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 *  https://leetcode.com/problems/valid-palindrome/
 */
public class ValidPalindrome {

  /**
   * Big-O: O(n) time
   */
  public static boolean isPalindrome(String s) {
    String alphaN = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    int left = 0;
    int right = alphaN.length()-1;
    while(left < right){
      if(alphaN.charAt(left) != alphaN.charAt(right))
        return false;
      left++;
      right--;
    }
    return true;
  }

  /**
   * Big-O: O(n) time
   */
  public static boolean isPalindromeRecursion(String s) {
    String alphaN = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    return isPalindromeHelper(alphaN);
  }

  /**
   * 1. check first and end of string
   * 2. reduce string using subString
   */
  public static boolean isPalindromeHelper(String s){
    if (s.length() <= 1)
      return true;
    if (s.charAt(0) == s.charAt(s.length() - 1))
      return isPalindromeHelper(s.substring(1, s.length() - 1));
    else
      return false;
  }
}
