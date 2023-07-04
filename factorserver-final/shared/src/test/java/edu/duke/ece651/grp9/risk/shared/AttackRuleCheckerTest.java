package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackRuleCheckerTest {

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

    String error1 = "This action is invalid: Two Rivers is not adjacent to Tar Valon.";
    String error2 = "This action is invalid: you cannot attack your own Territory.";

    RuleChecker ruleChecker = new AttackRuleChecker(null);

    assertEquals(ruleChecker.checkMyRule(new AttackAction(p1, t1, t2, 4, 0)), error1);

    t1.addNeighbors(t2);
    assertEquals(ruleChecker.checkMyRule(new AttackAction(p1, t1, t2, 4, 0)), null);
    t1.addNeighbors(t3);
    assertEquals(ruleChecker.checkMyRule(new AttackAction(p1, t1, t3, 4, 0)), error2);
  }

  @Test
  public void test_chainedRules() {
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

    String error1 = "This action is invalid: Two Rivers is not adjacent to Tar Valon.";
    String error2 = "This action is invalid: you cannot attack your own Territory.";
    String error3 = "This action is invalid: you do not own Tar Valon.";
    String error4 = "This action is invalid: Two Rivers does not have enough units.";

    RuleChecker ruleChecker = new UnitsRuleChecker(new OwnerRuleChecker(new AttackRuleChecker(null)));

    t3.addNeighbors(t1);
    assertEquals(ruleChecker.checkAction(new AttackAction(p1, t1, t3, 4, 0)), error2);
    assertEquals(ruleChecker.checkAction(new AttackAction(p1, t1, t2, 4, 0)), error1);

    t2.addNeighbors(t1);
    assertEquals(ruleChecker.checkAction(new AttackAction(p1, t1, t2, 6, 0)), null);
    assertEquals(ruleChecker.checkAction(new AttackAction(p1, t2, t1, 3, 0)), error3);

    assertEquals(ruleChecker.checkAction(new AttackAction(p1, t1, t2, 5, 0)), null);

    assertFalse(t1.mockIsValid());
  }

}
