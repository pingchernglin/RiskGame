package edu.duke.ece651.grp9.risk.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class GamePlay {
  public HashMap<String, String> userPassPairs;
  public HashMap<User, Socket> UserList;
  public GamePlay() {
    this.userPassPairs = new HashMap<String, String>();
    this.UserList = new HashMap<>();
  }


  /**
   * Set units for player's Territories based on input from Client
   *
   * @param unitString String of unit values from client
   * @param player     Player whose units are being added to their Territories
   */
  public void playerUnitSetting(String unitString, Player player) {

    String[] words = unitString.split(" ");

    int i = 0;
    for (Territory ter : player.getTerritoryList()) {
      ter.setUnit(Integer.parseInt(words[i]));
      ter.setUnits(Integer.parseInt(words[i]), 0);
      i++;
    }
  }

  
  public void unitSetting(ObjectOutputStream stream, Player player) {
    StringBuilder sb = new StringBuilder();
    sb.append("You have " + player.getTerritoryNumber() + " territories: ");
    for (Territory ter : player.getTerritoryList()) {
      sb.append(ter.getName() + " ");
    }
    sb.append("\n");
    sb.append("You have 30 total units, how do you want to place the units?");
    try {
      //stream.writeObject(sb.toString());
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Find Player bin map based on color input
   *
   * @param color String color input
   * @param m     Map we are searching for Player
   * @return returns null if no Player found, returns Player if found
   */
  public Player findPlayer(String color, Map m) {
    HashSet<Player> list = m.getPlayer();
    Iterator<Player> it = list.iterator();
    while (it.hasNext()) {
      Player pyr = it.next();
      if (pyr.getName().equals(color)) {
        return pyr;
      }
    }
    return null;
  }

  /**
   * Creates an Action given String input from client
   *
   * @param map    Map we are checking to see if Territory and Player exists
   * @param action String input from client to be converted to Action
   * @param isMove boolean to indicate if creating MoveAction or AttackAction
   * @return Action as indicated by client
   */
  public Action createAction(Map map, String color, String action, boolean isMove) {
    int numUnits = 0;
    int unitLevel = 0;

    String[] words = action.split(" ");
    Player player = map.findPlayer(color);
    Territory source = map.findTerritory(words[0]);
    Territory destination = map.findTerritory(words[1]);
    try {
      numUnits = Integer.parseInt(words[2]);
    } catch (NumberFormatException e) {
    }

    try {
      unitLevel = Integer.parseInt(words[3]);
    } catch (NumberFormatException e) {
    }

    if (source == null || destination == null) {
      return null;
    }

    if (isMove) {
      return new MoveAction(player, source, destination, numUnits, unitLevel);
    } else {
      return new AttackAction(player, source, destination, numUnits, unitLevel);
    }
  }


  /**
   * create a upgrade action
   * @param map
   * @param color
   * @param action
   * @return
   */
  public Action createUpgrade(Map map, String color, String action) {
    int numUnits = 0;
    int unitLevelStart = 0;
    int unitLevelEnd = 0;

    String[] words = action.split(" ");
    Player player = map.findPlayer(color);
    Territory source = map.findTerritory(words[0]);

    if (source == null) {
      return null;
    }

    try {
      numUnits = Integer.parseInt(words[1]);
    } catch (NumberFormatException e) {
    }

    try {
      unitLevelStart = Integer.parseInt(words[2]);
    } catch (NumberFormatException e) {
    }

    try {
      unitLevelEnd = Integer.parseInt(words[3]);
    } catch (NumberFormatException e) {
    }

    return new UpgradeAction(player, source, numUnits, unitLevelStart, unitLevelEnd);
  }


  public Action createCloak(Map map, String color, String action) {
    //String[] words = action.split(" ");
    Player player = map.findPlayer(color);
    Territory source = map.findTerritory(action);
    if (source == null) {
      return null;
    }
    return new CloakAction(player, source);

  }

  public Action createProtect(Map map, String color, String action) {
    //String[] words = action.split(" ");
    Player player = map.findPlayer(color);
    Territory source = map.findTerritory(action);
    if (source == null) {
      return null;
    }
    return new ProtectAction(player, source);
  }

  public Action createBuy(Map map, String color, String action) {
    int numUnits = 0;
    String[] words = action.split(" ");
    Player player = map.findPlayer(color);
    Territory source = map.findTerritory(words[0]);

    try {
      numUnits = Integer.parseInt(words[1]);
    } catch (NumberFormatException e) {
    }

    if (source == null) {
      return null;
    }

    return new BuyAction(player, source, numUnits);
  }

  /**
   * Checks is a set of Actions from client is valid
   *
   * @param player  Player who made actions
   * @param moves   MoveActions that are checked
   * @param attacks AttackActions that are checked
   * @return null if no error, String describing problem if there is error
   */
  public String validActionSet(Player player, HashSet<MoveAction> moves, HashSet<AttackAction> attacks,
      HashSet<UpgradeAction> upgrades, boolean techLevelUpgrade, boolean research, HashSet<CloakAction> cloaks, HashSet<ProtectAction> protects, HashSet<BuyAction> buys) {
    int foodCost = 0;
    int moneyCost = 0;
    //Once we first meet the problem, then reenter with "Done", moves and attacks would be "NULL"
    if (moves.isEmpty() && attacks.isEmpty() && upgrades.isEmpty() && !techLevelUpgrade && !research && cloaks.isEmpty() && protects.isEmpty() && buys.isEmpty()) {
      return null;
    }

    for (ProtectAction protect: protects) {
      if (protect == null) {
        return "This action is invalid: Territory does not exist";
      }
      String err = protect.canPerformAction();
      moneyCost += protect.computeCost();
      if (err != null) {
        return err;
      }
    }

    if (moneyCost > player.getMoneyQuantity()) {
      return "Do not have enough food to protect a territory";
    }


    for (MoveAction move : moves) {
      if (move == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = move.canPerformAction();
      foodCost += move.computeCost();
      if (error != null) {
        return error;
      }
    }

    for (AttackAction attack : attacks) {
      if (attack == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = attack.canPerformAction();
      foodCost += attack.computeCost();
      if (error != null) {
        return error;
      }
    }

    for (UpgradeAction upgrade: upgrades) {
      if (upgrade == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = upgrade.canPerformAction();
      moneyCost += upgrade.computeCost();
      if (error != null) {
        return error;
      }
    }

    for (BuyAction buy: buys) {
      if (buy == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = buy.canPerformAction();
      moneyCost += buy.computeCost();
      if (error != null) {
        return error;
      }
    }

    // check if have enough resource
    if (foodCost > player.getFoodQuantity()) {
      return "Do not have enough food to do move or attack orders";
    }

    if (techLevelUpgrade) {
      TechAction techAction = new TechAction(player);
      String error = techAction.canPerformAction();
      moneyCost += techAction.computeCost();
      if (error != null) {
        return error;
      }
    }

    if (moneyCost > player.getMoneyQuantity()) {
      return "Do not have enough money to do upgrade/buy orders";
    }

    if (research) {
      ResearchAction researchAction = new ResearchAction(player);
      String error = researchAction.canPerformAction();
      moneyCost += researchAction.computeCost();
      if (error != null) {
        return error;
      }
    }

    if (moneyCost > player.getMoneyQuantity()) {
      return "Do not have enough money to do research orders";
    }

    for (CloakAction cloak: cloaks) {
      if (cloak == null) {
        return "This action is invalid: Territory does not exist";
      }
      String error = cloak.canPerformAction();
      moneyCost += cloak.computeCost();
      if (error != null) {
        return error;
      }
    }

    if (moneyCost > player.getMoneyQuantity()) {
      return "Do not have enough money to do cloak orders";
    }

    for (Territory territory : player.getTerritoryList()) {
      if (!territory.mockIsValid(player)) {
        return "These actions are invalid: " + territory.getName()
                + " territory ends with negative units";
      }
    }

    return null;
  }



  /**
   * Sends message to client to indicate win or lose
   *
   * @param stream OutputStream for client
   * @param color  String indicating which Player
   * @param map    Map
   */
  public void gameWinner(ObjectOutputStream stream, String color, Map map) throws IOException {
    stream.reset();
    stream.writeObject(map);
    System.out.println("Send map : there is a winner.");

    Player winner = map.getGameWinner();
    if (winner.equals(findPlayer(color, map))) {
      stream.reset();
      stream.writeObject("win");
      System.out.println("write win to player");
    } else {
      stream.reset();
      stream.writeObject("game over");
      System.out.println("write game over to player");
    }
    stream.close();
  }

  /**
   * play all received attacks
   *
   * @param attacks received attacks
   */
  public void playAttacks(Map map, HashSet<AttackAction> attacks) {
    Battle battle = new Battle(map);
    for (AttackAction att : attacks) {
      battle.addAttackAction(att);
    }
    battle.playBattlePhase();
  }

  public void playMoves(HashSet<MoveAction> moves) {
    for (MoveAction move : moves) {
      move.performAction();
    }
  }

  public void playUpgrades(HashSet<UpgradeAction> allUpgrades) {
    for (UpgradeAction upgrade: allUpgrades) {
      upgrade.performAction();
    }
  }

  public void playTechLevels(HashSet<TechAction> allTechLevels) {
    for (TechAction techAction: allTechLevels) {
      if (techAction != null) {
        techAction.performAction();
      }
    }
  }

  public void playResearch(HashSet<ResearchAction> allResearch) {
    for (ResearchAction researchAction: allResearch) {
      if (researchAction != null) {
        researchAction.performAction();
      }
    }
  }

  public void playCloak(HashSet<CloakAction> allCloaks) {
    for (CloakAction cloakAction: allCloaks) {
      if (cloakAction != null) {
        cloakAction.performAction();
      }
    }
  }


  public void playProtect(HashSet<ProtectAction> allProtects) {
    for (ProtectAction protectAction: allProtects) {
      if (protectAction != null) {
        protectAction.performAction();
      }
    }
  }

  public void playBuy(HashSet<BuyAction> allBuys) {
    for (BuyAction buyAction: allBuys) {
      if (buyAction != null) {
        buyAction.performAction();
      }
    }
  }


  /**
   * to store the user_name and password that sent by the client
   *
   */
  public String storeUserNameAndPassword(ObjectInputStream objectInputStream) throws IOException {
    try {
      System.out.println("In server username and password part.");
      String username = (String) objectInputStream.readObject();
      String password = (String) objectInputStream.readObject();
      System.out.println("username is" + username);
      System.out.println("password is" + password);
      //to test whether we have this username before or not and the password are matched or not.
      for (String key: userPassPairs.keySet()){
        System.out.println(key);
      }

      if (userPassPairs.containsKey(username)) {
        System.out.println("The username already exists");
        if (!userPassPairs.get(username).equals(password)) {
          return null;
        }
        else{
          return username;
        }
      } else {
        userPassPairs.put(username, password);
        System.out.println("Record in the userPassPairs");
        return username;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  
  
}
