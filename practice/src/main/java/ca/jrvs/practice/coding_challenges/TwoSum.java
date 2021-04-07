package ca.jrvs.practice.coding_challenges;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Given an array of integers nums and an integer target,
 * return indices of the two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * You can return the answer in any order.
 */
public class TwoSum {
  /**
   * Time complexity: O(n^2)
   * nested for loop
   */
  public int[] twoSumBruteForce(int[] nums, int target){
    for(int i=0; i < nums.length; i++){
      for(int j=1; j<nums.length; j++){
        if((nums[i] + nums[j] == target) && (nums[i] != nums[j])){
          return new int[]{nums[i], nums[j]};
        }
      }
    }
    throw new IllegalArgumentException("No two sum solution");
  }

  /**
   * Time complexity: O(n + nlogn)
   * Iterate the loop - O(n)
   * Sorting method - n log n
   */
  public int[] twoSumSorting(int[] nums, int target){
    Arrays.sort(nums);
    int indexL=0;
    int indexR=nums.length-1;
    while(indexR >= indexL){
      int result = nums[indexL] + nums[indexR];
      if(result > target)
        indexR--;
      else if(result < target)
        indexL++;
      else{
        return new int[]{nums[indexL], nums[indexR]};
      }
    }
    throw new IllegalArgumentException("No two sum solution");
  }

  /**
   * Time complexity: O(n)
   */
  public int[] twoSumLinear(int[] nums, int target){
    HashMap<Integer, Integer> map = new HashMap<>();
    for(int i = 0; i < nums.length; i++) {
      if(map.containsKey(target - nums[i]))
        return new int[]{target - nums[i], nums[i]};
      map.put(nums[i], target - nums[i]);
    }
    throw new IllegalArgumentException("No two sum solution");
  }
}
