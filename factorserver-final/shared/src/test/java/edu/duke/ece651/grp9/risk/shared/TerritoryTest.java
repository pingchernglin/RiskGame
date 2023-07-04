package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TerritoryTest {

  @Test
  public void test_setgetUnit(){
    Territory ter = new Territory("Test");
    ter.setUnit(10);
    int test_unit = ter.getUnit();
    assertEquals(test_unit, 10);
  }
  
  @Test
  public void test_getName() {
    Territory ter = new Territory("Test");
    assertEquals("Test",ter.getName());
  }

  @Test
  public void test_setOwner(){
    Player p1 = new Player("p1");
    Territory ter = new Territory("Test");
    ter.setOwner(p1);
    Player p2 = ter.getOwner();
    assertEquals(p1, p2);
  }

  @Test
  public void test_Neighbors(){
    Territory ter1 = new Territory("Test1");
    Territory ter2 = new Territory("Test2");
    ter1.addNeighbors(ter2);
    HashSet<Territory> neighbors = new HashSet<Territory>();
    neighbors.add(ter2);
    assertEquals(neighbors, ter1.getNeighbors());
  }

  @Test
  public void test_equals(){
    Territory ter1 = new Territory("Test1");
    String a = "Hello";
    Territory ter2 = new Territory("Hello");

    assertFalse(ter1.equals(a));

    Territory ter3 = new Territory("test1");
    assertTrue(ter1.equals(ter3));
  }

  @Test
  public void test_addUnit() {
    Territory ter1 = new Territory("Test1");
    ter1.setUnit(3);

    assertEquals(ter1.getUnit(), 3);
    ter1.addUnit();
    assertEquals(ter1.getUnit(), 4);
  }

  @Test
  public void test_moveUnits() {
    Territory t1 = new Territory("Tar Valon");
    t1.setUnits(3);
    assertEquals(t1.getUnits(0), 3);
    assertEquals(t1.getUnits(2), 0);

    Territory t2 = new Territory("Andor");
    t1.moveUnits(t2, 2, 0);

    assertEquals(t1.getUnits(0), 1);
    assertEquals(t2.getUnits(0), 2);
  }

  @Test
  public void test_canUpgrade() {
    Territory t1 = new Territory("Tar Valon");
    Player player = new Player("red");
    t1.setOwner(player);

    Unit unit0 = new Level0Unit();
    Unit unit2 = new Level2Unit();

    String error1 = "Invalid upgrade: Must upgrade tech level before upgrading Unit level.";

    assertEquals(error1, t1.canUpgradeUnits(0, 2, 1));

    String error2 = "Invalid upgrade: You can only increase a Unit's level.";
    assertEquals(error2, t1.canUpgradeUnits(0, 0, 1));


    player.upgradeTechLevel();

    assertEquals(null, t1.canUpgradeUnits(0, 1, 1));
  }

  @Test
  public void test_upgradeUnits() {
    Territory t1 = new Territory("Tar Valon");
    t1.setUnits(3);
    assertEquals(t1.getUnits(0), 3);

    t1.upgradeUnits(2, 0, 3);
    assertEquals(t1.getUnits(0), 1);
    assertEquals(t1.getUnits(3), 2);
  }

  @Test
  public void test_addLevel0Unit() {
    Territory t1 = new Territory("Tar Valon");
    assertEquals(t1.getUnits(0), 0);
    t1.addUnit(0);
    t1.addUnit(0);
    assertEquals(t1.getUnits(0), 2);
  }

  @Test
  public void test_mockAction() {
    Territory t1 = new Territory("Tar Valon");
    t1.setUnits(10);
    Territory t2 = new Territory("Two Rivers");
    t2.setUnits(4);

    assertEquals(t1.getUnits(0), 10);
    assertEquals(t2.getUnits(0), 4);

    t1.mockActions(t2, 1, 0);

    assertTrue(t1.mockIsValid());

    t1.mockActions(t2, 10, 0);
    assertFalse(t1.mockIsValid());

    //Mock units should reset when this occurs
    //assertTrue(t1.mockIsValid());
  }

  @Test
  void produceFood() {
    Territory ter = new Territory("red");
    assertEquals(50, ter.produceFood().getQuantity());
  }

  @Test
  void productMoney() {
    Territory ter = new Territory("red");
    assertEquals(20, ter.productMoney().getQuantity());
  }

  @Test
  void clockNum() {
    Territory territory = new Territory("A");
    assertEquals(0, territory.getCloakNum());
    territory.doClockOnTerritory();
    assertEquals(3, territory.getCloakNum());
    territory.reduceClockNum();
    territory.doClockOnTerritory();
    territory.doClockOnTerritory();
    assertEquals(8, territory.getCloakNum());
  }

  @Test
  public void test_addMoveSpy() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Tar Valon");
    assertTrue(t1.getSpies().isEmpty());
    t1.addSpy(p1, 1);
    assertFalse(t1.getSpies().isEmpty());
    assertEquals(t1.getSpies().get(p1).getNumUnits(), 1);
    assertEquals(t1.getSpies().get(new Player("blue")), null);

    t1.addSpy(p1, 1);
    assertEquals(t1.getSpies().get(p1).getNumUnits(), 2);

    Territory t2 = new Territory("Two Rivers");
    t1.moveSpy(p1, t2, 1);
    assertEquals(t1.getSpies().get(p1).getNumUnits(), 1);
    assertEquals(t2.getSpies().get(p1).getNumUnits(), 1);

    t1.moveSpy(p1, t2, 1);
    assertEquals(t1.getSpies().get(p1).getNumUnits(), 0);
    assertEquals(t2.getSpies().get(p1).getNumUnits(), 2);
  }
}
