package edu.duke.ece651.grp9.risk.shared;

/**
 * This class makes sure a Territory has enough units in it for the Action
 *
 * @author PROY
 * @since 10 March 2022
 */
public class UnitsRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public UnitsRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Performs mockActions to make sure Territories have enough units after all moves are made
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (action.getNumUnits() < 0) {
      return "This action is invalid: number of units must be positive int.";
    }

    action.getSource().mockActions(action.getPlayer(), action.getDestination(), action.getNumUnits(),
        action.getUnitLevel()); //EVOLUTION 2
    return null;
  }
}

