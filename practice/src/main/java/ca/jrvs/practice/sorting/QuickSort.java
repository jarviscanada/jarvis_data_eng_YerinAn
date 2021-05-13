package ca.jrvs.practice.sorting;

public class QuickSort {

  /**
   * Quick sort
   * Quicksort is the most optimized sort algorithm which performs sorting in O(n log n) comparisons.
   * For example, after selecting a pivot value in the list,
   * setting the start point from the first element.
   * Startpoint ignores elements if the next element value is smaller than the pivot.
   * Setting endpoint from the last element.
   * Endpoint ignores elements if the next value is greater than the pivot.
   * When start point and endpoint are intersecting,
   * the left elements are smaller than pivot and right elements are bigger than pivot.
   * Partitioning this two group of elements and recurring this function.
   * If the pivot value is selected the smallest or biggest value every time,
   * then complexity will be O of n square.
   */
  public static void quickSort(int[] arr){
    quickSort(arr, 0, arr.length-1);
  }
  private static void quickSort(int[] arr, int start, int end){
    int part = partition(arr,start,end);
    if(start < part-1)
      quickSort(arr, start, part-1);
    if(part < end)
      quickSort(arr, part, end);
  }

  private static void swap(int[] arr, int start, int end){
    int tmp = arr[start];
    arr[start] = arr[end];
    arr[end] = tmp;
  }

  private static int partition(int[] arr, int start, int end){
    int pivot = arr[(start + end)/2];
    while(start <= end){
      while(arr[start] < pivot) start ++;
      while (arr[end] > pivot) end--;
      if(start <= end){
        swap(arr, start, end);
        start++;
        end--;
      }
    }
    return start;
  }

  private static void printArray(int[] arr){
    for(int data : arr)
      System.out.print(data + ", ");
  }

  public static void main(String[] args) {
    int[] arr ={9,11,3,5,4,8};
    quickSort(arr);
    printArray(arr);
  }
}
