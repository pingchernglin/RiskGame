package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UpgradeActionTest {

  @Test
  public void test_performAction() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5, 0);

    assertEquals(t1.getUnits(0), 5);
    assertEquals(t1.getUnits(3), 0);

    String error1 = "This action is invalid: Your technology level is not yet at level 4.";

    UpgradeAction u1 = new UpgradeAction(p1, t1, 2, 0, 4);
    assertEquals(u1.canPerformAction(), error1);
    assertTrue(t1.mockIsValid());

    p1.upgradeTechLevel();
    p1.upgradeTechLevel();
    p1.upgradeTechLevel();
    assertEquals(p1.getTechLevel(), 4);

    assertEquals(u1.canPerformAction(), null);
    assertEquals(u1.getPlayer(), p1);

    u1.performAction();

    assertEquals(t1.getUnits(0), 3);
    assertEquals(t1.getUnits(4), 2);

    UpgradeAction u2 = new UpgradeAction(p1, t1, 2, 0, 3);
    UpgradeAction u3 = new UpgradeAction(p1, t1, 2, 0, 3);
    assertTrue(t1.mockIsValid());


    assertEquals(u2.canPerformAction(), null);
    assertTrue(t1.mockIsValid());
    assertEquals(u3.canPerformAction(), null);
    assertFalse(t1.mockIsValid());
  }

  @Test
  public void test_computeCost() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5, 0);

    assertEquals(t1.getUnits(0), 5);
    assertEquals(t1.getUnits(3), 0);

    UpgradeAction u1 = new UpgradeAction(p1, t1, 2, 0, 3);
    assertEquals(u1.computeCost(), 60);

    UpgradeAction u2 = new UpgradeAction(p1, t1, 2, 1, 3);
    assertEquals(u2.computeCost(), 54);

    UpgradeAction u3 = new UpgradeAction(p1, t1, 1, 1, 6);
    assertEquals(u3.computeCost(), 137);
  }
}

