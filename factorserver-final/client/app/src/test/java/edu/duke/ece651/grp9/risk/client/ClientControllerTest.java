//package edu.duke.ece651.grp9.risk.client;
//
//import edu.duke.ece651.grp9.risk.shared.Map;
//import edu.duke.ece651.grp9.risk.shared.MapFactory;
//import edu.duke.ece651.grp9.risk.shared.Player;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxAssert;
//import org.testfx.api.FxRobot;
//import org.testfx.assertions.api.Assertions;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.matcher.control.TextInputControlMatchers;
//import org.testfx.util.WaitForAsyncUtils;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Objects;
//import java.util.logging.Logger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(ApplicationExtension.class)
//class ClientControllerTest extends ApplicationTest {
//    Button button;
//    StartGameController cont;
//    Client client;
//    Stage stage;
//    public ObjectOutputStream objectOutputStream;
//    public ObjectInputStream objectInputStream;
//
//    @Override
//    public void start(Stage stage) throws Exception{
//        client = new Client();
//        new Thread(() -> {
//            try {
//                ServerSocket socket = new ServerSocket(12345);
//                Socket clientSocket = socket.accept();
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//                objectOutputStream.writeObject(null);
//                objectOutputStream.reset();
//
//            } catch (Exception e) {
//
//            }
//        }).start();
//
//        Thread.sleep(1000);
//        client.start(stage);
//
////        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartView.fxml")));
////        stage.setScene(new Scene(root));
//        stage.show();
//    }
//
//
//    @Test
//    void test_getStyle() {
//        MapController cont = new MapController(stage, new Map(), new Player("red"), null, null);
//        String s = cont.getStyle("red");
//        assertEquals("-fx-background-color: red;;-fx-border-color: black;", s);
//    }
//
//    @Test
//    void test_hasSeen() {
//        MapController cont = new MapController(stage, new Map(), new Player("red"), null, null);
//        assertEquals(false, cont.hasSeen("A"));
//        cont.seen.put("A", "test");
//        assertEquals(true, cont.hasSeen("A"));
//    }
//
//    @Test
//    void test_isVisibleTerr() {
//        MapFactory mapFactory = new MapFactory();
//        Map map = mapFactory.makeMapForTwo();
//        MapController cont = new MapController(stage, map, map.findPlayer("red"), null, null);
//        assertTrue(cont.isVisibleTerr("A"));
//        assertFalse(cont.isVisibleTerr("I"));
//    }
//
//    @Test
//    void test_updateResources() {
//        MapFactory factory = new MapFactory();
//        Map map = factory.makeMapForTwo();
//        MapController cont = new MapController(stage, map, map.findPlayer("red"), null, null);
//        cont.foodQuantity = new Label();
//        cont.moneyQuantity = new Label();
//        cont.techLevelLabel = new Label();
//        cont.updateResources();
//
//        assertEquals(cont.foodQuantity.getText(), "200");
//        assertEquals(cont.moneyQuantity.getText(), "100");
//        assertEquals(cont.techLevelLabel.getText(), "1");
//    }
//
//    @Test
//    void test_resetActions(){
//        MapFactory factory = new MapFactory();
//        Map map = factory.makeMapForTwo();
//        MapController cont = new MapController(stage, map, map.findPlayer("red"), null, null);
//        cont.moves.add("1 2 3 3");
//
//        assertEquals(cont.moves.iterator().next(), "1 2 3 3");
//
//        MapController.resetActions();
//        assertTrue(cont.moves.isEmpty());
//    }
//
//    @Test
//    void test_validAction() {
//        MapFactory factory = new MapFactory();
//        Map map = factory.makeMapForTwo();
//        MapController cont = new MapController(stage, map, map.findPlayer("red"), null, null);
//
//        assertNull(cont.validAction(null));
//
//        assertNull(cont.validAction("3 3 3"));
//
//        assertTrue(cont.validAction("3 1 2 4")[0].equals("3"));
//        assertTrue(cont.validAction("3 1 2 4")[1].equals("1"));
//        assertTrue(cont.validAction("3 1 2 4")[2].equals("2"));
//        assertTrue(cont.validAction("3 1 2 4")[3].equals("4"));
//    }
//
//    @Test
//    void test_showMap() throws Exception {
//        MapFactory factory = new MapFactory();
//        Map map = factory.makeMapForThree();
//        MapController cont = new MapController(stage, map, map.findPlayer("red"), null, null);
//
//        cont.foodQuantity = new Label();
//        cont.moneyQuantity = new Label();
//        cont.techLevelLabel = new Label();
//        cont.colorLabel = new Label();
//        cont.status = new Label();
//        cont.A = new Button();
//        cont.B = new Button();
//        cont.C = new Button();
//        cont.D = new Button();
//        cont.E = new Button();
//        cont.F = new Button();
//        cont.G = new Button();
//        cont.H = new Button();
//        cont.I = new Button();
//        cont.J = new Button();
//
//        cont.initialize();
//        cont.updateMap();
//
//        assertEquals(cont.colorLabel.getText(), "You are the red player");
//    }
//
//    @Test
//    void test_statusLabel() {
//        MapFactory factory = new MapFactory();
//        Map map = factory.makeMapForTwo();
//        MapController cont = new MapController(stage, map, map.findPlayer("red"), null, null);
//        cont.status = new Label();
//
//        assertEquals(cont.status.getText(), "");
//
//        cont.statusLabel("Test");
//
//        assertEquals(cont.status.getText(), "Last Action: Test");
//    }
//
//
//    @Test
//    void test_login(FxRobot robot) throws IOException {
//        MapFactory mapFactory = new MapFactory();
//        Map map = mapFactory.makeMap(2);
//        cont = new StartGameController(stage,objectInputStream, objectOutputStream);
//        //stage.show();
//       try {
//           robot.clickOn("#login");
//           cont.login();
////
////           robot.doubleClickOn("#username").write("ryu");
////           robot.doubleClickOn("#password").write("ryu");
////           FxAssert.verifyThat("#username", TextInputControlMatchers.hasText("ryu"));
////           FxAssert.verifyThat("#password", TextInputControlMatchers.hasText("ryu"));
////
////           robot.clickOn("#join");
//       } catch (Exception e){
//
//       }
//
//        //WaitForAsyncUtils.waitForFxEvents();
//
//    }
//
//}
