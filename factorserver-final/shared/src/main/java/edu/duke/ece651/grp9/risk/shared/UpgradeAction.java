package edu.duke.ece651.grp9.risk.shared;

/**
 * Class to handle Upgrade Action
 *
 * @author PROY
 * @since 29 March 2022
 */
public class UpgradeAction implements Action {

  private final Player player;
  private final Territory source;
  private final int numUnits;
  private final RuleChecker upgradeChecker;
  private int startLevel;
  private int endLevel;

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param numUnits is the number of units we are moving from source to destination
   */
  public UpgradeAction(Player player, Territory source, int numUnits) {
    this.player = player;
    this.source = source;
    this.numUnits = numUnits;
    this.upgradeChecker = new UnitsRuleChecker(new OwnerRuleChecker(new UpgradeRuleChecker(null)));
    this.startLevel = 0;
    this.endLevel = 0;
    source.syncUnits();
  }

  /**
   * Constructor to create an Upgrade
   * @param player is the Player performing the Action
   * @param source is the Territory we are upgrading Units in
   * @param numUnits is the number of units we are upgrading
   * @param startLevel is the Unit level we are upgrading
   * @param endLevel is the Unit level we are upgrading to
   */
  public UpgradeAction(Player player, Territory source, int numUnits, int startLevel, int endLevel) {
    this(player, source, numUnits);
    this.startLevel = startLevel;
    this.endLevel = endLevel;
    source.syncUnits(startLevel);
    source.syncUnits(endLevel);
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
    return source;
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
    return startLevel;
  }

  /**
   * Getter for Unit end level
   *
   * @return int target level for Units performing Action
   */
  public int getEndLevel() {
    return endLevel;
  }

  /**
   * Checks chain of rules to ensure Move is valid
   *
   * @return null if valid, if invalid a String describing error is returned
   */
  public String canPerformAction() {
    return upgradeChecker.checkAction(this);
  }

  /**
   * Perform upgrade on source Territories of Unit startLevel
   */
  public void performAction() {
    if (endLevel < 7) {
      source.upgradeUnits(numUnits, startLevel, endLevel); //EVOLUTION 2
    } else {
      source.upgradeSpy(player, numUnits, startLevel);
    }
    player.setMoneyQuantity(player.getMoneyQuantity() - this.computeCost());
  }

  /**
   * Computes the cost of an Upgrade Action
   *
   * @return cost of this Upgrade Action
   */
  public int computeCost() {
    if (endLevel < 7) {
      return (source.getUnitClass(endLevel).getUpgradeCost() -
          source.getUnitClass(startLevel).getUpgradeCost()) * numUnits;
    }
    return numUnits * 20;
  }
}
