package edu.duke.ece651.grp9.risk.client;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InitiUnitPlace {

    private static Stage popupwindow;
    public static String action;

    @FXML
    TextField sourceTerritory;
    @FXML
    TextField destinationTerritory;
    @FXML
    TextField numUnits;
    @FXML
    Slider unitLevel;

    @FXML
    public static void display() throws IOException {
        popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Please place your territory units!");

        URL xmlRes = MapController.class.getResource("/FXML/InitialPlace.fxml");
        assert (xmlRes != null);
        GridPane gp = FXMLLoader.load(xmlRes);
        gp.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(gp, 350, 300);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            popupwindow.close();
            this.action = null;
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            popupwindow.close();
            String action = sourceTerritory.getText() + " " + destinationTerritory.getText() + " " +
                    numUnits.getText() + " " + (int)unitLevel.getValue();
            this.action = action;
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }
}

