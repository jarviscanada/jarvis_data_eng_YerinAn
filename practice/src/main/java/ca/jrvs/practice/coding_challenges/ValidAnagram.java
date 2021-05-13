package ca.jrvs.practice.coding_challenges;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.notion.so/jarvisdev/Valid-Anagram-d6104be4475d4ec081b76a13257dfdb7
 */
public class ValidAnagram {

  /**
   * Big-O: O(nlgn) time
   * TimSort algorithm, Arrays.sort() is O(nlogn)
   */
  public static boolean isAnagramEqual(String s, String t) {
    if(s.length() != t.length()) return false;
    char[] sArray = s.toCharArray();
    char[] tArray = t.toCharArray();
    Arrays.sort(sArray);
    Arrays.sort(tArray);
    return Arrays.equals(sArray, tArray);
  }

  /**
   * Big-O: O(n) time
   */
  public static boolean isAnagram(String s, String t){
    if(s.length() != t.length()) return false;
    int[] char_counts = new int[26];
    for(int i=0; i<s.length(); i++){
      char_counts[s.charAt(i)-'a']++;
      char_counts[t.charAt(i)-'a']--;
    }
    for(int count : char_counts){
      if(count != 0)
        return false;
    }
    return true;
  }

  /**
   * Big-O: O(n) time
   * using recursion and map. if map contains value 1 or -1 return false
   */
  public static boolean isAnagramRecursion(String s, String t){
    if(s.length() != t.length())
      return false;
    Map<Character, Integer> map = new HashMap<>();
    return isAnagramRecursionHelper(s, t, map, s.length()-1);
  }

  public static boolean isAnagramRecursionHelper(String s, String t, Map<Character, Integer> map, int n){
    addMap(s, map, n, 1);
    addMap(t, map, n, -1);
    if(n ==0)
      return (!map.containsValue(-1) && !map.containsValue(1)) ? true : false;
    else
      return isAnagramRecursionHelper(s,t,map,n-1);
  }

  public static Map<Character, Integer> addMap(String s, Map<Character, Integer> map, int n, int add){
    if(map.containsKey(s.charAt(n)))
      map.replace(s.charAt(n), map.get(s.charAt(n) + add));
    else
      map.put(s.charAt(n), add);
    return map;
  }
}
