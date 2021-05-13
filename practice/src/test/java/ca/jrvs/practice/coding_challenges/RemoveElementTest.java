package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RemoveElementTest {
  private int[] nums;
  private int val;
  @Before
  public void setUp() throws Exception {
    nums = new int[] {0, 1, 2, 2, 3, 0, 4, 2};
    val = 2;
  }

  @Test
  public void removeElement() {
    assertEquals(5, RemoveElement.removeElement(nums, val));
  }
}