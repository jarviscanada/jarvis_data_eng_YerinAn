package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PrintLetterWithNumTest {
  private String s;
  @Before
  public void setUp() throws Exception {
    s = "abceeA";
  }

  @Test
  public void printLetterWithNum() {
    assertEquals("a1b2c3e5e5A27", PrintLetterWithNum.printLetterWithNum(s));
    s = "abcee";
    assertEquals("a1b2c3e5e5", PrintLetterWithNum.printLetterWithNum(s));
  }
}