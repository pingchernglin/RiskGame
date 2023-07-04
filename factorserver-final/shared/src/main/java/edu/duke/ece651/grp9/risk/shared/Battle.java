package edu.duke.ece651.grp9.risk.shared;

import java.util.*;

/**
 * This class is to handle all attacks in a battle round. All attacks would be executed after all
 * move actions completed.
 */
public class Battle {

  // The general methodology is to first add all players' attack actions to the hashmap, then iteratively the
  // territory and do the battles on each territory.
  // private final Map territoryMap;
  // key: territory being attacked; value: combined attacks onto the territory
  private HashMap<Territory, HashSet<AttackAction>> territoryUnderAttack;
  private final Map territoryMap;

  /*
   * Constructor to create an Attack
   * @param map is a map for game
   */
  public Battle(Map map) {
    this.territoryMap = map;
    this.territoryUnderAttack = new HashMap<>();
  }

  /**
   * This method currently only used for testing
   */
  public List<AttackAction> getAllAttackActions() {
    List<AttackAction> res = new ArrayList<>();
    for (HashSet<AttackAction> attacks : territoryUnderAttack.values()) {
      for (AttackAction attack : attacks) {
        res.add(attack);
      }
    }
    return res;
  }

  /**
   * The method is to add attacks to the battle. If the attack comes from same player and attack to
   * the same destination, combing the attacks to a single force. Note that the combined attack's
   * source is the first attack's source
   *
   * @param oneAttack is the new attack action
   */
  public void addAttackAction(AttackAction oneAttack) {
    // consume food for each attack
    Player attacker = oneAttack.getPlayer();
    int foodQuantity = attacker.getFoodQuantity();
    attacker.setFoodQuantity(foodQuantity - oneAttack.computeCost());

    // Update the units in source defender.
    Territory source = oneAttack.getSource();
    source.setUnits(source.getUnits(oneAttack.getUnitLevel()) - oneAttack.getNumUnits(),
        oneAttack.getUnitLevel());
    source.setUnit(source.getUnit() - oneAttack.getNumUnits());
    // Add attack action to map
    Territory destination = oneAttack.getDestination();
    if (territoryUnderAttack.containsKey(destination)) {
      for (AttackAction att : territoryUnderAttack.get(destination)) {
        // combine attack actions from the same player of same tech level.
        if (att.isSameOriAttack(oneAttack) && oneAttack.getUnitLevel() == att.getUnitLevel()) {
          att.setAttackUnits(att.getNumUnits() + oneAttack.getNumUnits());
          return;
        }
      }
      // new attack from a new player with different tech level.
      territoryUnderAttack.get(destination).add(oneAttack);
    } else {
      HashSet<AttackAction> attackMap = new HashSet<>();
      attackMap.add(oneAttack);
      territoryUnderAttack.put(destination, attackMap);
    }
  }


  /**
   * this method is to get attacks grouped by players
   * @param attacks are the attacks on a specific territory
   * @return a hashmap that stores attacks grouped by player
   */
  private HashMap<Player, HashSet<AttackAction>> getAttacksByPlayer(HashSet<AttackAction> attacks ) {
    HashMap<Player, HashSet<AttackAction>> attacksByPlayer = new HashMap<>();
    for (AttackAction att: attacks) {
      Player p = att.getPlayer();
      if (attacksByPlayer.containsKey(p)) {
        attacksByPlayer.get(att.getPlayer()).add(att);
      } else {
        HashSet<AttackAction> attackActions = new HashSet<>();
        attackActions.add(att);
        attacksByPlayer.put(p, attackActions);
      }
    }
    return attacksByPlayer;
  }

  /**
   * The method is to play the battles on each territory. On each territory, there might be multiple
   * players attack the same destination. The hashset ensures the order on each territory is random
   */
  public void playBattlePhase() {
    for (HashSet<AttackAction> attacksAll : territoryUnderAttack.values()) {
      // get original owner of the attacked territory
      Player oriOwner = attacksAll.iterator().next().getDestination().getOwner();
      HashMap<Player, HashSet<AttackAction>> attacksByPlayer = getAttacksByPlayer(attacksAll);
      // a player starts a battle on a territory

      for (HashSet<AttackAction> attacks: attacksByPlayer.values()) {
        AttackAction attack = attacks.iterator().next();
        Player attacker = attack.getPlayer();
        Player defender = attack.getDestination().getOwner();
        Territory destination = attack.getDestination();
        HashMap<Integer, Unit> defenderUnits = destination.getAllUnits();
        HashMap<Integer, Unit> attackerUnits = getAllUnits(attacks);
        doOneAttack(attackerUnits,defenderUnits, attacker, defender, destination, oriOwner);
      }
    }
  }

  /**
   * convert the attackAction to Unit hashmap
   * @param attacks
   * @return
   */
  private HashMap<Integer, Unit> getAllUnits(HashSet<AttackAction> attacks) {
    HashMap<Integer, Unit> allAttacks = new HashMap<>();
    for (AttackAction att: attacks) {
      int level = att.getUnitLevel();
      int attackUnitNum = att.getNumUnits();
      Territory terr = att.getSource();
      Unit unit = terr.getUnitClass(level);
      Unit unitAttack = new Unit(unit);
      unitAttack.setNumUnits(attackUnitNum);
      allAttacks.put(level, unitAttack);
    }
    return allAttacks;
  }


  /**
   * get the Unit with the highest bonus
   * @param unitMap
   * @return
   */
  public Unit getHighestLevelUnit(HashMap<Integer, Unit> unitMap) {
    Unit unit = null;
    for (int i = 6; i >= 0; i--) {
      if (unitMap.get(i) != null && unitMap.get(i).getNumUnits() > 0) {
        unit = unitMap.get(i);
        break;
      }
    }
    return unit;
  }

  /**
   * get the Unit with the lowest bonus
   * @param unitMap
   * @return
   */
  public Unit getLowestLevelUnit(HashMap<Integer, Unit> unitMap) {
    Unit unit = null;
    for (int i = 0; i <= 6; i++) {
      if (unitMap.get(i) != null && unitMap.get(i).getNumUnits() > 0) {
        unit = unitMap.get(i);
        break;
      }
    }
    return unit;
  }

  /**
   * this method is to calculate the total number of Units for a given Unit hashset
   * @param playerUnits is the player's total units for this battle round
   * @return the num of the total units for this round
   */
  private int getBattleUnitNum(HashMap<Integer, Unit> playerUnits) {
    int battleUnitNum = 0;
    for (Integer i : playerUnits.keySet()) {
      battleUnitNum += playerUnits.get(i).getNumUnits();
    }
    return battleUnitNum;
  }

  /**
   * this method plays one attack from a attacker to a defender after combing attack force.
   * The attacker and the defender iteratively dominates the roll die
   * @param attackerUnits is the attacker's attack unit set
   * @param defenderUnits is the defender's defend unit set
   * @param attacker is the attacker player
   * @param defender is the defender player
   * @param destination is the attacked territory
   */
  public void doOneAttack(HashMap<Integer, Unit> attackerUnits, HashMap<Integer, Unit> defenderUnits, Player attacker, Player defender, Territory destination, Player oriOwner) {
    int iterIndex = 0;
    int protectedNum = 0;
    int defenderUnitSum = getBattleUnitNum(defenderUnits);
    int attackerUnitSum = getBattleUnitNum(attackerUnits);

    if (oriOwner.equals(defender) && destination.getIsProtected()) {
      protectedNum = 3;
    }
    // if two players both has unit left
    while ((defenderUnitSum > 0 || protectedNum > 0) && attackerUnitSum > 0) {
      Unit attackUnit;
      Unit defendUnit;
      if (iterIndex++ % 2 == 0) { // iteratively attack
        // attacker dominates with the highest level unit
        // if protectedNum greater than 0,
        attackUnit = getHighestLevelUnit(attackerUnits);
        defendUnit = protectedNum > 0? new Level0Unit():getLowestLevelUnit(defenderUnits);
      } else {
        // defender dominates with the highest level unit
        attackUnit = getLowestLevelUnit(attackerUnits);
        defendUnit = protectedNum > 0? new Level0Unit():getHighestLevelUnit(defenderUnits);
      }
      if (isSuccessBattle(attackUnit, defendUnit)) {
        // defender's unit decreased by 1
        if (protectedNum-- <= 0) {
          int level = defendUnit.getLevel();
          int unitNum = defendUnit.getNumUnits() - 1;
          defenderUnits.get(level).setNumUnits(unitNum); // update unitNum
        }
      } else {
        // attacker's unit decreased by 1
        int level = attackUnit.getLevel();
        int unitNum = attackUnit.getNumUnits() - 1;
        attackerUnits.get(level).setNumUnits(unitNum); // update unitNum
      }
      // update the total units for the defender and the attacker
      defenderUnitSum = getBattleUnitNum(defenderUnits);
      attackerUnitSum = getBattleUnitNum(attackerUnits);
    }

    // check the winner of the game
    // if the attacker make a success attack, reset owner and units
    if (attackerUnitSum > 0) {
      // reset territory owner, unit info
      destination.setOwner(attacker);
      for (Integer level: attackerUnits.keySet()) {
       destination.setUnits(attackerUnits.get(level).getNumUnits(), level);
      }
      destination.setUnit(attackerUnitSum);
      attacker.addTerritory(destination);
      defender.removeTerritory(destination);
      // once changed the ownership, the protection does not work anymore
      destination.resetProtected();
    } else {
      for (Integer level: defenderUnits.keySet()) {
        destination.setUnits(defenderUnits.get(level).getNumUnits(), level);
      }
      destination.setUnit(defenderUnitSum);
    }
  }

  /**
   * check if the attack from player A to territory x is success or not
   * @param attack is the attacker's unit
   * @param defender is the defender's unit
   * @return
   */
  private boolean isSuccessBattle(Unit attack, Unit defender) {
    Random attackRoll = new Random();
    Random defenderRoll = new Random();
    int roll1 = attackRoll.nextInt(20);
    int roll2 = defenderRoll.nextInt(20);
    int attackNum = attack.applyBonus(roll1);
    int defendNum = defender.applyBonus(roll2);
    //System.out.println("attack:" + attackNum + " defender: " + defendNum);
    return attackNum > defendNum;
  }
}
