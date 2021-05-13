package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidPalindromeTest {
  String s;

  @Before
  public void setUp() throws Exception {
    s = "A man, a plan, a canal: Panama";
  }

  @Test
  public void isPalindrome() {
    assertTrue(ValidPalindrome.isPalindrome(s));
    s = "ate";
    assertFalse(ValidPalindrome.isPalindrome(s));
  }

  @Test
  public void isPalindromeRecursion() {
    assertTrue(ValidPalindrome.isPalindromeRecursion(s));
    s = "ate";
    assertFalse(ValidPalindrome.isPalindromeRecursion(s));
  }
}