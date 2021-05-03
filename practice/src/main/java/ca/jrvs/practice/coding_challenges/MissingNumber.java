package ca.jrvs.practice.coding_challenges;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.notion.so/jarvisdev/Missing-Number-6f379be9991f4f82b0226dc7ca02b591
 * Given an array nums containing n distinct numbers in the range [0, n],
 * return the only number in the range that is missing from the array.
 * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
 */
public class MissingNumber {

  /**
   * Big-O: O(n) time : (2n)
   */
  public static int missingNumberSum(int[] nums) {
    int sum = 0;
    int expected = 0;
    for(int n : nums)
      sum += n;
    for(int i = 0; i <= nums.length; i++)
      expected += i;
    return expected-sum;
  }

  /**
   * Big-O: O(n) time
   * O(1) space
   */
  public static int missingNumberGauss(int[] nums){
    int sum = 0;
    int expected = nums.length * (nums.length + 1) / 2;
    for(int n : nums)
      sum += n;
    return expected-sum;
  }

  /**
   * Big-O: O(n log n) time
   */
  public static int missingNumberSort(int[] nums) {
    Arrays.sort(nums);
    for(int i=0; i< nums.length; i++){
      if(nums[i] != i)
        return i;
    }
    return nums.length;
  }

  /**
   * Big-O: O(n) time
   * O(n) space
   */
  public static int missingNumberSet(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for(int n : nums) set.add(n);
    for(int i = 0; i <= nums.length; i++){
      if(!set.contains(i))
        return i;
    }
    return -1;
  }

}
