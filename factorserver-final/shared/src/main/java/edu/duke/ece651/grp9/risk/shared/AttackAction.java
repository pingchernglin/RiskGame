package edu.duke.ece651.grp9.risk.shared;

import java.util.Random;

/**
 * This class is to define player's attack Actions from his territory to the enemy's territory.
 */
public class AttackAction implements Action {
    private final Player attacker;
    private final Territory source;
    private final Territory destination;
    private int attackUnits;
    private final RuleChecker attackChecker;
    private boolean win; // for testing only
    private int unitLevel;

    /*
     * Constructor to create an Attack
     * @param player is the Player performing the Action
     * @param source is the Territory we start an attack
     * @param destination is the Territory we are going to make an attack
     * @param numUnits is the number of units we attack from source to destination
     * @param moveChecker checks if an Attack  is valid
     */
    public AttackAction(Player player, Territory source, Territory destination, int numUnits) {
        this.source = source;
        this.destination = destination;
        this.attackUnits = numUnits;
        this.attacker = player;
        this.attackChecker = new UnitsRuleChecker(new OwnerRuleChecker(new AttackRuleChecker(null)));
        this.win = false;  // default battle status as false
        this.unitLevel = 0;
        source.syncUnits();
        destination.syncUnits();
    }

    /*
     * Constructor to create an Attack
     * @param player is the Player performing the Action
     * @param source is the Territory we start an attack
     * @param destination is the Territory we are going to make an attack
     * @param numUnits is the number of units we attack from source to destination
     * @param moveChecker checks if an Attack  is valid
     * @param unitLevel is the Unit level we are attacking with
     */
    public AttackAction(Player player, Territory source, Territory destination, int numUnits, int unitLevel) {
        this(player, source, destination, numUnits);
        this.unitLevel = unitLevel;
        source.syncUnits(unitLevel);
        destination.syncUnits(unitLevel);
    }

    @Override
    public String canPerformAction() {
        return attackChecker.checkAction(this);
    }

    @Override
    //TODO alternate between highest level and lowest level for each Territory
    public void performAction() {
//        //source.setUnits(source.getUnits(unitLevel) - attackUnits, unitLevel);
//        int defenderUnit = destination.getUnits(unitLevel);
//        Player defender = destination.getOwner();
//
//        while (defenderUnit > 0 && attackUnits> 0) {
//            if (isSuccessAttack()) {
//                defenderUnit--;
//            } else {
//                attackUnits--;
//            }
//        }
//        if (attackUnits > 0) {
//            win = true;
//            // if attacker wins the round, reset the unit and owner
//            destination.setOwner(attacker);
//            destination.setUnits(attackUnits, unitLevel);
//            // if attacker wins the round, add the unit to attacker territory list
//            attacker.addTerritory(destination);
//            defender.removeTerritory(destination);
//        } else {
//            // if defender wins the round, reset the unit
//            destination.setUnits(defenderUnit, unitLevel);
//        }
    }

    /**
     * This method is to check if the attack is success or not
     * Roll two 20-sided dice for attacker and defender, and check which side wins the game.
     * The player with larger number wins; the defender wins if in a tie.
     * @return true if attacker makes a successful attack, false if fail
     */
//    private boolean isSuccessAttack() {
//        Random attackRoll = new Random();
//        Random defenderRoll = new Random();
//        int roll1 = attackRoll.nextInt(20); //TODO call applyBonus()
//        int roll2 = defenderRoll.nextInt(20); //TODO call applyBonus()
//        return roll1 > roll2;
//    }

    /**
     * get the unit numbers for attack
     * @return attack units
     */
    public int getNumUnits() {
        return attackUnits;
    }

    /**
     * get the destination territory
     * @return the destination
     */
    public Territory getDestination() {
        return destination;
    }

    /**
     * get the source territory
     * @return source
     */
    public Territory getSource() {
        return source;
    }

    /**
     * get the attacker for the attack action
     * @return attacker
     */
    public Player getPlayer() {
        return attacker;
    }

    /**
     * get the game status -> for testing only
     * @return boolean status for win
     */
//    public boolean getState() {
//        return win;
//    }

    /**
     * Getter for the Unit level of this Attack
     *
     * @return int Unit level of this Attack
     */
    public int getUnitLevel() {
        return unitLevel;
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
     * check if attack is the same orig attack.
     * same origin attack: if player 1 attacks territory X with units from multiple
     * of her own territories, the attacks count as a single combined force.
     * @param att another attack
     * @return true if can be combined, false if cannot be combined
     */
    public boolean isSameOriAttack(AttackAction att) {
        return this.attacker.equals(att.getPlayer()) && this.destination.equals(att.getDestination());
    }

    /**
     * reset the attack units if the attacks are the sameOriAttack
     * eg: Player 1 attacks 5 units from territory A to D
     * Player 1 attacks 3 units from territory B to D
     * Player 1 attacks 8 units to D
     * @param units
     */
    //TODO how to handle this for different Unit types?
    public void setAttackUnits(int units) {
        this.attackUnits = units;
    }

    /**
     * cost 1 food per attack unit
     * @return the # of food consumption
     */
    public int computeCost() {
        return attackUnits;
    }
}
