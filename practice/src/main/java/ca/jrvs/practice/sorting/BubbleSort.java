package ca.jrvs.practice.sorting;

/**
 * bubble sort
 * comparing two values and go to next
 *  O(n^2)
 *
 * A bubble sort is used to arrange N elements in ascending order,
 * and it is comparing two values and go to next from the first element.
 * For example, if the first element is greater than the second element, then it swaps.
 * Once the first n loop is done, then it will start the second n-1 loop.
 * It is O of n square. O(n^2)
 */
public class BubbleSort {
  private static void bubbleSort(int[] arr){
    bubbleSort(arr, arr.length-1);
  }

  private static void bubbleSort(int[] arr, int last){
    if(last >0){
      for(int i=1; i<=last; i++){
        if(arr[i-1]>arr[i])
          swap(arr, i-1, i);
      }
      bubbleSort(arr, last-1);
    }
  }

  private static void swap(int[] arr, int source, int target){
    int tmp = arr[source];
    arr[source] = arr[target];
    arr[target] = tmp;
  }

  private static void printArray(int[] arr){
    for(int data : arr)
      System.out.print(data + ", ");
  }

  public static void main(String[] args) {
    int[] arr ={9,11,3,5,4,8};
    bubbleSort(arr);
    printArray(arr);
  }
}
