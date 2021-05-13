package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MissingNumberTest {
  private int[] nums;
  @Before
  public void setUp() throws Exception {
    nums = new int[]{9,6,4,2,3,5,7,0,1};
  }

  @Test
  public void missingNumberSum() {
    assertEquals(8, MissingNumber.missingNumberSum(nums));
    nums = new int[]{0};
    assertEquals(1, MissingNumber.missingNumberSum(nums));
  }

  @Test
  public void missingNumberGauss() {
    assertEquals(8, MissingNumber.missingNumberGauss(nums));
    nums = new int[]{0};
    assertEquals(1, MissingNumber.missingNumberGauss(nums));
  }

  @Test
  public void missingNumberSort() {
    assertEquals(8, MissingNumber.missingNumberSort(nums));
    nums = new int[]{0};
    assertEquals(1, MissingNumber.missingNumberSort(nums));
  }

  @Test
  public void missingNumberSet() {
    assertEquals(8, MissingNumber.missingNumberSet(nums));
    nums = new int[]{0};
    assertEquals(1, MissingNumber.missingNumberSet(nums));
  }
}