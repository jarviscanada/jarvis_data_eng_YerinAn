package ca.jrvs.practice.search;

import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;

public class BinarySearchTest {

  BinarySearch binarySearch = new BinarySearch();
  String[] arr = new String[]{"a", "b", "c", "d", "e"};

  @Test
  public void binarySearchRecursion() {
    assertEquals(Optional.of(4), binarySearch.binarySearchRecursion(arr, "e"));
    assertEquals(Optional.of(1), binarySearch.binarySearchRecursion(arr, "b"));
    assertEquals(Optional.of(2), binarySearch.binarySearchRecursion(arr, "c"));
  }

  @Test
  public void binarySearchIteration() {
    assertEquals(Optional.of(4), binarySearch.binarySearchIteration(arr, "e"));
    assertEquals(Optional.of(1), binarySearch.binarySearchIteration(arr, "b"));
    assertEquals(Optional.of(2), binarySearch.binarySearchIteration(arr, "c"));
  }
}