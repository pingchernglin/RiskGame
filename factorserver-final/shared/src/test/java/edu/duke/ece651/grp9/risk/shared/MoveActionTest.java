package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MoveActionTest {

  @Test
  public void test_performAction() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5);

    Territory t2 = new Territory("Tar Valon");
    t2.setOwner(p1);
    t2.setUnits(0);
    String error1 = "This action is invalid: Two Rivers is not connected to Tar Valon.";

    MoveAction m1 = new MoveAction(p1, t1, t2, 5);
    assertEquals(m1.canPerformAction(), error1);
    t2.addNeighbors(t1);
    assertEquals(m1.canPerformAction(), null);

    assertEquals(m1.getPlayer(), p1);

    assertEquals(t1.getUnits(0), 5);
    assertEquals(t2.getUnits(0), 0);
    m1.performAction();
    assertEquals(t1.getUnits(0), 0);
    assertEquals(t2.getUnits(0), 5);
  }

  @Test
  public void test_getSet() {
    Player p1 = new Player("red");
    Territory t1 = new Territory("Two Rivers");
    t1.setOwner(p1);
    t1.setUnits(5);

    Territory t2 = new Territory("Tar Valon");
    t2.setOwner(p1);
    t2.setUnits(0);

    MoveAction m1 = new MoveAction(p1, t1, t2, 5);

    assertEquals(m1.getEndLevel(), -1);
  }

  // 1: {1,2,3}
  // 2: {2,4}
  // 3: {1,4,5,6}
  // 4: {1,2,3,6}
  // 5: {3,6}
  // 6: {3,4,5}
  @Test
  public void test_compute_resource() {
    Player p1 = new Player("red");
    Player p2 = new Player("green");

    Territory t1 = new Territory("a");
    Territory t2 = new Territory("b");
    Territory t3 = new Territory("c");
    Territory t4 = new Territory("d");
    Territory t5 = new Territory("e");
    Territory t6 = new Territory("f");

    t1.setOwner(p1);
    t2.setOwner(p1);
    t3.setOwner(p2);
    t4.setOwner(p1);
    t5.setOwner(p2);
    t6.setOwner(p2);

    t1.addNeighbors(t2);
    t1.addNeighbors(t3);
    t1.addNeighbors(t4);

    t2.addNeighbors(t1);
    t2.addNeighbors(t4);

    t3.addNeighbors(t1);
    t3.addNeighbors(t4);
    t3.addNeighbors(t5);
    t3.addNeighbors(t6);

    t4.addNeighbors(t1);
    t4.addNeighbors(t2);
    t4.addNeighbors(t3);
    t4.addNeighbors(t6);

    t5.addNeighbors(t3);
    t5.addNeighbors(t6);

    t6.addNeighbors(t4);
    t6.addNeighbors(t5);
    t6.addNeighbors(t3);

    // check resources
    Action move1 = new MoveAction(p2, t6, t3, 5, 0);
    assertEquals(100,move1.computeCost());

    Action move2 = new MoveAction(p2, t3, t5, 2,1);
    assertEquals(40,move2.computeCost());

    Action move3 = new MoveAction(p2, t3, t3, 2,1);
    assertEquals(0,move3.computeCost());

  }
}

