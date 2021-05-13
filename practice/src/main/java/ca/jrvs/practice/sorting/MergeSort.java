package ca.jrvs.practice.sorting;

/**
 * This algorithm is based on splitting a list, into two comparable sized lists.
 * When the function is called, it divides N elements in half and recurs function.
 * The method merges arranged elements by merging two pieces from the smallest piece in the last.
 * left and right and then sorting each list and then merging the two sorted lists back together as one.
 * It is O(n log n) that is always the same. But it needs to have extra storage space.
 */
public class MergeSort {
  private static void mergeSort(int[] arr) {
    int[] tmp = new int[arr.length];
    mergeSort(arr, tmp, 0, arr.length-1);
  }
  private static void mergeSort(int[] arr, int[] tmp, int start, int end){
    if(start < end){
      int mid = (start + end) / 2;
      mergeSort(arr, tmp, start, mid);
      mergeSort(arr, tmp, mid + 1, end);
      merge(arr, tmp, start, mid, end);
    }
  }
  private static void merge(int[] arr, int[] tmp, int start, int mid, int end){
    for(int i = start; i <= end; i++){
      tmp[i] = arr[i];
    }
    int part1 = start;
    int part2 = mid + 1;
    int index = start;
    while(part1 <= mid && part2 <=end){
      if(tmp[part1] <= tmp[part2]){
        arr[index] = tmp[part1];
        part1++;
      }else{
        arr[index] = tmp[part2];
        part2++;
      }
      index++;
    }
    for(int i = 0; i <= mid - part1; i++){
      arr[index + i] = tmp[part1 + i];
    }
  }
  private static void printArray(int[] arr){
    for(int data:arr){
      System.out.print(data + ", ");
    }
    System.out.println();
  }
  public static void main(String[] args){
    int[] arr ={9,11,3,5,4,8};
    mergeSort(arr);
    printArray(arr);
  }
}
