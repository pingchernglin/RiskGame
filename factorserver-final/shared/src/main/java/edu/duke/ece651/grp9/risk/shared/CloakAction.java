package edu.duke.ece651.grp9.risk.shared;

public class CloakAction implements Action {
    private final Player player;
    private final RuleChecker cloakChecker;
    private final Territory source;
    private final int clockCost;

    /**
     * Constructor to create a Move
     * @param player is the Player performing the Action
     */
    public CloakAction(Player player,  Territory source) {
        this.player = player;
        this.cloakChecker = new OwnerRuleChecker(new CloakRuleChecker(null));
        this.clockCost = 20;
        this.source = source;
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
        return cloakChecker.checkAction(this);
    }

    /**
     * Perform move on source and destination Territories
     */
    public void performAction() {
        player.setMoneyQuantity(player.getMoneyQuantity() - computeCost());
        source.doClockOnTerritory();
    }

    @Override
    public int computeCost() {
        // if player has gain the reserach ability, cost 0
        return clockCost;
    }
}
