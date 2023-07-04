package edu.duke.ece651.grp9.risk.shared;

/**
 * This class makes sure the Tech Action has enough Money to upgrade
 *
 * @author PROY
 * @since 2 April 2022
 */
public class TechRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public TechRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if a Player is allowed to upgrade it's tech level
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (action.getPlayer().getTechLevel() > 5) {
      return "This action is invalid: You are already at the max tech level.";
    }

    return null;
  }
}