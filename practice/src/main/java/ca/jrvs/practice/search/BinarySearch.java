package ca.jrvs.practice.search;

import java.util.Optional;

public class BinarySearch {

  /**
   * find the target index in a sorted array
   * @param arr input array is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not found
   */
  public <E extends Comparable>Optional<Integer> binarySearchRecursion(E[] arr, E target){
    if(arr.length >= 1)
      return binarySearchRecursionHelper(arr, 0, arr.length - 1, target);
    return Optional.empty();
  }

  public <E extends Comparable>Optional<Integer> binarySearchRecursionHelper(E[] arr, int left, int right, E target){
    int mid = Math.round((left + right)/2);
    if(left > right)
      return Optional.empty();

    if(arr[mid].equals(target))
      return Optional.of(mid);
    else if(arr[mid].compareTo(target) > 0)
      return binarySearchRecursionHelper(arr, left,mid-1, target);
    else{
      return binarySearchRecursionHelper(arr,mid+1, right, target);
    }
  }

  /**
   * find the target index in a sorted array
   * @param arr input array is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not found
   */
  public <E extends Comparable>Optional<Integer> binarySearchIteration(E[] arr, E target){
    int left = 0;
    int right = arr.length;
    if(arr.length >= 1){
      while(left <= right){
        int mid = Math.round((left + right)/2);
        if(arr[mid].compareTo(target) > 0){
          right = mid -1;
        }else if(arr[mid].compareTo(target) < 0)
          left = mid + 1;
        else
          return Optional.of(mid);
      }
    }
    return Optional.empty();
  }
}
