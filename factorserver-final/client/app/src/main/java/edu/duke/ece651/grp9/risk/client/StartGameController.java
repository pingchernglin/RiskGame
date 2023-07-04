package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGameController implements Initializable {
    private Stage Window;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    public MediaPlayer mediaPlayer;

    public StartGameController(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, MediaPlayer mediaPlayer) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.mediaPlayer = mediaPlayer;
    }

    @FXML
    public void login() throws IOException {
        System.out.println("click on log in");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/LoginView.fxml"));
        loaderStart.setControllerFactory(c->{
            return new logInPageController(Window,objectInputStream,objectOutputStream, mediaPlayer);
        });
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String musicFile = "src/main/resources/Music/StartMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
