package ca.jrvs.practice.coding_challenges;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;


/**
 * https://www.notion.so/jarvisdev/Find-Largest-Smallest-68480fed1f0b4a71b9a4f3d074cf891b
 */
public class FindLargestNSmallest {

  /**
   * Big-O: O(n) time
   * O(1) space.
   */
  public static int findMaxLoop(int[] nums) {
    int compare = nums[0];
    for(int n : nums){
      if(compare < n)
        compare = n;
    }
    return compare;
  }

  /**
   * Big-O: O(n) time
   * O(1) space.
   */
  public static int findMinLoop(int[] nums) {
    int compare = nums[0];
    for(int n : nums){
      if(compare > n)
        compare = n;
    }
    return compare;
  }

  /**
   * Big-O: O(n) time
   * O(1) space
   * loop all elements in array but it doesn't hold elements in memory
   */
  public static int findMinSteam(int[] nums) {
    return Arrays.stream(nums).min().getAsInt();
  }

  /**
   * Big-O: O(n) time
   * O(1) space
   * loop all elements in array but it doesn't hold elements in memory
   */
  public static int findMaxSteam(int[] nums) {
    return Arrays.stream(nums).max().getAsInt();
  }

  /**
   * Big-O: O(n) time
   * O(1) space.
   */
  public static int findMinCollection(int[] nums){
    return Collections.min(Arrays.stream(nums).boxed().collect(Collectors.toList()));
  }

  /**
   * Big-O: O(n) time
   * O(1) space.
   */
  public static int findMaxCollection(int[] nums){
    return Collections.max(Arrays.stream(nums).boxed().collect(Collectors.toList()));
  }
}
