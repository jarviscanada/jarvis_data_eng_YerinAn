package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidAnagramTest {
  private String s;
  private String t;

  @Before
  public void setUp() throws Exception {
    s = "anagram";
    t = "nagaram";
  }

  @Test
  public void isAnagramEqual() {
    assertTrue(ValidAnagram.isAnagramEqual(s,t));
    s = "anagramq";
    t = "nagaramw";
    assertFalse(ValidAnagram.isAnagramEqual(s,t));
  }

  @Test
  public void isAnagram() {
    assertTrue(ValidAnagram.isAnagram(s,t));
    s = "anagramq";
    t = "nagaramw";
    assertFalse(ValidAnagram.isAnagram(s,t));
  }

  @Test
  public void isAnagramRecursion() {
    assertTrue(ValidAnagram.isAnagramRecursion(s,t));
    s = "anagramq";
    t = "nagaramw";
    assertFalse(ValidAnagram.isAnagramRecursion(s,t));
  }
}