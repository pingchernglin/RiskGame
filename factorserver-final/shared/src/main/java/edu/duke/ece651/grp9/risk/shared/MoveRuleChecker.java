package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class makes sure the source Territory is connected to destination Territory
 *
 * @author PROY
 * @since 10 March 2022
 */
public class MoveRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public MoveRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is connected to destination Territory
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (action.getUnitLevel() == 7) {
      if (!action.getSource().getNeighbors().contains(action.getDestination())) {
        return "This action is invalid: " + action.getSource().getName() +
            " is not adjacent to " + action.getDestination().getName();
      }
      return null;
    }
    Queue<Territory> queue = new LinkedList<Territory>();
    HashSet<Territory> visited = new HashSet<Territory>();
    queue.add(action.getSource());
    visited.add(action.getSource());
    while (!queue.isEmpty()) {
      Territory front = queue.poll();
      if (front.equals(action.getDestination())) {
        return null;
        //TODO String value of shortest path?
      }
      for (Territory t : front.getNeighbors()) {
        if (t.getOwner().equals(action.getPlayer()) && !visited.contains(t)) {
          queue.add(t);
          visited.add(action.getSource());
        }
      }
    }
    return "This action is invalid: " + action.getSource().getName() + " is not connected to " + action.getDestination().getName() + ".";
  }
}

