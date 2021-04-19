package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TwoSumTest {

  @Test
  public void twoSumBruteForce() {
    TwoSum twoSum = new TwoSum();
    int[] test01 = { 3,9,5,7,4,1};
    int target = 9;
    int[] result01 = twoSum.twoSumBruteForce(test01, target);
    assertArrayEquals(new int[]{5,4}, result01);
    int[] test02 = {3,9,5,7};
    try{
      twoSum.twoSumBruteForce(test02, target);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

  @Test
  public void twoSumSorting() {
    TwoSum twoSum = new TwoSum();
    int[] test01 = { 3,9,5,7,4,1};
    int target = 9;
    int[] result01 = twoSum.twoSumSorting(test01, target);
    assertArrayEquals(new int[]{4,5}, result01);
    int[] test02 = {3,9,5,7};
    try{
      twoSum.twoSumSorting(test02, target);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

  @Test
  public void twoSumLinear() {
    TwoSum twoSum = new TwoSum();
    int[] test01 = { 3,9,5,7,4,1};
    int target = 9;
    int[] result01 = twoSum.twoSumLinear(test01, target);
    assertArrayEquals(new int[]{5,4}, result01);
    int[] test02 = {3,9,5,7};
    try{
      twoSum.twoSumLinear(test02, target);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }
}