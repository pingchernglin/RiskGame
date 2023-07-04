package edu.duke.ece651.grp9.risk.shared;

public class ProtectAction implements Action{
    private final Player player;
    private final RuleChecker protectChecker;
    private final int protectCost;
    private final Territory source;

    /**
     * Constructor to create a Protection of the territory
     * @param player is the Player performing the Action
     */
    public ProtectAction(Player player, Territory source) {
        this.player = player;
        this.protectChecker = new OwnerRuleChecker(null);
        this.protectCost = 60;
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
        return protectChecker.checkAction(this);
    }

    /**
     * Perform move on source and destination Territories
     */
    public void performAction() {
        player.setMoneyQuantity(player.getMoneyQuantity() - computeCost());
        source.doProtect();
    }

    @Override
    public int computeCost() {
        if (source.getIsProtected()) {
            return 0;
        } else {
            return protectCost;
        }
    }
}
