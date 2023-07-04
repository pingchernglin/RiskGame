package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to handle Move Action
 *
 * @author PROY
 * @since 2 April 2022
 */
public class TechAction implements Action {

  private final Player player;
  private final RuleChecker techChecker;
  private final int[] upgradeCosts;

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   */
  public TechAction(Player player) {
    this.player = player;
    this.techChecker = new TechRuleChecker(null);
    //1->2 = 50
    //2->3 = 75
    //3->4 = 125
    //4->5 = 200
    //5->6 = 300
    this.upgradeCosts = new int[]{50, 75, 125, 200, 300, 0};
  }

  /**
   * Getter for Player
   *
   * @return Player who is making Action
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Getter for source Territory
   *
   * @return source Territory
   */
  public Territory getSource() {
    return null;
  }

  /**
   * Getter for destination Territory
   *
   * @return destination Territory
   */
  public Territory getDestination() {
    return null;
  }

  /**
   * Getter for number of Units
   *
   * @return int number of Units performing Action
   */
  public int getNumUnits() {
    return -1;
  }

  /**
   * Getter for Unit level
   *
   * @return int level of Units performing Action
   */
  public int getUnitLevel() {
    return -1;
  }

  /**
   * Getter for Unit end level
   *
   * @return int target level for Units performing Action
   */
  public int getEndLevel() {
    return -1;
  }

  /**
   * Checks chain of rules to ensure Move is valid
   *
   * @return null if valid, if invalid a String describing error is returned
   */
  public String canPerformAction() {
    return techChecker.checkAction(this);
  }

  /**
   * Perform move on source and destination Territories
   */
  public void performAction() {
    player.setMoneyQuantity(player.getMoneyQuantity() - this.computeCost());
    player.upgradeTechLevel();
  }

  /**
   * Compute the cost to upgrade this Player's level
   *
   * @return int cost of upgrading Player's tech level
   */
  public int computeCost() {
    return upgradeCosts[player.getTechLevel() - 1];
  }
}