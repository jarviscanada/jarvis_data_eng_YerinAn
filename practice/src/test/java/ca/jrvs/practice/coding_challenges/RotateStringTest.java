package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RotateStringTest {

  private String A, B;
  @Before
  public void setUp() throws Exception {
    A = "abcde";
    B = "cdeab";
  }

  @Test
  public void rotateString() {
    assertTrue(RotateString.rotateString(A,B));
    B = "cdeaa";
    assertFalse(RotateString.rotateString(A,B));
  }

  @Test
  public void rotateStringLoop() {
    assertTrue(RotateString.rotateStringLoop(A,B));
    B = "cdeaa";
    assertFalse(RotateString.rotateStringLoop(A,B));
  }
}