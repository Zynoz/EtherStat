package businesslogic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/start.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("EtherStat");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.setResizable(true);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}