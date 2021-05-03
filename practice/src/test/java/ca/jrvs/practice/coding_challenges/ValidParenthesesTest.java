package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidParenthesesTest {

  @Test
  public void isValid() {
    String test = "{[]}";
    assertTrue(ValidParentheses.isValid(test));
    test = "{}[][)";
    assertFalse(ValidParentheses.isValid(test));
    test = "([]){}";
    assertTrue(ValidParentheses.isValid(test));
  }
}