package ca.jrvs.practice.coding_challenges;

/**
 * Recursion
 * Store/Memoize
 * Bottom-up
 */
public class FiboNumNClimbStairs {

  /**
   * Big-O: O(2^n) time; O(n) space
   * recomputing values every time until reaching the goal
   * this creates a recursion tree with 2^n nodes (calls) and height n (O(n) space complexity).
   */
  public static int fiboRecursive(int n){
    if(n <= 0)
      return -1;
    if(n <= 2)
      return 1;
    else
      return fiboRecursive(n-1) + fiboRecursive(n-2);
  }

  /**
   * Big-O: O(n) time; O(n) space
   * values are cached at the first time and calling saved values at the next computed time
   * the method only calls itself at most 2n times.
   */
  public static int fiboMemoizeHelper(int n, int[] memo){
    if(memo.length > 0 && memo[n] != 0)
      return memo[n];
    if(n <= 2)
      return 1;
    return memo[n] = fiboMemoize(n-1) + fiboMemoize(n-2);
  }

  public static int fiboMemoize(int n){
    if(n <= 0)
      return -1;
    int[] memo = new int[n+1];
    return fiboMemoizeHelper(n, memo);
  }

  /**
   * Big-O: O(n) time; O(n) space.
   * not using recursion
   */
  public static int fiboBottomUp(int n){
    if(n <= 0)
      return -1;
    if(n <= 2)
      return 1;
    int[] bu = new int[n+1];
    bu[1] = 1;
    bu[2] = 1;
    for(int i = 3; i < bu.length; i++)
      bu[i] = bu[i-1] + bu[i-2];
    return bu[n];
  }

  public static int stairRecursive(int n){
    if(n <= 0)
      return -1;
    return fiboRecursive(n+1);
  }

  public static int stairMemoize(int n){
    if(n <= 0)
      return -1;
    return fiboMemoize(n+1);
  }

  public static int stairBottomUp(int n){
    if(n <= 0)
      return -1;
    return fiboBottomUp(n+1);
  }
}
