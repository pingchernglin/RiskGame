package edu.duke.ece651.grp9.risk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.io.*;

public class WinPage {
    private Stage Window;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    public MediaPlayer mediaPlayer;
    public MediaPlayer mediaPlayer_win;

    public WinPage(Stage Window, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, MediaPlayer mediaPlayer) {
        this.Window = Window;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        mediaPlayer.stop();
        String musicFile = "src/main/resources/Music/win.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer_win = new MediaPlayer(sound);
        mediaPlayer_win.play();

    }

    @FXML
    public void back2room() throws IOException {
        Socket socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            this.Window = Window;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/FXML/StartView.fxml"));
        loaderStart.setControllerFactory(c->{
            //debug 4.9
            return new StartGameController(Window,objectInputStream,objectOutputStream,mediaPlayer);
        });
        mediaPlayer_win.stop();
        Scene scene = new Scene(loaderStart.load());
        Window.setScene(scene);
        Window.show();
    }

}
