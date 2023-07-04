package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UnitTest {
  @Test
  public void test_applyBonus() {
    Unit unit2 = new Level2Unit();
    assertEquals(unit2.applyBonus(5), 8);

    Unit unit6 = new Level6Unit();
    assertEquals(unit6.applyBonus(19), 34);
  }

  @Test
  public void test_syncUnits() {
    Unit unit2 = new Level2Unit();
    unit2.setNumUnits(30);

    assertEquals(unit2.getNumUnits(), 30);
    assertEquals(unit2.getNumUnits(), unit2.getMockUnits());

    unit2.addUnits(2);
    assertEquals(unit2.getNumUnits(), 32);
    assertEquals(unit2.getNumUnits(), unit2.getMockUnits());
  }

  @Test
  public void test_mockAction() {
    Unit unit2 = new Level2Unit();
    unit2.setNumUnits(30);
    unit2.mockAction(-8);

    assertEquals(unit2.getNumUnits(), 30);
    assertEquals(unit2.getMockUnits(), 22);
  }
}
