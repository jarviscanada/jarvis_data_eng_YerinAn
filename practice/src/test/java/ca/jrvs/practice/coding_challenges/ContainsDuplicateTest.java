package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ContainsDuplicateTest {
  private int[] nums;
  @Before
  public void setUp() throws Exception {
    nums = new int[]{1, 2, 3, 8, 7};
  }

  @Test
  public void containsDuplicate() {
    assertFalse(ContainsDuplicate.containsDuplicate(nums));
    nums = new int[]{1, 2, 3, 8, 1};
    assertTrue(ContainsDuplicate.containsDuplicate(nums));
  }

  @Test
  public void containsDuplicateSet() {
    assertFalse(ContainsDuplicate.containsDuplicateSet(nums));
    nums = new int[]{1, 2, 3, 8, 1};
    assertTrue(ContainsDuplicate.containsDuplicateSet(nums));
  }
}