package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Test;

public class FiboNumNClimbStairsTest {

  @Test
  public void fiboRecursive() {
    assertEquals(-1, FiboNumNClimbStairs.fiboRecursive(0));
    assertEquals(2, FiboNumNClimbStairs.fiboRecursive(3));
    assertEquals(21, FiboNumNClimbStairs.fiboRecursive(8));
  }

  @Test
  public void fiboMemoize() {
    assertEquals(-1, FiboNumNClimbStairs.fiboMemoize(0));
    assertEquals(2, FiboNumNClimbStairs.fiboMemoize(3));
    assertEquals(21, FiboNumNClimbStairs.fiboMemoize(8));
  }

  @Test
  public void fiboBottomUp() {
    assertEquals(-1, FiboNumNClimbStairs.fiboBottomUp(0));
    assertEquals(2, FiboNumNClimbStairs.fiboBottomUp(3));
    assertEquals(21, FiboNumNClimbStairs.fiboBottomUp(8));
  }

  @Test
  public void stairRecursive() {
    assertEquals(-1, FiboNumNClimbStairs.stairRecursive(0));
    assertEquals(1, FiboNumNClimbStairs.stairRecursive(1));
    assertEquals(2, FiboNumNClimbStairs.stairRecursive(2));
    assertEquals(3, FiboNumNClimbStairs.stairRecursive(3));
    assertEquals(5, FiboNumNClimbStairs.stairRecursive(4));
  }

  @Test
  public void stairMemoize() {
    assertEquals(-1, FiboNumNClimbStairs.stairMemoize(0));
    assertEquals(1, FiboNumNClimbStairs.stairMemoize(1));
    assertEquals(2, FiboNumNClimbStairs.stairMemoize(2));
    assertEquals(3, FiboNumNClimbStairs.stairMemoize(3));
    assertEquals(5, FiboNumNClimbStairs.stairMemoize(4));
  }

  @Test
  public void stairBottomUp() {
    assertEquals(-1, FiboNumNClimbStairs.stairBottomUp(0));
    assertEquals(1, FiboNumNClimbStairs.stairBottomUp(1));
    assertEquals(2, FiboNumNClimbStairs.stairBottomUp(2));
    assertEquals(3, FiboNumNClimbStairs.stairBottomUp(3));
    assertEquals(5, FiboNumNClimbStairs.stairBottomUp(4));
  }
}