package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OwnerRuleCheckerTest {

  @Test
  public void test_checkMyRule() {
    Player p1 = new Player("red");
    Player p2 = new Player("green");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnit(5);

    Territory t2 = new Territory("Tar Valon");
    t2.setOwner(p2);
    t2.setUnit(3);

    String error = "This action is invalid: you do not own Tar Valon.";

    RuleChecker ruleChecker = new OwnerRuleChecker(null);

    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t1, t2, 4, 0)), null);
    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t2, t1, 4, 0)), error);
  }
}

