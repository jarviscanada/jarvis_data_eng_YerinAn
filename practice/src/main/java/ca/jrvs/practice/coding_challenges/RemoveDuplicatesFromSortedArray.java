package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Duplicates-from-Sorted-Array-0160a121ebc649eb9a236a95d8c29566
 * Given a sorted array nums, remove the duplicates in-place such that each element appears only once and returns the new length.
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 */
public class RemoveDuplicatesFromSortedArray {

  /**
   * Big-O: O(n) time
   * O(1) space
   * using two pointers which are slow and fast
   * if the fast and slow value are different,
   * slow's next value is changing as fast value
   * at the same time slow counts the number of unique values.
   */
  public static int removeDuplicates(int[] nums) {
    if(nums.length == 0) return 0;
    int slow = 0;
    for(int fast = 1; fast < nums.length; fast ++){
      if(nums[slow] != nums[fast]){
        nums[++slow] = nums[fast];
      }
    }
    return slow+1;
  }
}
