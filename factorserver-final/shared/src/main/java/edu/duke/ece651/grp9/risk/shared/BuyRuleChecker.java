package edu.duke.ece651.grp9.risk.shared;

/**
 * Checks if buying positive number of Units
 *
 * @author PROY
 * @since 20 April 2022
 */
public class BuyRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public BuyRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if buying positive number of Units
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (action.getNumUnits() < 0) {
      return "This action is invalid: cannot buy negative units.";
    }
    return null;
  }
}

