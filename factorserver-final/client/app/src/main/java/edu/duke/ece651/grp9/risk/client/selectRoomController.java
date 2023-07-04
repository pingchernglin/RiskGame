package edu.duke.ece651.grp9.risk.client;

import edu.duke.ece651.grp9.risk.shared.Map;
import edu.duke.ece651.grp9.risk.shared.MapFactory;
import edu.duke.ece651.grp9.risk.shared.Player;
import edu.duke.ece651.grp9.risk.shared.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.scene.media.*;
import javafx.application.Application;
import javafx.scene.layout.StackPane;

public class selectRoomController {
    private Stage Window;

    @FXML Button room1;
    @FXML Button room2;
    @FXML Button room3;
    @FXML Button room4;
    
    public String real_color;
    public  ObjectOutputStream objectOutputStream;
    public  ObjectInputStream objectInputStream;
    private HashMap<String, Button> ButtonMap;
    public MediaPlayer mediaPlayer;
    public String unit_order;



    private void InitButtonMap(){
        ButtonMap = new HashMap<>();
        ButtonMap.put("room1", room1);
        ButtonMap.put("room2", room2);
        ButtonMap.put("room3", room3);
        ButtonMap.put("room4", room4);
    }

    // initial game room according to the given map
    public void initialize() {
        //mediaPlayer.pause();
        // String musicFile = "src/main/resources/Music/MainMusic.mp3";
        // Media sound = new Media(new File(musicFile).toURI().toString());
        // MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();

        InitButtonMap();
        try {

            String startOrnot1 = (String) objectInputStream.readObject();
            String startOrnot2 = (String) objectInputStream.readObject();
            String startOrnot3 = (String) objectInputStream.readObject();
            String startOrnot4 = (String) objectInputStream.readObject();

            if (startOrnot1.equals("true")) {
                System.out.println("ROOM 1 FULL");
                ButtonMap.get("room1").setDisable(true);
            }
            if (startOrnot2.equals("true")) {
                ButtonMap.get("room2").setDisable(true);
            }
            if (startOrnot3.equals("true")) {
                ButtonMap.get("room3").setDisable(true);
            }
            if (startOrnot4.equals("true")) {
                ButtonMap.get("room4").setDisable(true);
            }
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }


    public selectRoomController(Stage Window, ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream,MediaPlayer mediaPlayer ) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.mediaPlayer = mediaPlayer;
        this.unit_order =new String();

    }

    private void joinRoomHelper(int playerNum, ActionEvent actionEvent) throws Exception {



        System.out.println("Status: enter the joining room helper");
        objectOutputStream.reset();
        objectOutputStream.writeObject(playerNum-1); //write room_id
        
        try{
        real_color = (String)objectInputStream.readObject(); //color
        System.out.println("Status get the color: " + real_color);

        //debug for same map between client and server
            Map map = (Map)objectInputStream.readObject();


            //接传过来的string
            //
            unit_order = (String) objectInputStream.readObject();
      
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/MapView.fxml"));
        loaderStart.setControllerFactory(c -> {
            
           //  make a map for n players
//            MapFactory mapFactory = new MapFactory();
//            Map map = mapFactory.makeMap(playerNum);

            System.out.println("Status get the map from the server ");
            Player player = map.findPlayer(real_color);
            MapController mc = new MapController(Window, map,player,objectInputStream,objectOutputStream, mediaPlayer,unit_order);
            
            return mc;
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
        catch(Exception e){
            System.out.println(e);
            
        }

        unitChecking();

        FXMLLoader loaderafterUnit = new FXMLLoader(getClass().getResource("/FXML/MapView.fxml"));
        loaderafterUnit.setControllerFactory(c -> {
            //  make a map for n players

            Map map = null;
            Player player = null;
            try {
                map = (Map) objectInputStream.readObject(); //ServerThread 106
                System.out.println("Status: get the new map");
                objectOutputStream.reset();
                String endGame = (String) objectInputStream.readObject(); //ServerThread 108
                System.out.println("Status: get the end game: " + endGame);
                player = map.findPlayer(real_color);
                //debug 4.7 afternoon
                objectOutputStream.writeObject("no act");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            MapController mc = new MapController(this.Window,map,player,objectInputStream,objectOutputStream, mediaPlayer,unit_order);

            return mc;
        });
        Scene scene_after = new Scene(loaderafterUnit.load());
        this.Window.setScene(scene_after);
        this.Window.show();

    }

    @FXML
    public void JoinRoom1(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(2, actionEvent);
    }

    @FXML
    public void JoinRoom2(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(3, actionEvent);
    }

    @FXML
    public void JoinRoom3(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(4, actionEvent);
    }

    @FXML
    public void JoinRoom4(ActionEvent actionEvent) throws Exception {
        joinRoomHelper(5, actionEvent);
    }


    public String unitPopup() throws Exception{
        try {
            UnitPopup popup = new UnitPopup();
            popup.display();
            return popup.unitPlacement;
        } catch (IOException e) {
            System.out.println("Could not display Unit Popup");
        }
        return null;
    }

    public void unitChecking() throws Exception {

            System.out.println("Status: in unit checking");
            String unitChecking = "";
            String unitPlacement = "";
            try {
            
            unitPlacement = unitPopup();
            System.out.println("Status: unitPlacement: " + unitPlacement);
            objectOutputStream.reset();
            objectOutputStream.writeObject(unitPlacement);// write unit ServerThread 51
            unitChecking = (String) objectInputStream.readObject();
            System.out.println("Status: read unitChecking: " + unitChecking);
            }
            catch (IOException e) {
                System.out.println(e);
            }

            while (unitChecking.equals("false")){
                try{
                unitPlacement = unitPopup();
                objectOutputStream.reset();
                objectOutputStream.writeObject(unitPlacement);
                unitChecking = (String) objectInputStream.readObject();
                }
                catch (IOException e) {
                System.out.println(e);
                }
            }
    }
}
