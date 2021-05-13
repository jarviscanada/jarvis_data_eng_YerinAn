package ca.jrvs.practice.coding_challenges;

import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {

  /**
   * Big-O: O(n^2) time
   * using String.contains() = O(nm)
   */
  public static boolean containsDuplicate(int[] nums) {
    String compare="";
    for(int i = 0; i < nums.length; i++){
      if(compare.contains(String.valueOf(nums[i])))
        return true;
      compare = compare + nums[i];
    }
    return false;
  }

  /**
   * Big-O: O(n) time
   * using Set.contains() = O(1)
   */
  public static boolean containsDuplicateSet(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for(int i : nums){
      if(set.contains(i))
        return true;
      else
        set.add(i);
    }
    return false;
  }
}
