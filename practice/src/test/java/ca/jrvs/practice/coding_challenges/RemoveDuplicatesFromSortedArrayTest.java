package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RemoveDuplicatesFromSortedArrayTest {
  private int[] nums;
  @Before
  public void setUp() throws Exception {
    nums = new int[]{0,0,1,1,1,2,2,3,3,4};
  }

  @Test
  public void removeDuplicates() {
    assertEquals(5, RemoveDuplicatesFromSortedArray.removeDuplicates(nums));
    nums = new int[]{0,0,1,1,1};
    assertEquals(2, RemoveDuplicatesFromSortedArray.removeDuplicates(nums));
  }
}