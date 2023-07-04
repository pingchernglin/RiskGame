package edu.duke.ece651.grp9.risk.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;

public class UpgradePopup implements Initializable {

    private static Stage popupwindow;
    public static String upgrade;

    @FXML
    ComboBox territory;
    @FXML
    TextField numUnits;
    @FXML
    Slider startLevel;
    @FXML
    Slider endLevel;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        territory.getItems().addAll("A","B","C","D","E","F","G","H","I","J");
    }

    @FXML
    public static void display() throws IOException {
        popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Upgrade Units");

        URL xmlRes = MapController.class.getResource("/FXML/UpgradePopup.fxml");
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
            this.upgrade = null;
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
            String upgradeAction = territory.getValue().toString() + " " + numUnits.getText() + " " +
                    (int)startLevel.getValue() + " " + (int)endLevel.getValue();
            this.upgrade = upgradeAction;
        } else {
            throw new IllegalArgumentException("Invalid source");
        }
    }
}
