package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void test_addTerritory() {
    Player p1 = new Player("blue");
    Territory ter = new Territory("Test");
    p1.addTerritory(ter);
    HashSet<Territory> territoryList = new HashSet<Territory>();
    territoryList.add(ter);
    assertEquals(territoryList, p1.getTerritoryList());
  }

  @Test
  public void test_getName(){
    Player p1 = new Player("blue");
    assertEquals(p1.getName(), "blue");     
  }

  @Test
  public void test_getTerritoryNumber() {
    Player p1 = new Player("blue");
    Territory ter1 = new Territory("Test");
    p1.addTerritory(ter1);
    assertEquals(p1.getTerritoryNumber(), 1);

    Territory ter2 = new Territory("Test2");
    p1.addTerritory(ter2);
    assertEquals(p1.getTerritoryNumber(), 2);
  }

  @Test
  public void test_getUnit() {
    Player p1 = new Player("blue");

    assertEquals(p1.getUnit(), 30);
  }

  @Test
  public void test_loseStatus() {
    Player p1 = new Player("blue");
    assertEquals(p1.getLoseStatus(), "no act");

    p1.setLoseStatus("Lost");
    assertEquals(p1.getLoseStatus(), "Lost");
  }

  @Test
  public void test_getAdjacency(){
    Player p1 = new Player("blue");
    Territory ter_1 = new Territory("Test_1");
    Territory ter_2 = new Territory("Test_2");
    Territory ter_3 = new Territory("Test_3");
    ter_1.addNeighbors(ter_2);
    ter_1.addNeighbors(ter_3);
    ter_2.addNeighbors(ter_3);
    p1.addTerritory(ter_1);
    p1.addTerritory(ter_2);
    p1.addTerritory(ter_3);
    //For the ter_1, its neighbors become the ter_2 and ter_3.
    // So that, the hashmap looks like <ter_1, <ter_2,ter_3>> for the playerB
    HashSet<Territory> neighbors = new HashSet<Territory>();
    neighbors.add(ter_2);
    neighbors.add(ter_3);
    assertEquals(neighbors, p1.getAdjacency().get(ter_1));
  }

  @Test
  public void test_equal(){
    Player p1 = new Player("blue");
    assertFalse(p1.equals(1));
  }

    @Test
    void isLose() {
      Player p1 = new Player("blue");
      Territory ter = new Territory("NC");
      assertEquals(true, p1.isLose());
      p1.addTerritory(ter);
      assertEquals(false, p1.isLose());
    }

  @Test
  void updatePlayerResource() {
    Player player = new Player("blue");
    player.setMoneyQuantity(500);
    player.setFoodQuantity(500);
    player.updatePlayerResource();
    assertEquals(500, player.getFoodQuantity());
    assertEquals(500, player.getMoneyQuantity());
    Territory ter1 = new Territory("NC");
    Territory ter2 = new Territory("CA");
    player.addTerritory(ter1);
    player.addTerritory(ter2);
    player.updatePlayerResource();
    assertEquals(600, player.getFoodQuantity());
    assertEquals(540, player.getMoneyQuantity());

    player.setFoodQuantity(500);
    player.setMoneyQuantity(400);

    assertEquals(500, player.getFoodQuantity());
    assertEquals(400, player.getMoneyQuantity());
  }
}
