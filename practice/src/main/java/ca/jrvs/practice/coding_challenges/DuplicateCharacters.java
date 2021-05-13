package ca.jrvs.practice.coding_challenges;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateCharacters {

  /**
   * Big-O: O(n) time
   * using set, checking duplicated value
   */
  public static List<Character> duplicateCharacters(String s){
    List<Character> result = new ArrayList<>();
    Set<Character> set = new HashSet<>();
    for(Character c : s.replace(" ","").toCharArray()){
      if(set.contains(c))
        result.add(c);
      else
        set.add(c);
    }
    return result;
  }
}
