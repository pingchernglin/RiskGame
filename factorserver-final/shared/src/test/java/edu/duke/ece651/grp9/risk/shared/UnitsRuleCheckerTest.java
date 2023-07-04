package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UnitsRuleCheckerTest {

  @Test
  public void test_checkMyRule() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5, 0);

    Territory t2 = new Territory("Tar Valon");
    t2.setOwner(p1);
    t2.setUnits(3, 0);

    String error1 = "This action is invalid: number of units must be positive int.";

    RuleChecker ruleChecker = new UnitsRuleChecker(null);

    assertEquals(t1.getUnits(0), 5);
    assertEquals(t2.getUnits(0), 3);

    MoveAction m1 = new MoveAction(p1, t1, t2, 4, 0);
    MoveAction m2 = new MoveAction(p1, t2, t1, 8, 0);

    assertEquals(ruleChecker.checkMyRule(m1), null);
    assertEquals(ruleChecker.checkMyRule(m2), null);

    assertTrue(t1.mockIsValid());
    assertFalse(t2.mockIsValid());

    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t2, t1, -3, 0)), error1);
  }
}
