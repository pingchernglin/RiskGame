package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

public class logInPageController{
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    private Stage Window;
    public String name;
    public String pwd;
    private String errMsg;
    private LinkedBlockingQueue<Pair<String, String>> loginList;
    public static ObjectOutputStream outputStream;
    public static ObjectInputStream inputStream;
    public MediaPlayer mediaPlayer;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Text errorMessage;

    public logInPageController(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,MediaPlayer mediaPlayer) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.mediaPlayer = mediaPlayer;
        System.out.println("input name and password.\n click join");
    }


    @FXML
    public void join() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/SelectRoomView.fxml"));




        
        this.name = username.getText();
        this.pwd = password.getText();
//        username.setText("");
//        password.setText("");

        if( !(name.equals("") || pwd.equals(""))) {
            objectOutputStream.reset();
            objectOutputStream.writeObject(name);
            objectOutputStream.reset();
            objectOutputStream.writeObject(pwd);

            String account_check = null;
            //account check
            try {
                account_check = (String) objectInputStream.readObject();


            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

            if (account_check.equals("true")) {
                loaderStart.setControllerFactory(c -> {
                    return new selectRoomController(Window, this.objectInputStream,this.objectOutputStream,mediaPlayer);
                });

                Scene scene = new Scene(loaderStart.load());
                Window.setScene(scene);
                Window.show();
            }
            
            // TODO: need to reader info from controller to client
            else{
                errorMessage.setText("your password is wrong!");
            }


        }
        else{
            username.setText("");
            password.setText("");
        }



    }
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        String musicFile = "src/main/resources/Music/MainMusic.mp3";
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
//    }
}
