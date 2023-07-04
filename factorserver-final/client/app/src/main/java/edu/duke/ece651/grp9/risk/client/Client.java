package edu.duke.ece651.grp9.risk.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.media.*;
import javafx.application.Application;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    private Stage Window;
    private Socket socket;


    @Override
    public void start(Stage stage) throws IOException {
        try{
            String musicFile = "src/main/resources/Music/StartMusic.mp3";
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            

            this.socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            this.Window = stage;

            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/StartView.fxml"));
            loaderStart.setControllerFactory(c -> {
                return new StartGameController(Window, objectInputStream, objectOutputStream, mediaPlayer);
            });
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();


        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


