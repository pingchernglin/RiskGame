package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class ActionRuleCheckerTest {

   @Test
  public void test_checkLoseAction() {
    ActionRuleChecker tmp = new ActionRuleChecker();
    assertNull(tmp.checkLoseAction("Q"));
    assertEquals("the input character is invalid, please enter again!", tmp.checkLoseAction("t"));
    assertEquals("the input length is invalid, please enter again!", tmp.checkLoseAction("Qt"));
  }
  @Test
  public void test_checkAction() {
    ActionRuleChecker tmp = new ActionRuleChecker();
    assertNull(tmp.checkAction("M"));
    assertEquals("the input character is invalid, please enter again!", tmp.checkAction("t"));
    assertEquals("the input length is invalid, please enter again!", tmp.checkAction("Mt"));
  }

  @Test
  public void test_checkColor(){
    ActionRuleChecker tmp = new ActionRuleChecker();
    HashSet<String> remainingColors = new HashSet<>();
    remainingColors.add("red");
    assertNull(tmp.checkColor("red",remainingColors ));
    assertEquals("The color you input is invalid, please enter again",tmp.checkColor("blue",remainingColors ));

  }


  @Test
  public void test_checkUnit(){
    ActionRuleChecker tmp = new ActionRuleChecker();
    Player a = new Player("red");
    Territory ter_1 = new Territory("A");
    Territory ter_2 = new Territory("B");
    Territory ter_3 = new Territory("C");
    a.addTerritory(ter_1);
    a.addTerritory(ter_2);
    a.addTerritory(ter_3);
    assertNull(tmp.checkUnit("15 5 10",a));

    String error1 = "The input is invalid: Input must only be numbers.";
    String error2 = "The input is invalid: Must enter 3 separate numbers.";
    String error3 = "This input is invalid: Sum of units must equal 30.";
    String error4 = "The input is invalid: Territory cannot have negative units.";

    assertEquals(tmp.checkUnit("15 5 ",a), error2);
    assertEquals(tmp.checkUnit("15 5 10 ",a), null);
    assertEquals(tmp.checkUnit("15 4 a",a), error1);
    assertEquals(tmp.checkUnit(" 15 5 10",a), error2);
    assertEquals(tmp.checkUnit("16 5 10",a), error3);
    assertEquals(tmp.checkUnit(" ", a), error2);
    assertEquals(tmp.checkUnit("-1 30 1", a), error4);
  }
}
