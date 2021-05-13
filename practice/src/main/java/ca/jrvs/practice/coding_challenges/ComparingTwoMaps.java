package ca.jrvs.practice.coding_challenges;

import java.util.Map;

/**
 * https://www.notion.so/jarvisdev/How-to-compare-two-maps-f0d249825ef74727ba4653e33627c23b
 * How to compare two maps in Java?
 * Two maps are equal when they have the same keys and values (e.g. use equals to determine if two objects are equal)
 */
public class ComparingTwoMaps {

  /**
   * Big-O: O(n) time
   */
  public static <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2){
    if(m1.size() != m2.size())
      return false;
    return m1.equals(m2);
  }

  /**
   * Big-O: O(n) time
   */
  public static <K,V> boolean compareMapsHashJMap(Map<K,V> m1, Map<K,V> m2){
    if(m1.size() != m2.size())
      return false;
    return m1.entrySet().stream().allMatch(e->e.getValue().equals(m2.get(e.getKey())));
  }
}
