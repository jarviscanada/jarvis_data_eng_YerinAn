package ca.jrvs.practice.coding_challenges;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.notion.so/jarvisdev/Find-the-Duplicate-Number-04262592056d4cb99fd5d709f2897fee
 */
public class FindTheDuplicateNumber {

  /**
   * Big-O: O(n log n) time
   * Sort : O(n log n)
   * O(1) space
   */
  public static int findDuplicate(int[] nums) {
    Arrays.sort(nums);
    for(int i = 0; i < nums.length-1; i++){
      if(nums[i] == nums[i+1])
        return nums[i];
    }
    return -1;
  }

  /**
   * Big-O: O(n) time
   * O(1) space
   */
  public static int findDuplicateSet(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for(int n : nums){
      if(set.contains(n))
        return n;
      else
        set.add(n);
    }
    return -1;
  }
}
