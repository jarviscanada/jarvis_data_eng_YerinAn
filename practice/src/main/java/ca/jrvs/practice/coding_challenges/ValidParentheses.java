package ca.jrvs.practice.coding_challenges;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParentheses {
  /**
   * Set the openClosePair
   */
  public static Map<Character, Character> setMap(){
    Map<Character, Character> openClosePair= new HashMap<>();
    openClosePair.put('(', ')');
    openClosePair.put('{', '}');
    openClosePair.put('[', ']');
    return openClosePair;
  }

  /**
   * Big-O: O(n) time
   * make a stack and compare with openClosePair
   * if openClosePair key contains a character, push the value in the stack
   * if openClosePair value contains a character,
   * pops the value in the stack and checks a character is the same value or not
   * if the stack is empty at the end, parentheses are valid.
   */
  public static boolean isValid(String s) {
    if(s.length() % 2 != 0) return false;
    Map<Character, Character> openClosePair = setMap();
    Stack<Character> stack = new Stack<>();
    for(Character c:s.toCharArray()){
      if(openClosePair.containsKey(c))
        stack.push(c);
      else if(openClosePair.containsValue(c)){
        if(stack.isEmpty() || openClosePair.get(stack.pop()) != c)
          return false;
      }
    }
    return stack.isEmpty();
  }
}
