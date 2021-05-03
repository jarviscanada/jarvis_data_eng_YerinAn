package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Remove-Element-c46a16cd128e4455bc10d7da7a762103
 * Given an array nums and a value val, remove all instances of that value in-place and return the new length.
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 */
public class RemoveElement {

  /**
   * Big-O: O(n) time
   * O(1) space
   */
  public static int removeElement(int[] nums, int val) {
    int size = 0;
    for(int i = 0; i < nums.length; i++){
      if(nums[i] != val){
        nums[size] = nums[i];
        size++;
      }
    }
    return size;
  }
}
