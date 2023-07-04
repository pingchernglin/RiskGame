package edu.duke.ece651.grp9.risk.shared;

/**
 * research action enables the player to gain the ability of clocking
 */
public class ResearchAction implements Action {
    private final Player player;
    private final RuleChecker researchChecker;
    private final int researchCost;

    /**
     * Constructor to create a Move
     * @param player is the Player performing the Action
     */
    public ResearchAction(Player player) {
        this.player = player;
        this.researchChecker = new ResearchRuleChecker(null);
        this.researchCost = 100;
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
        return null;
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
        return -1;
    }

    /**
     * Getter for Unit level
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
        return researchChecker.checkAction(this);
    }

    /**
     * Perform move on source and destination Territories
     */
    public void performAction() {
        player.setMoneyQuantity(player.getMoneyQuantity() - computeCost());
        player.doResearch();
    }

    @Override
    public int computeCost() {
        // if player has gain the reserach ability, cost 0
        if (!player.getResearched()) {
            return researchCost;
        } else {
            return 0;
        }
    }

}
