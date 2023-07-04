package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;


public class ActionRuleChecker {

  /**
   * The method deal with the option when a gamer has lost the game.
   * The player have two options: Q for quit or C for continuing watching.
   * The valid inputs are: 'q' or 'Q' or 'C' or 'c'.
   * @param input is the player's input selection
   * @return err msg
   */
  public String checkLoseAction(String input) {
    if (input.length() != 1) {
      return "the input length is invalid, please enter again!";
    } else if (!(input.charAt(0) == 'Q' || input.charAt(0) == 'q' || input.charAt(0) == 'C'
        || input.charAt(0) == 'c')) {
      return "the input character is invalid, please enter again!";
    }
    return null;
  }

  /**
   * The method deal with the option when a gamer has lost the game.
   * The player has three actions: M for move, A for attack, and D for done.
   * The valid inputs are: 'm' or 'M' or 'A' or 'a' or 'd' or 'D'.
   * @param input is the user input
   * @return err msg
   */
  public String checkAction(String input) {
    if (input.length() != 1) {
      return "the input length is invalid, please enter again!";
    } else if (!(input.charAt(0) == 'M' || input.charAt(0) == 'A' || input.charAt(0) == 'D'
        || input.charAt(0) == 'm' || input.charAt(0) == 'a' || input.charAt(0) == 'd'
        || input.charAt(0) == 'u' || input.charAt(0) == 'U')) {
      return "the input character is invalid, please enter again!";
    }
    return null;
  }

  /**
   * The method is to check it the input color is valid
   * @param input is the user input color, eg: "red", "blue"
   * @param remainingColors is the valid colors we have
   * @return err msg
   */
  public String checkColor(String input, HashSet<String> remainingColors) {
    if (!remainingColors.contains(input)) {
      return "The color you input is invalid, please enter again";
    }
    return null;
  }

  /**
   * The method is to check if the input unit number is valid
   * @param input the user input unit for n territories. eg: 10 10 10 for three territories
   * @param player the Player who does the actions
   * @return err msg
   */
  public String checkUnit(String input, Player player) {
    String[] words = input.split(" ");

    if (words.length != player.getTerritoryList().size()) {
      return "The input is invalid: Must enter " + player.getTerritoryList().size() + " separate numbers.";
    }

    int sum = 0;
    for (int i = 0; i < words.length; i++) {
      int numUnits = 0;
      try {
        numUnits = Integer.parseInt(words[i]);
      } catch (NumberFormatException e) {
        return "The input is invalid: Input must only be numbers.";
      }
      if (numUnits < 0) {
        return "The input is invalid: Territory cannot have negative units.";
      }
      sum += numUnits;
    }

    if (sum != 30) {
      return "This input is invalid: Sum of units must equal 30.";
    }
    
    return null;
  }
}