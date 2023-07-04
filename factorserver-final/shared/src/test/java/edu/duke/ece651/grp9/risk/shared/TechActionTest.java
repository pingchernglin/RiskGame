package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TechActionTest {

  @Test
  public void test_performAction() {
    Player p1 = new Player("red");
    String error1 = "This action is invalid: You are already at the max tech level.";

    p1.setMoneyQuantity(500);
    assertEquals(p1.getMoneyQuantity(), 500);
    assertEquals(p1.getTechLevel(), 1);

    Action action = new TechAction(p1);

    //1->2
    assertEquals(action.canPerformAction(), null);
    assertEquals(action.computeCost(), 50);
    action.performAction();
    assertEquals(p1.getMoneyQuantity(), 450);
    assertEquals(p1.getTechLevel(), 2);

    //2->3
    assertEquals(action.canPerformAction(), null);
    assertEquals(action.computeCost(), 75);
    action.performAction();
    assertEquals(p1.getMoneyQuantity(), 375);
    assertEquals(p1.getTechLevel(), 3);

    //3->4
    assertEquals(action.canPerformAction(), null);
    assertEquals(action.computeCost(), 125);
    action.performAction();
    assertEquals(p1.getMoneyQuantity(), 250);
    assertEquals(p1.getTechLevel(), 4);

    //4->5
    assertEquals(action.canPerformAction(), null);
    assertEquals(action.computeCost(), 200);
    action.performAction();
    assertEquals(p1.getMoneyQuantity(), 50);
    assertEquals(p1.getTechLevel(), 5);

    //5->6
    assertEquals(action.canPerformAction(), null);
    assertEquals(action.computeCost(), 300);
    action.performAction();
    assertEquals(p1.getMoneyQuantity(), -250);
    assertEquals(p1.getTechLevel(), 6);

    assertEquals(action.canPerformAction(), error1);
  }

  @Test
  public void test_getters() {
    Player p1 = new Player("red");

    Action action = new TechAction(p1);

    assertEquals(action.getSource(), null);
    assertEquals(action.getDestination(), null);
    assertEquals(action.getUnitLevel(), -1);
    assertEquals(action.getEndLevel(), -1);
    assertEquals(action.getNumUnits(), -1);
  }
}
