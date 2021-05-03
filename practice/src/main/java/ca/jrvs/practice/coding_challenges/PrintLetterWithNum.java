package ca.jrvs.practice.coding_challenges;

/**
 * https://www.notion.so/jarvisdev/Print-letter-with-number-60a4d874ad9d49d2aebd0b9f2f45635b
 * Given a String which contains only lower and upper case letters,
 * print letter index after each character. For example,
 * the index of a is 1,  b is 2, A is 27, and so on.
 * input: "abcee"
 * output: "a1b2c3e5e5"
 */
public class PrintLetterWithNum {
  /**
   * Big-O: O(n) time
   * ASCII - A = 65; a=97;
   */
  public static String printLetterWithNum(String s){
    StringBuilder builder = new StringBuilder();
    for(Character c : s.toCharArray()){
      builder.append(c);
      if(c-'a' >= 0)
        builder.append(c-'a'+1);
      else
        builder.append(c-'A'+27);
    }
    return builder.toString();
  }
}
