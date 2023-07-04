package edu.duke.ece651.grp9.risk.shared;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class ActionThread extends Thread{
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;
  private Map m;
  private Player player;
  public HashSet<MoveAction> allMove = new HashSet<>();
  public HashSet<AttackAction> allAttack = new HashSet<>();
  public HashSet<UpgradeAction> allUpgrade = new HashSet<>();
  public HashSet<TechAction> techActions;
  public HashSet<ResearchAction> researchAction;
  public HashSet<CloakAction> allCloak;
  public HashSet<ProtectAction> allProtect;
  public HashSet<BuyAction> allBuy;

  public ActionThread(Map m, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
      Player player, HashSet<MoveAction> allMoves, HashSet<AttackAction>allAttack,
      HashSet<UpgradeAction> allUpgrade, HashSet<TechAction> techActions, HashSet<ResearchAction> researchAction,
                      HashSet<CloakAction> allCloak, HashSet<ProtectAction> allProtect, HashSet<BuyAction> allBuy) {
    this.m = m;
    this.objectInputStream = objectInputStream;
    this.objectOutputStream = objectOutputStream;
    this.player = player;
    this.allMove = allMoves;
    this.allAttack = allAttack;
    this.allUpgrade = allUpgrade;
    this.techActions = techActions;
    this.researchAction = researchAction;
    this.allCloak = allCloak;
    this.allProtect = allProtect;
    this.allBuy = allBuy;
  }

  @Override
    public void run() {
    GamePlay gamePlay =new GamePlay();
      try{
        while (true) {
          HashSet<MoveAction> moveActions = new HashSet<>();
          HashSet<AttackAction> attackActions = new HashSet<>();
          HashSet<UpgradeAction> upgradeActions = new HashSet<>();
          HashSet<CloakAction> cloakActions = new HashSet<>();
          HashSet<ProtectAction> protectActions = new HashSet<>();
          HashSet<BuyAction> buyActions = new HashSet<>();

          System.out.println("ready to read actionSet From " + player.getName());

          ActionSet actionSet = (ActionSet) objectInputStream.readObject(); // read 002 (actionSet)

          System.out.println("Status: Get action from " + player.getName());

          HashSet<String> actionListMove = actionSet.getMoveList();
          for (String move : actionListMove) {
            moveActions.add((MoveAction) gamePlay.createAction(m, player.getName(), move, true));
          }

          HashSet<String> actionListAttack = actionSet.getAttackList();
          for (String attack : actionListAttack) {
            attackActions.add((AttackAction) gamePlay.createAction(m, player.getName(), attack, false));
          }

          HashSet<String> actionListUpgrade = actionSet.getUpgradeList();
          for (String upgrade : actionListUpgrade) {
            upgradeActions.add((UpgradeAction) gamePlay.createUpgrade(m, player.getName(), upgrade));
          }

          HashSet<String> actionListCloak = actionSet.getCloakList();
          for (String cloak : actionListCloak) {
            cloakActions.add((CloakAction) gamePlay.createCloak(m, player.getName(), cloak));
          }

          HashSet<String> actionListProtect = actionSet.getProtectList();
          for (String protect : actionListProtect) {
            protectActions.add((ProtectAction) gamePlay.createProtect(m, player.getName(), protect));
          }

            HashSet<String> actionListBuy = actionSet.getBuyList();
            for (String buy : actionListBuy) {
                buyActions.add((BuyAction) gamePlay.createBuy(m, player.getName(), buy));
            }

          //moveActions  attackActions need to be reset in the next round.
          String actionProblem = gamePlay.validActionSet(player, moveActions, attackActions,
              upgradeActions, actionSet.techLevelUpgrade, actionSet.doResearch, cloakActions, protectActions, buyActions);
          //debug：here should be reset
          objectOutputStream.reset();
          objectOutputStream.writeObject(actionProblem); //write 003 (send action problem)
          System.out.println("Status: writing actionProblem to client: " +actionProblem );
          if (actionProblem == null) {
            allMove.addAll(moveActions);
            allAttack.addAll(attackActions);
            allUpgrade.addAll(upgradeActions);
            allCloak.addAll(cloakActions);
            allProtect.addAll(protectActions);
            allBuy.addAll(buyActions);

            if (actionSet.techLevelUpgrade) {
              techActions.add(new TechAction(player));
            }
            if (actionSet.doResearch) {
              researchAction.add(new ResearchAction(player));
            }

            break;
          }

          System.out.println("There are problems with this client's setting, send information back to the server");
        }
      }
      catch(Exception e){
        System.out.println("Error occured " + e.getMessage());
      }
  }
}
