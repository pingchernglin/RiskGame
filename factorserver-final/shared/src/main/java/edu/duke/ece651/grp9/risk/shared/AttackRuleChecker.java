package edu.duke.ece651.grp9.risk.shared;

/**
 * This class makes sure the source Territory is adjacent to destination Territory
 *
 * @author PROY
 * @since 10 March 2022
 */
public class AttackRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public AttackRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is adjacent to destination Territory
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (!action.getSource().getNeighbors().contains(action.getDestination())) {
      return "This action is invalid: " + action.getSource().getName() + " is not adjacent to " + action.getDestination().getName() + ".";
    } else if (action.getDestination().getOwner().equals(action.getPlayer())) {
      return "This action is invalid: you cannot attack your own Territory.";
    }
    return null;
  }
}

