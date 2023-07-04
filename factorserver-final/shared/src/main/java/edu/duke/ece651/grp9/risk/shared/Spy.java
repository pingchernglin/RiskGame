package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

/**
 * Class for Spy Unit
 *
 * @author PROY
 * @since 26 March 2022
 */
public class Spy implements Serializable {
  //private Player owner;
  private int numUnits;
  private int mockUnits;
  private int upgradeCost;
  //private int numUnits;
  //private Territory location;

  public Spy () {
    this(1);
  }

  public Spy(int numUnits) {
    this.upgradeCost = 20;
    this.numUnits = numUnits;
    syncUnits();
    //this.location = location;
    //this.owner = owner;
  }

  /**
   * Getter for number of Units
   *
   * @return int number of units
   */
  public int getNumUnits() {
    return numUnits;
  }

  /**
   * Sets mockUnits to unit so that they are synced for next round of Action's check
   */
  public void syncUnits() {
    mockUnits = numUnits;
  }

  /**
   * Update mockUnits to be used as part of RuleChecker
   *
   * @param movedUnits int for number of Unit's being relocated
   */
  public void mockAction(int movedUnits) {
    mockUnits += movedUnits;
  }

  /**
   * Getter for mockUnits of this Unit level
   *
   * @return mockUnits for this Unit level
   */
  public int getMockUnits() {
    return mockUnits;
  }

  /**
   * Move spy between Territories
   *
   * @param destination Territory Spy is moving to
   * @return String to indicate if move is allowed
   */
  //TODO incorporate cost of move, only allow 1 move per round
  //TODO can move more than 1 within own Territory
//  public String move(Territory destination) {
//    if (location.getNeighbors().contains(destination)) {
//      location = destination;
//      return null;
//    }
//    return "Invalid move: Spy can only move one Territory at a time.";
//  }

  /**
   * Getter for this Spy's location
   *
   * @return Territory to indicate this Spies location
   */
//  public Territory getLocation() {
//    return location;
//  }

  /**
   * Add units to this Spy instance
   * @param addedUnits number of units to add
   */
  public void addUnits(int addedUnits) {
    numUnits += addedUnits;
    syncUnits();
  }
}
