package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class MapFactoryTest {

  @Test
  public void test_makeMap() {
    MapFactory f = new MapFactory();
    Map map2 = f.makeMap(2);
    assertEquals(map2.getPlayerNum(), 2);

    Map map3 = f.makeMap(3);
    assertEquals(map3.getPlayerNum(), 3);

    Map map4 = f.makeMap(4);
    assertEquals(map4.getPlayerNum(), 4);

    Map map5 = f.makeMap(5);
    assertEquals(map5.getPlayerNum(), 5);

    Map map6 = f.makeMap(6);
    assertEquals(map6, null);
  }

  @Test
  public void test_twomap() {
    MapFactory f = new MapFactory();
    Map m = f.makeMapForTwo();
    HashSet<Player> pyr_list = new HashSet<Player>();
    pyr_list.add(new Player("blue"));
    pyr_list.add(new Player("red"));
    
    assertEquals(2, m.getPlayerNum());
  }


  @Test
  public void test_threemap(){
    MapFactory f = new MapFactory();
    Map m = f.makeMapForThree();
    assertEquals(3, m.getPlayerNum());
  }


  @Test
  public void test_fourmap(){
    MapFactory f = new MapFactory();
    Map m = f.makeMapForFour();
    assertEquals(4, m.getPlayerNum());
  }


  @Test
  public void test_fivemap(){
    MapFactory f = new MapFactory();
    Map m = f.makeMapForFive();
    assertEquals(5, m.getPlayerNum());
  }


}
