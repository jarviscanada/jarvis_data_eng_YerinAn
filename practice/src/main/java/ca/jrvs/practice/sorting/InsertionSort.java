package ca.jrvs.practice.sorting;

/**
 * O(n^2)
 * To sort an array of size n in ascending order:
 * 1: Iterate from arr[1] to arr[n] over the array.
 * 2: Compare the current element (key) to its predecessor.
 * 3: If the key element is smaller than its predecessor, compare it to the elements before.
 * Move the greater elements one position up to make space for the swapped element.
 */
public class InsertionSort {
  public static void insertionSortImperative(int[] input) {
    for (int i = 1; i < input.length; i++) {
      int key = input[i];
      int j = i - 1;
      while (j >= 0 && input[j] > key) {
        input[j + 1] = input[j];
        j = j - 1;
      }
      input[j + 1] = key;
    }
  }
  private static void printArray(int[] arr){
    for(int data : arr)
      System.out.print(data + ", ");
  }

  public static void main(String[] args) {
    int[] arr = {9, 11, 3, 5, 4, 8};
    insertionSortImperative(arr);
    printArray(arr);
  }
}
