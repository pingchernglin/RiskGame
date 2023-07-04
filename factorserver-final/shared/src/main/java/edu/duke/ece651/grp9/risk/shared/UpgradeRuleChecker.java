package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class makes sure the Upgrade Action has enough Money to upgrade
 *
 * @author PROY
 * @since 29 March 2022
 */
public class UpgradeRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public UpgradeRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is connected to destination Territory
   *
   * @param action Action we are checking rules against
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Action action) {
    if (action.getEndLevel() != 7 && action.getPlayer().getTechLevel() < action.getEndLevel()) {
      return "This action is invalid: Your technology level is not yet at level " + action.getEndLevel() + ".";
    }
    if (action.getUnitLevel() >= action.getEndLevel()) {
      return "This action is invalid: Unit can only increase in level.";
    }
    //Enough Money to upgrade - mock money?
//    if (action.computeCost() < player.getMoney()) {
//      return null;
//    }
    //TODO action.getPlayer().mockMoney(action.computeCost());

    return null;
  }
}

