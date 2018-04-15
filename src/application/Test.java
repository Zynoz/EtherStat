package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/start.fxml"));
//        Scene scene = null;
//
//        try {
//            scene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        primaryStage.setTitle("EtherStat");
//        primaryStage.setWidth(600);
//        primaryStage.setHeight(400);
//        primaryStage.setResizable(true);
//
//        primaryStage.setScene(scene);
//        primaryStage.getIcons().add(new Image("/resources/images/test.png"));
//        primaryStage.show();

        try {
            RootAnchorPane rootAnchorPane = new RootAnchorPane();
            Scene scene = new Scene(rootAnchorPane, 600, 400);
            primaryStage.setTitle("EtherStat");
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(alertType.name());
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}