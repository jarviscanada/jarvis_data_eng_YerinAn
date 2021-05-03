package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FindLargestNSmallestTest {
  private int[] nums;
  @Before
  public void setUp() throws Exception {
    nums = new int[]{3,2,3,1,2,4,5,5,6};
  }

  @Test
  public void findMaxLoop() {
    assertEquals(6, FindLargestNSmallest.findMaxLoop(nums));
  }

  @Test
  public void findMinLoop() {
    assertEquals(1, FindLargestNSmallest.findMinLoop(nums));
  }

  @Test
  public void findMinSteam() {
    assertEquals(1, FindLargestNSmallest.findMinSteam(nums));
  }

  @Test
  public void findMaxSteam() {
    assertEquals(6, FindLargestNSmallest.findMaxSteam(nums));
  }

  @Test
  public void findMinCollection() {
    assertEquals(1, FindLargestNSmallest.findMinCollection(nums));
  }

  @Test
  public void findMaxCollection() {
    assertEquals(6, FindLargestNSmallest.findMaxCollection(nums));
  }
}