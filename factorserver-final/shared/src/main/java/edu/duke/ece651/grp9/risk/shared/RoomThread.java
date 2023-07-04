package edu.duke.ece651.grp9.risk.shared;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class RoomThread extends Thread {
    private ArrayList<String> remainingColors;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ArrayList<Socket> socketList;
    private ArrayList<String> playerList;
    public ArrayList<ObjectInputStream> InputList;
    public ArrayList<ObjectOutputStream> OutputList;
    private HashSet<MoveAction> allMoves = new HashSet<>();
    private HashSet<AttackAction> allAttacks = new HashSet<>();
    private HashSet<UpgradeAction> allUpgrades = new HashSet<>();
    private HashSet<TechAction> techActions = new HashSet<>();
    private HashSet<ResearchAction> researchActions = new HashSet<>();
    private HashSet<CloakAction> allCloaks = new HashSet<>();
    private HashSet<ProtectAction> allProtects = new HashSet<>();
    private HashSet<BuyAction> allBuys = new HashSet<>();

    public Room room;
    public int player_num;

    public RoomThread(Room room) {
        this.room = room;
        this.player_num =room.map.getPlayerNum();
        playerList = new ArrayList<String>();
        InputList = new ArrayList<ObjectInputStream>();
        OutputList = new ArrayList<ObjectOutputStream>();
        allMoves = new HashSet<MoveAction>();
        allAttacks = new HashSet<AttackAction>();
        allUpgrades = new HashSet<UpgradeAction>();
        techActions = new HashSet<TechAction>();
        researchActions = new HashSet<ResearchAction>();
        remainingColors = new ArrayList<String>();
        allCloaks = new HashSet<CloakAction>();
        allProtects = new HashSet<ProtectAction>();
        allBuys = new HashSet<BuyAction>();
    }

    @Override
    public void run() {
      try{

        System.out.println("Status: enter the room thread");
        Map m = room.map;
        remainingColors = new ArrayList<>();
        Iterator<Player> it = m.getPlayer().iterator();
        while (it.hasNext()) {
            remainingColors.add(it.next().getName());
        }

        ArrayList<ServerThread> serverThreadList = new ArrayList<>();
        ArrayList<ActionThread> ActionThreadList = new ArrayList<>();
        Socket socket = null;
        GamePlay gamePlay = new GamePlay();

            int i = 0;

//for part 1 - initial placement
            while (i < player_num) {
                System.out.println("Status: ready to go to server thread, i=" + i);
                
                //add the checker
                /**
                Before unit setting, sending map to the client.
                 */
                ObjectOutputStream objectOutputStream =OutputList.get(i);
                ObjectInputStream objectInputStream = InputList.get(i);

                ServerThread serverThread = new ServerThread(socket, serverThreadList, m, objectInputStream, objectOutputStream, remainingColors.get(i));
                serverThreadList.add(serverThread);
                serverThread.start();
                m = serverThread.m;
                i++;
            }

            for (int n = 0; n < serverThreadList.size(); ++n) {
                System.out.println("Status: server thread join");
                serverThreadList.get(n).join();
            }

            //for part 2 - for action part
            //while the game is not over?

            
            while (m.getGameWinner() == null) {

                for (int j = 0; j < player_num; j++) {
                    //how to update
                    OutputList.get(j).reset();
                    OutputList.get(j).writeObject(m); // # write 001 map
                    System.out.println("Status: sent the map");
                }
                for (int j = 0; j < player_num; j++) {
                    OutputList.get(j).reset();
                    OutputList.get(j).writeObject("keep going"); // # write 002 (end game)
                    System.out.println("Status: sent keep going");

                    /*********************************/
                    //debug
                    String action = (String) InputList.get(j).readObject();  // # read 001 (no action, quit, continue)
                    System.out.println("Status: read action: " + action);
                    gamePlay.findPlayer(remainingColors.get(j), m).setLoseStatus(action);
                    /**********************************/

                    Player tmp = gamePlay.findPlayer(remainingColors.get(j), m);

                    /*************adding new parts***/
                    if (tmp.isLose() && tmp.getLoseStatus().equals("quit") && m.getPlayer().contains(tmp)) {
                        
                            //remove it from player list
                            //auto set empty actionSet
                            System.out.println("Status: lose status = quit");
                            InputList.remove(j);
                            OutputList.remove(j);
                            remainingColors.remove(j);
                            player_num--;
                            j--;
                        
                    } 
                    else {
                        System.out.println("Status: enter actionThread");
                        ActionThread actionThread = new ActionThread(m, InputList.get(j), OutputList.get(j),
                            tmp, allMoves, allAttacks, allUpgrades, techActions, researchActions, allCloaks, allProtects, allBuys);
                        ActionThreadList.add(actionThread);
                        actionThread.start();
                        allMoves.addAll(actionThread.allMove);
                        allAttacks.addAll(actionThread.allAttack);
                        allUpgrades.addAll(actionThread.allUpgrade);
                        techActions.addAll(actionThread.techActions);
                        researchActions.addAll(actionThread.researchAction);
                        allCloaks.addAll(actionThread.allCloak);
                        allProtects.addAll(actionThread.allProtect);
                        allBuys.addAll(actionThread.allBuy);

                    }
                }

                //after all the actions, they should be merged
                for (int k = 0; k < ActionThreadList.size(); ++k) {
                    ActionThreadList.get(k).join();
                }

                int j = 0;
                System.out.println("perform all actions");
                gamePlay.playProtect(allProtects);
                gamePlay.playMoves(allMoves);
                gamePlay.playBuy(allBuys);
                gamePlay.playAttacks(m, allAttacks);
                gamePlay.playUpgrades(allUpgrades);
                gamePlay.playTechLevels(techActions);
                gamePlay.playResearch(researchActions);
                gamePlay.playCloak(allCloaks);
                // increase the basic unit per terr, produce resource
                m.upgradeMapPerRound();
                allMoves.clear();
                allBuys.clear();
                allAttacks.clear();
                allUpgrades.clear();
                techActions.clear();
                researchActions.clear();
                allCloaks.clear();
                allProtects.clear();
            }

            //for part 3 - for game winner and end this game
            for (int t = 0; t < player_num; t++) {
                System.out.println("go to the get winner phase");
                gamePlay.gameWinner(OutputList.get(t), remainingColors.get(t), m);
            }
            System.out.println("Final point");
            TimeUnit.SECONDS.sleep(20);


        } catch (Exception e) {
            System.out.println(e);
        }



    }

}

