package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FindTheDuplicateNumberTest {
  private int [] nums;
  @Before
  public void setUp() throws Exception {
    nums = new int[]{3, 1, 3, 4, 2};
  }

  @Test
  public void findDuplicate() {
    assertEquals(3, FindTheDuplicateNumber.findDuplicate(nums));
    nums = new int[]{3, 1, 4, 2};
    assertEquals(-1, FindTheDuplicateNumber.findDuplicate(nums));
  }

  @Test
  public void findDuplicateSet() {
    assertEquals(3, FindTheDuplicateNumber.findDuplicateSet(nums));
    nums = new int[]{3, 1, 4, 2};
    assertEquals(-1, FindTheDuplicateNumber.findDuplicateSet(nums));
  }
}