package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

/**
 * Class to handle different types of Unit's that each Territory contains
 *
 * @author PROY
 * @since 26 March 2022
 */
public class Unit implements Serializable {

  private String name;
  private int numUnits;
  private int mockUnits;
  private int level;
  private int bonus;
  private int upgradeCost;

  /**
   * Constructor for Unit class
   *
   * @param name String description of Unit
   * @param bonus int number added to roll value
   * @param upgradeCost int cost to upgrade to the next Unit level
   */
  public Unit(String name, int level, int bonus, int upgradeCost) {
    this.name = name;
    this.numUnits = 0;
    this.mockUnits = numUnits;
    this.level = level;
    this.bonus = bonus;
    this.upgradeCost = upgradeCost;
  }

  public Unit(Unit unit) {
    this.name = unit.getName();
    this.numUnits = unit.getNumUnits();
    this.mockUnits = unit.getMockUnits();
    this.level = unit.getLevel();
    this.bonus = unit.getBonus();
    this.upgradeCost = unit.getUpgradeCost();
  }

  /**
   * Getter for name of this Unit level
   *
   * @return name of this Unit level
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for numUnits of this Unit level
   *
   * @return numUnits for this Unit level
   */
  public int getNumUnits() {
    return numUnits;
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
   * Getter for upgradeCost of this Unit level
   *
   * @return upgrade cost for this Unit level
   */
  public int getUpgradeCost() {
    return upgradeCost;
  }

  /**
   * Setter for count of this Unit level
   *
   * @param numUnits int indicating amount of units of this Unit level
   */
  public void setNumUnits(int numUnits) {
    this.numUnits = numUnits;
    syncUnits();
  }

  /**
   * Sets mockUnits to unit so that they are synced for next round of Action's check
   */
  public void syncUnits() {
    mockUnits = numUnits;
  }

  /**
   * Add newUnits to this Unit level
   *
   * @param newUnits int for number of Unit's being added
   */
  public void addUnits(int newUnits) {
    numUnits += newUnits;
    syncUnits();
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
   * Applies this Unit's bonus to the rolled value
   *
   * @param roll number rolled by the die
   * @return roll + bonus
   */
  public int applyBonus(int roll) {
    return roll + bonus;
  }

  public int getLevel() {
    return level;
  }

  public int getBonus() {
    return bonus;
  }
}
