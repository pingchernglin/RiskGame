package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.ActionSet;
import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.Player;
import edu.duke.ece651.grp9.risk.shared.Spy;
import edu.duke.ece651.grp9.risk.shared.Territory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.lang.Math;
import java.util.concurrent.TimeUnit;
import javafx.scene.media.MediaPlayer.Status;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.ReadOnlyBufferException;
import java.util.*;


public class MapController {

  private Stage Window;

  @FXML
  public Label foodQuantity;
  @FXML
  public Label moneyQuantity;
  @FXML
  public Label techLevelLabel;
  @FXML
  public Label colorLabel;
  @FXML
  public Label researchLabel;

  @FXML
  public Button A;

  @FXML
  public Button B;
  @FXML
  public Button C;
  @FXML
  public Button D;
  @FXML
  public Button E;
  @FXML
  public Button F;
  @FXML
  public Button G;
  @FXML
  public Button H;
  @FXML
  public Button I;
  @FXML
  public Button J;
  @FXML
  public Slider volumn;

  private HashMap<String, Button> ButtonMap;
  private Map myMap;
  private Player player;
  private String color;
  private Button researchButton;
  private MediaPlayer mediaPlayer;


  @FXML
  Label territoryStats;
  @FXML
  Label status;
  @FXML
  Label spyLabel;
  @FXML
  Button done;
  @FXML
  Button createAttack;
  @FXML
  Button createMove;
  @FXML
  Button createUpgrade;
  @FXML
  Button levelUp;
  @FXML
  Button research;
  @FXML
  Button createClock;
  @FXML
  Button createProtect;

  public static HashSet<String> attacks = new HashSet<>();
  public static HashSet<String> moves = new HashSet<>();
  public static HashSet<String> upgrades = new HashSet<>();
  public static HashSet<String> buys = new HashSet<>();
  public static boolean techAction = false;
  public HashMap<String, String> seen = new HashMap<>();
  public static boolean researchAction = false;
  public static HashSet<String> cloaks = new HashSet<>();
  public static HashSet<String> protects = new HashSet<>();

  public ObjectOutputStream objectOutputStream;
  public ObjectInputStream objectInputStream;
  public String unit_order;


  /**
   * this method to get all buttons for the default map (containing all territories
   */
  public void InitButtonMap() {
    ButtonMap = new HashMap<>();
    ButtonMap.put("A", A);
    ButtonMap.put("B", B);
    ButtonMap.put("C", C);
    ButtonMap.put("D", D);
    ButtonMap.put("E", E);
    ButtonMap.put("F", F);
    ButtonMap.put("G", G);
    ButtonMap.put("H", H);
    ButtonMap.put("I", I);
    ButtonMap.put("J", J);
    researchButton = research;

  }


  public MapController(Stage Window, Map map, Player player, ObjectInputStream objectInputStream,
      ObjectOutputStream objectOutputStream, MediaPlayer mediaPlayer, String unit_order) {
    this.myMap = map;
    this.Window = Window;
    this.player = player;
    this.color = player.getName();
    this.objectInputStream = objectInputStream;
    this.objectOutputStream = objectOutputStream;
    this.mediaPlayer = mediaPlayer;
    this.unit_order = unit_order;
  }


  // initial game room according to the given map
  public void initialize() throws Exception {
    InitButtonMap();
    // display different map to different players
    // TODO: how to indicate the player's name
    
    mediaPlayer.stop();

    String musicFile = "src/main/resources/Music/MainMusic.mp3";
    Media sound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);

    showMap();
    volumn.setValue(0);
    mediaPlayer.setVolume(0.0);

    volumn.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.pause(); 
            mediaPlayer.setVolume(volumn.getValue()/100.0);
            System.out.println(volumn.getValue());
            System.out.println(mediaPlayer.getVolume());
            mediaPlayer.play();
        });

  }

  public void showMap() throws Exception {
    //set each button's color and shape in buttonMap
    updateTerritoryText();

    //debug -> for same map between client and server
    updateTerritoryText();
    updateTerritoryText();


    updateButtonColors();
    // set food and money value
    updateResources();
    colorLabel.setText("You are the " + color + " player");
    System.out.println("Status: end of showMap");
    spyLabel.setVisible(false);
    // updateMapafterInitiualization();
  }

//    public void updateButtonColors() {
//        HashSet<Player> players = myMap.getPlayer();
//        // store useable buttons
//        Set<String> allButtons = ButtonMap.keySet();
//        for (Player p: players) {
//            String color = p.getName();
//            for (Territory ter: p.getTerritoryList()) {
//                String style = getStyle(color);
//                Button button =  ButtonMap.get(ter.getName());
//                allButtons.remove(ter.getName());
//                button.setStyle(style);
//                button.setCursor(Cursor.HAND);
//            }
//        }
//        for (String unusedButton: allButtons) {
//            Button button = ButtonMap.get(unusedButton);
//            button.setDisable(true);
//        }
//    }

  // ----------evo 3: only display adjacency---------------
  public void updateButtonColors() {
    // store useable buttons
    Set<String> allButtons = ButtonMap.keySet();
    HashSet<Player> players = myMap.getPlayer();
    // store neighbored territories
    HashSet<Territory> neighbors = new HashSet<>();
    Player player = myMap.findPlayer(color);
    for (Territory ter : player.getTerritoryList()) {
      for (Territory nei : ter.getNeighbors()) {
        if (!player.getTerritoryList().contains(nei)) {
          neighbors.add(nei);
        }
      }
    }

    for (Player p : players) {
      for (Territory ter : p.getTerritoryList()) {
        String terColor = ter.getOwner().getName();
        boolean hasSpy = (ter.hasSpy(player) > 0);// && (p.equals(player));

        // if it's owned by player or adjacency territories, show color
        if (p.equals(player) || (!p.equals(player) && myMap.isNeighbor(ter, color)
            && ter.getCloakNum() == 0) || hasSpy) {
          String style = getStyle(terColor);
          Button button = ButtonMap.get(ter.getName());
          button.setStyle(style);
          button.setCursor(Cursor.HAND);
          // save old info to hashset seen
          seen.put(ter.getName(), myMap.getTerritoryInfo(ter.getName()));
        } else if (hasSeen(ter.getName()) && ter.getCloakNum() == 0) {
          // if has seen before, set grey background color
          Button button = ButtonMap.get(ter.getName());
          button.setStyle(getStyle("grey"));
        } else {
          // if haven't been seen before, set transparent background
          Button button = ButtonMap.get(ter.getName());
          button.setStyle("-fx-background-color: transparent");
        }
        allButtons.remove(ter.getName());
      }
    }
    for (String unusedButton : allButtons) {
      Button button = ButtonMap.get(unusedButton);
      button.setDisable(true);
    }
    if (player.getResearched()) {
      Button button = researchButton;
      button.setDisable(true);
    }
  }

//    /**
//     * check if territory t is the neighbors of player
//     * @param t is the given territory
//     * @return true if it's neighbor
//     */
//    private boolean isNeighbor(Territory t) {
//        for (Territory ter: myMap.findPlayer(color).getTerritoryList()) {
//            for (Territory nei: ter.getNeighbors()) {
//                if (Objects.equals(t.getName(), nei.getName())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


  /**
   * check if the territory info has seen before
   */
  public boolean hasSeen(String terName) {
    for (String ter : seen.keySet()) {
      if (Objects.equals(ter, terName)) {
        return true;
      }
    }
    return false;
  }

  public void updateResources() {
    player = myMap.findPlayer(color);
    foodQuantity.setText(Integer.toString(player.getFoodQuantity()));
    moneyQuantity.setText(Integer.toString(player.getMoneyQuantity()));
    techLevelLabel.setText(Integer.toString(player.getTechLevel()));
    System.out.println(player.getResearched());
    researchLabel.setText(player.getResearched() ? "YES" : "NO");
  }

  public void updateTerritoryText() {
    player = myMap.findPlayer(color);
    StringBuilder sb = new StringBuilder();
    String ter = "you have "  ;
    sb.append(ter);
    sb.append("\n");

//    System.out.println(player.getName());
//    for (Territory t : player.getTerritoryList()) {
//          sb.append(t.getName() + " ");
//        }
    sb.append(unit_order);

    status.setText(sb.toString());
    System.out.println("Status: set text:" + sb.toString());
  }


  public void updateMap() {
    InitButtonMap();
    updateButtonColors();
    updateResources();
  }

  public String getStyle(String color) {
    //set each button's shape and color
    String style = "-fx-background-color: " + color + ";";
    style += ";" + "-fx-border-color: black;";
    return style;
  }

  @FXML
  public void onCreateAttack(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      AttackPopup popup = new AttackPopup();
      popup.display();
      String[] words = validAction(popup.action);
      if (words != null) {
        attacks.add(popup.action);
        statusLabel("Attack " + words[1] + " with " + words[2] + "(Level " + words[3] +
            ") units from " + words[0]);
      } else {
        statusLabel("Invalid Action");
      }

    } catch (IOException e) {
      System.out.println("Could not display Attack Popup");
    }
  }

  @FXML
  public void onCreateMove(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      MovePopup popup = new MovePopup();
      popup.display();
      String[] words = validAction(popup.action);
      if (words != null) {
        moves.add(popup.action);
        statusLabel("Move " + words[2] + "(Level " + words[3] +
            ") units from " + words[0] + " to " + words[1]);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Move Popup");
    }
  }

  @FXML
  public void onCreateUpgrade(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      UpgradePopup popup = new UpgradePopup();
      popup.display();
      String[] words = validAction(popup.upgrade);
      if (words != null) {
        upgrades.add(popup.upgrade);
        statusLabel("Upgrade " + words[1] + " units in "
            + words[0] + " from level" + words[2] + " to " + words[3]);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Upgrade Popup");
    }
  }

  @FXML
  public void onCreateCloak(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      CloakPopup popup = new CloakPopup();
      popup.display();
      if (popup.cloak != null && popup.cloak.split(" ").length == 1) {
        cloaks.add(popup.cloak);
        statusLabel("Cloak territory " + popup.cloak);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Cloak Popup");
    }
  }

  @FXML
  public void onCreateProtect(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      ProtectPopup popup = new ProtectPopup();
      popup.display();
      if (popup.protect != null && popup.protect.split(" ").length == 1) {
        protects.add(popup.protect);
        statusLabel("Protect territory " + popup.protect);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Protect Popup");
    }
  }

  // move to map class, easier for testing
//    private String getTerritoryInfo(String terrName) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Territory " + terrName + "\n");
//        Territory t = myMap.findTerritory(terrName);
//        sb.append("Owner: " + t.getOwner().getName() + "\n");
//        //int[] unitCounts = new int[] {13, 0, 0, 0, 0, 0, 0};
//        for (int i = 0; i < 7; i++) {
//            sb.append("Level " + i + ": " + t.getUnits(i) + "\n");
//        }
//        sb.append("Food Prod: 50\n");
//        sb.append("Money Prod: 20\n");
//        sb.append("Size: " + t.getSize() + "\n");
//        sb.append("Clock Number: " + t.getCloackNum());
//        return sb.toString();
//    }

  @FXML
  public void onTerritory(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      Territory ter = myMap.findTerritory(btn.getText());
      // if visible this round and the neighbored territory is not cloaked, update text
      if (player.getTerritoryList().contains(ter) || (isVisibleTerr(btn.getText())
          && ter.getCloakNum() == 0) || ter.hasSpy(myMap.findPlayer(color)) > 0) {
        territoryStats.setText(myMap.getTerritoryInfo(btn.getText()));
      } else if ((isVisibleTerr(btn.getText()) && ter.getCloakNum() > 0) || ter.getCloakNum() > 0) {
        // if the neighbored territory was cloaked, display null;
        // if it's ever been seen before, still be cloaked
        territoryStats.setText(null);
      } else { // if the territory is invisible, set old text from seen, or null if it hasn't been seen before
        String info = seen.containsKey(btn.getText()) ? seen.get(btn.getText()) : null;
        territoryStats.setText(info);
      }

      int spyCount = myMap.findTerritory(btn.getText()).hasSpy(player);
      if (spyCount > 0) {
        spyLabel.setText("Spies: " + spyCount);
        spyLabel.setVisible(true);
      } else {
        spyLabel.setVisible(false);
      }
    } else {
      throw new IllegalArgumentException("Invalid source");
    }
  }

  /**
   * check if the territory is visible in this round, if yes, update ter info; else, do not update.
   */
  public boolean isVisibleTerr(String terName) {
    HashSet<Territory> neighbors = new HashSet<>();
    Player player = myMap.findPlayer(color);
    Territory territory = myMap.findTerritory(terName);
    for (Territory ter : player.getTerritoryList()) {
      for (Territory nei : ter.getNeighbors()) {
        neighbors.add(nei);
      }
    }
    boolean hasSpy = territory.hasSpy(player) > 0;
    return  neighbors.contains(territory);
  }


  @FXML
  public void onLevelUp(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      techAction = true;
      statusLabel("Upgrade Tech Level");
    } else {
      throw new IllegalArgumentException("Invalid source");
    }
  }

  @FXML
  public void onResearch(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      researchAction = true;
      statusLabel("Do research order");
    } else {
      throw new IllegalArgumentException("Invalid source");
    }
  }

  @FXML
  public void onDone(ActionEvent actionEvent) throws Exception {
    //disableButtons();
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      for (String move : moves) {
        System.out.println("Move : " + move);
      }
      for (String attack : attacks) {
        System.out.println("Attack : " + attack);
      }
      for (String upgrade : upgrades) {
        System.out.println("Upgrade : " + upgrade);
      }
      for (String buy : buys) {
        System.out.println("Buys : " + buy);
      }
      if (techAction) {
        System.out.println("Player tech level upgrade");
      }
      if (researchAction) {
        System.out.println("Player do research");
      }
      for (String cloak : cloaks) {
        System.out.println("cloak : " + cloak);
      }
      ActionSet actionSet = new ActionSet();
      actionSet.actionListAttack = attacks;
      actionSet.actionListMove = moves;
      actionSet.actionListUpgrade = upgrades;
      actionSet.actionListBuy = buys;
      actionSet.techLevelUpgrade = techAction;
      actionSet.doResearch = researchAction;
      actionSet.actionListCloak = cloaks;
      actionSet.actionListProtect = protects;
      objectOutputStream.reset();
      objectOutputStream.writeObject(actionSet); //write 001
      System.out.println("Status: write actionSet");
      String actionProblem = (String) objectInputStream.readObject();//read 001
      System.out.println("Status: read actionProblem: " + actionProblem);

      if (actionProblem == null) {
        statusLabel("Actions submitted to server. Waiting for updated map.");
        btn.setStyle("-fx-background-color: Green");
        myMap = (Map) objectInputStream.readObject();//read 002 //RoomThread 106
        System.out.println("Status: Received updated Map.");
        statusLabel("Received updated Map.");
        btn.setStyle("-fx-background-color: White");
        String endGame = (String) objectInputStream.readObject();//read 003
        System.out.println("Status: read endGame: " + endGame);//keep going

        /************For quit and continue part****************/

        if (!checkWinner(endGame)) {
          //What do we do here?
          //debug 4.7 --> last to find the continue and quit
          if (myMap.findPlayer(color).isLose() && myMap.findPlayer(color).getLoseStatus()
              .equals("no act")) {
            //    popup替换成
            String quitOrContinue = losePopup();
            if (quitOrContinue.equals("quit")) {
              objectOutputStream.writeObject(quitOrContinue); // write 001
              //change it at local player.
              myMap.findPlayer(color).setLoseStatus(quitOrContinue);
            } else {
              objectOutputStream.writeObject("no act");
            }

          } else if (myMap.findPlayer(color).isLose() && myMap.findPlayer(color).getLoseStatus()
              .equals("quit")) {
            System.out.println("Bye bye I quit");
            objectOutputStream.writeObject("quit"); //write 001


          } else if (myMap.findPlayer(color).isLose() && myMap.findPlayer(color).getLoseStatus()
              .equals("continue")) {

            System.out.println("player decide to continue!");
            objectOutputStream.writeObject("continue");  //write 001
          } else {
            objectOutputStream.writeObject("no act"); //write 002
          }
        }

        /*********************************************/

      } else {

        status.setText(actionProblem);
        btn.setStyle("-fx-background-color: red");
        //enableButtons();
        resetActions();
        updateMap();
        return;
      }

    } else {
      throw new IllegalArgumentException("Invalid Done Button");
    }
    // TODO: check results and triggle game end page

    resetActions();
    updateMap();
    //enableButtons();

  }


  public static void resetActions() {
    attacks.clear();
    moves.clear();
    upgrades.clear();
    buys.clear();
    techAction = false;
    researchAction = false;
    cloaks.clear();
    protects.clear();
  }

  public boolean checkWinner(String endGame) throws IOException {

    if (endGame.equals("win")) {
      System.out.println("You have won");
      status.setText("You have won!");

      // display page
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/WinView.fxml"));
      loaderStart.setControllerFactory(c -> {
        mediaPlayer.stop();
        return new WinPage(Window, objectInputStream, objectOutputStream, mediaPlayer);
      });
      Scene scene = new Scene(loaderStart.load());
      Window.setScene(scene);
      Window.show();

      return true;
    } else if (endGame.equals("game over")) {
      System.out.println("Gameover, you have lost.");
      status.setText("Gameover, you have lost.");

      // display page
      FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/LoseView.fxml"));
      loaderStart.setControllerFactory(c -> {
        mediaPlayer.stop();
        return new LosePage(Window, objectInputStream, objectOutputStream, mediaPlayer);
      });
      Scene scene = new Scene(loaderStart.load());
      Window.setScene(scene);
      Window.show();

      return true;
    }
    return false;
  }

  public void statusLabel(String message) {
    String label = "Last Action: " + message;
    status.setText(label);
  }

  public String[] validAction(String actionString) {
    if (actionString == null) {
      return null;
    }
    String[] words = actionString.split(" ");
    if (words.length != 4) {
      return null;
    }
    return words;
  }

  public String[] validBuy(String actionString) {
    if (actionString == null) {
      return null;
    }
    String[] words = actionString.split(" ");
    if (words.length != 2) {
      return null;
    }
    return words;
  }

  public String losePopup() throws Exception {
    try {
      LosePopup popup = new LosePopup();
      popup.display();
      return popup.quitOrContinue;

    } catch (IOException e) {
      System.out.println("Could not display Unit Popup");
    }
    return null;
  }

  @FXML
  public void back2room() throws IOException {
    Socket socket = new Socket("localhost", 8080);
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

    this.Window = Window;
    FXMLLoader loaderStart = new FXMLLoader(
        getClass().getResource("/FXML/StartView.fxml"));
    loaderStart.setControllerFactory(c -> {
      //debug 4.9
      return new StartGameController(Window, objectInputStream, objectOutputStream, mediaPlayer);
    });
    Scene scene = new Scene(loaderStart.load());
    Window.setScene(scene);
    Window.show();
  }

  @FXML
  public void onBuyUnit(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (source instanceof Button) {
      Button btn = (Button) source;
      System.out.println(btn.getId());
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

    try {
      BuyPopup popup = new BuyPopup();
      popup.display();
      String[] words = validBuy(popup.action);
      if (words != null) {
        buys.add(popup.action);
        statusLabel("Buy " + words[1] + " units in territory " + words[0]);
      } else {
        statusLabel("Invalid Action");
      }
    } catch (IOException e) {
      System.out.println("Could not display Buy Popup");
    }
  }
}

