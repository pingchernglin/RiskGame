package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class MapTest {
  @Test
  public void test_getList() {
    HashSet<Territory> list = new HashSet<Territory>();
    Territory ter1 = new Territory("test1");
    Territory ter2 = new Territory("test2");
    Map m = new Map();
    m.addTerritory(ter1);
    m.addTerritory(ter2);
    list.add(ter1);
    list.add(ter2);
    assertEquals(m.getList(), list);
  }
  @Test
  public void test_getplayer(){
    HashSet<Player> list = new HashSet<Player>();
    Player p1 = new Player("blue");
    Map m = new Map();
    m.addPlayer(p1);
    list.add(p1);
    assertEquals(m.getPlayer(), list);    
  }

  @Test
  public void test_findPlayer() {
    Player p1 = new Player("blue");
    Map map = new Map();
    map.addPlayer(p1);
    assertEquals(map.findPlayer("blue"), p1);
    assertEquals(map.findPlayer("yellow"), null);
  }

  @Test
  public void test_findTerritory() {
    MapFactory mapFactory = new MapFactory();
    Map map = mapFactory.makeMapForTest();
    assertEquals(map.findTerritory("C"), new Territory("C"));
    assertEquals(map.findTerritory("Z"), null);
  }

  @Test
  public void test_removePlayer() {
    Map map = new Map();
    Player p1 = new Player("blue");
    map.addPlayer(p1);
    Player p2 = new Player("red");
    map.addPlayer(p2);

    assertEquals(map.findPlayer("blue"), p1);
    assertEquals(map.findPlayer("red"), p2);

    map.removePlayer(p1);
    assertEquals(map.findPlayer("blue"), null);
    map.removePlayer(p2);
    assertEquals(map.findPlayer("red"), null);
  }

  @Test
  void getGameWinner_true() {
    MapFactory mapFactory = new MapFactory();
    Map map = mapFactory.makeMapForTest();
    Player playerRed = map.findPlayer("red");
    Player playerBlue = map.findPlayer("blue");
    map.findTerritory("C").setOwner(playerRed);
    map.findTerritory("D").setOwner(playerRed);
    playerRed.addTerritory(map.findTerritory("C"));
    playerRed.addTerritory(map.findTerritory("D"));
    playerBlue.removeTerritory(map.findTerritory("C"));
    playerBlue.removeTerritory(map.findTerritory("D"));
    assertEquals(playerRed, map.getGameWinner());
  }

  @Test
  void getGameWinner_false() {
    MapFactory mapFactory = new MapFactory();
    Map map = mapFactory.makeMapForTest();
    Player playerRed = map.findPlayer("red");
    Player playerBlue = map.findPlayer("blue");
    map.findTerritory("C").setOwner(playerRed);
    playerRed.addTerritory(map.findTerritory("C"));
    playerBlue.removeTerritory(map.findTerritory("C"));
    assertEquals(null, map.getGameWinner());
  }

  @Test
  void upgradeMapPerRound() {
    Map map = new Map();
    Player player = new Player("red");
    map.addPlayer(player);
    Territory ter1 = new Territory("NC");
    Territory ter2 = new Territory("CA");
    map.addTerritory(ter1);
    map.addTerritory(ter2);
    ter1.setUnit(10);
    ter1.setUnits(5,0);
    ter1.setUnits(5, 2);

    ter2.setUnit(12);
    ter2.setUnits(5,0);
    ter2.setUnits(5, 2);
    ter2.setUnits(2, 5);
    map.upgradeMapPerRound();

    assertEquals(11, ter1.getUnit());
    assertEquals(6, ter1.getUnits(0));
    assertEquals(5, ter1.getUnits(2));

    assertEquals(13, ter2.getUnit());
    assertEquals(6, ter2.getUnits(0));
    assertEquals(5, ter2.getUnits(2));
    assertEquals(2, ter2.getUnits(5));
  }


    @Test
    void getTerritoryInfo() {
      Map map = new Map();
      Player p1 = new Player("red");

      map.addPlayer(p1);
      p1.setMoneyQuantity(50);
      p1.setFoodQuantity(100);

      Territory ter1 = new Territory("NC");
      map.addTerritory(ter1);
      p1.addTerritory(ter1);
      ter1.setOwner(p1);

      ter1.setUnits(5,0);
      ter1.setUnits(10,2);
      // public String getTerritoryInfo(String terrName) {
      // StringBuilder sb = new StringBuilder();
      // Territory t = this.findTerritory(terrName);
      // sb.append("Territory " + terrName + "    ");
      // sb.append(t.getOwner().getName() + "\n");
      // sb.append("Size: " + t.getSize() + "\n");
      // sb.append("Food: 50 | Money: 20\n");
      // sb.append("Cloak Number: " + t.getCloakNum() + "\n");
      // for (int i = 0; i < 7; i++) {
      // sb.append("Level: " + i + ": " + t.getUnits(i) + "\n");
      // }
      // return sb.toString();
      // }
      String exp = "Territory NC    red\n" +
              "Size: 10\n" +
              "Food: 50 | Money: 20\n" +
              "Cloak Number: 0\n" +
              "Level 0: 5\n" +
              "Level 1: 0\n" +
              "Level 2: 10\n" +
              "Level 3: 0\n" +
              "Level 4: 0\n" +
              "Level 5: 0\n" +
              "Level 6: 0\n";
      System.out.println(map.getTerritoryInfo(ter1.getName()));
      assertEquals(exp, map.getTerritoryInfo(ter1.getName()));
    }

    @Test
    void isNeighbor() {
      Map map = new Map();

      Player p1 = new Player("red");
      Player p2 = new Player("blue");
      map.addPlayer(p1);
      map.addPlayer(p2);

      Territory ter1 = new Territory("NC");
      Territory ter2 = new Territory("CA");
      Territory ter3 = new Territory("MI");

      p1.addTerritory(ter1);
      p2.addTerritory(ter2);
      p2.addTerritory(ter3);

      ter1.addNeighbors(ter2);
      ter2.addNeighbors(ter1);

      assertEquals(true, map.isNeighbor(ter2, p1.getName()));
      assertEquals(false, map.isNeighbor(ter3, p1.getName()));



    }
}
