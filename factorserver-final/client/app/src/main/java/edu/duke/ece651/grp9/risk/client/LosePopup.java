package edu.duke.ece651.grp9.risk.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LosePopup {

    private static Stage popupwindow;
    public static String quitOrContinue;

    @FXML
    public static void display() throws IOException {
        popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Game Over");

        URL xmlRes = MapController.class.getResource("/FXML/LosePopup.fxml");
        assert (xmlRes != null);
        GridPane gp = FXMLLoader.load(xmlRes);
        gp.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(gp, 340, 100);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    @FXML
    public void onContinue(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            popupwindow.close();
            this.quitOrContinue = "continue";
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }

    @FXML
    public void onQuit(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            popupwindow.close();
            this.quitOrContinue = "quit";
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }
}

