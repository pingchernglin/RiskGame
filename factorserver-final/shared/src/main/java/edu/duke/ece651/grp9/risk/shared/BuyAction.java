package edu.duke.ece651.grp9.risk.shared;

/**
 * Class to handle Buy Action
 *
 * @author PROY
 * @since 20 April 2022
 */
public class BuyAction implements Action {

  private final Player player;
  private final Territory source;
  private final int numUnits;
  private final RuleChecker buyChecker;

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param numUnits is the number of units we are moving from source to destination
   */
  public BuyAction(Player player, Territory source, int numUnits) {
    this.player = player;
    this.source = source;
    this.numUnits = numUnits;
    this.buyChecker = new OwnerRuleChecker(new BuyRuleChecker(null));
    source.syncUnits();
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
    return source;
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
    return numUnits;
  }

  /**
   * Getter for Unit start level
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
    return buyChecker.checkAction(this);
  }

  /**
   * Perform upgrade on source Territories of Unit startLevel
   */
  public void performAction() {
    source.getUnitClass(0).addUnits(numUnits);
    player.setMoneyQuantity(player.getMoneyQuantity() - this.computeCost());
  }

  /**
   * Computes the cost of an Upgrade Action
   *
   * @return cost of this Upgrade Action
   */
  public int computeCost() {
    return numUnits * 40;
  }
}