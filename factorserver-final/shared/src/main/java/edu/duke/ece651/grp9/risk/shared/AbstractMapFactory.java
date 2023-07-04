package edu.duke.ece651.grp9.risk.shared;
/**
 * This interface represents an Abstract Factory pattern for Map creation.
 */

public interface AbstractMapFactory {
  /**
   * Make a map for a certain number of players
   * @return the Map created for two/three/four/five people.
   */

  public Map makeMapForTwo();
  public Map makeMapForThree();
  public Map makeMapForFour();
  public Map makeMapForFive();
  public Map makeMapForTest();
}
