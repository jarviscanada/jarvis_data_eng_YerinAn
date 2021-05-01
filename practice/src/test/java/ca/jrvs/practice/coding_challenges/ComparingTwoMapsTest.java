package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import ca.jrvs.practice.coding_challenges.LinkedListCycle.ListNode;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ComparingTwoMapsTest {
  Map<String, Integer> m1;
  Map<String, Integer> m2;
  @Before
  public void setUp() throws Exception {
    m1 = new HashMap<>();
    m1.put("Hello", 1);
    m1.put("World", 2);
    m1.put("Java", 3);
    m1.put("Haha", 3);
    m2 = new HashMap<>();
    m2.put("Hello", 1);
    m2.put("World", 2);
    m2.put("Java", 3);
    m2.put("Haha", 3);
  }

  @Test
  public void compareMaps() {
    assertTrue(ComparingTwoMaps.compareMaps(m1, m2));
    m2.replace("Java", 2);
    assertFalse(ComparingTwoMaps.compareMaps(m1, m2));
  }

  @Test
  public void compareMapsHashJMap() {
    assertTrue(ComparingTwoMaps.compareMapsHashJMap(m1, m2));
    m2.replace("Java", 2);
    assertFalse(ComparingTwoMaps.compareMapsHashJMap(m1, m2));
  }
}