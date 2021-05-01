package ca.jrvs.practice.coding_challenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DuplicateCharactersTest {
  private String s;
  @Before
  public void setUp() throws Exception {
    s ="A black cat";
  }

  @Test
  public void duplicateCharacters() {
    assertTrue(DuplicateCharacters.duplicateCharacters(s).contains('a'));
    assertTrue(DuplicateCharacters.duplicateCharacters(s).contains('c'));
    assertFalse(DuplicateCharacters.duplicateCharacters(s).contains('A'));
  }
}