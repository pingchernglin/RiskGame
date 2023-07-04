package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveRuleCheckerTest {

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

    Territory t3 = new Territory("Falme");
    t3.setOwner(p1);
    t3.setUnit(5);

    Territory t4 = new Territory("Tear");
    t4.setOwner(p1);
    t4.setUnit(5);

    Territory t5 = new Territory("Andor");
    t5.setOwner(p1);
    t5.setUnit(5);

    String error1 = "This action is invalid: Two Rivers is not connected to Tar Valon.";
    String error2 = "This action is invalid: Two Rivers is not connected to Andor.";

    RuleChecker ruleChecker = new MoveRuleChecker(null);
    t2.addNeighbors(t1);

    t1.addNeighbors(t3);

    t4.addNeighbors(t2);

    t4.addNeighbors(t5);

    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t1, t2, 4, 0)), error1);
    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t1, t3, 4, 0)), null);
    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t1, t5, 4, 0)), error2);

    t4.addNeighbors(t3);
    assertEquals(ruleChecker.checkMyRule(new MoveAction(p1, t1, t5, 4, 0)), null);
  }
}

