package edu.duke.ece651.grp9.risk.shared;

/**
 * This class makes sure the source Territory is owned the player
 *
 * @author PROY
 * @since 10 March 2022
 */
public class OwnerRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public OwnerRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is owned by player
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (action.getUnitLevel() < 7 && !action.getSource().getOwner().equals(action.getPlayer())) {
      return "This action is invalid: you do not own " + action.getSource().getName() + ".";
    }
    return null;
  }
}

