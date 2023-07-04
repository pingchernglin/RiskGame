package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to handle Move Action
 *
 * @author PROY
 * @since 11 March 2022
 */
public class MoveAction implements Action {

  private final Player player;
  private final Territory source;
  private final Territory destination;
  private final int numUnits;
  private final RuleChecker moveChecker;
  private int unitLevel;

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   */
  public MoveAction(Player player, Territory source, Territory destination, int numUnits) {
    this.player = player;
    this.source = source;
    this.destination = destination;
    this.numUnits = numUnits;
    this.moveChecker = new UnitsRuleChecker(new OwnerRuleChecker(new MoveRuleChecker(null)));
    this.unitLevel = 0;
    source.syncUnits();
    destination.syncUnits();
  }

  /**
   * Constructor to create a Move
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to destination
   * @param unitLevel is the Unit level we are moving
   */
  public MoveAction(Player player, Territory source, Territory destination, int numUnits, int unitLevel) {
    this(player, source, destination, numUnits);
    this.unitLevel = unitLevel;
    source.syncUnits(unitLevel);
    destination.syncUnits(unitLevel);
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
    return destination;
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
   * Getter for Unit level
   *
   * @return int level of Units performing Action
   */
  public int getUnitLevel() {
    return unitLevel;
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
    return moveChecker.checkAction(this);
  }

  /**
   * Perform move on source and destination Territories
   */
  public void performAction() {
    if (unitLevel < 7) {
      source.moveUnits(destination, numUnits, unitLevel); //EVOLUTION 2
      player.setFoodQuantity(player.getFoodQuantity() - this.computeCost());
    } else {
      source.moveSpy(player, destination, numUnits); //EVOLUTION 3
      player.setFoodQuantity(player.getFoodQuantity() - this.computeCost());
    }
  }

  /**
   * compute the lowest cost for moving units
   * cost =  unitsNum * passing territories' size
   * @return
   */
  // TODO: now the assumption is all territories have the same size, can use bfs
  // may need use Dijkstra’s Algorithm if territory has different size
  public int computeCost() {
    if (unitLevel == 7) {
      return 20 * numUnits;
    }
    int passTerrSize = 0;
    boolean found = false;
    // bfs for shortest path
    Queue<Territory> queue = new LinkedList<Territory>();
    HashSet<Territory> visited = new HashSet<Territory>();
    queue.add(source);
    visited.add(source);
    // corner case: source == destination
    if (source.equals(destination)) {
      return 0;
    }
    while (!queue.isEmpty()) {
      passTerrSize += queue.peek().getSize();
      int sz = queue.size();
      for (int i = 0; i < sz; i++) {
        Territory front = queue.poll();
        if (front.equals(destination)) {
          found = true;
          break;
        }
        for (Territory t : front.getNeighbors()) {
          if (t.getOwner().equals(player) && !visited.contains(t)) {
            queue.add(t);
            visited.add(source);
          }
        }
      }
      if (found) break;
    }
    return passTerrSize * numUnits;
  }
}
