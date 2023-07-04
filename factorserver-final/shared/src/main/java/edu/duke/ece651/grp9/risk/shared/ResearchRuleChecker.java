package edu.duke.ece651.grp9.risk.shared;

public class ResearchRuleChecker extends RuleChecker {
    /**
     * Adds the next Action rule to our list
     *
     * @param next Action rule in list
     */
    public ResearchRuleChecker(RuleChecker next) {
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
        if (action.getPlayer().getTechLevel() < 3) {
            return "This action is invalid: you cannot do research at the tech level lower than 3.";
        }
        return null;
    }
}
