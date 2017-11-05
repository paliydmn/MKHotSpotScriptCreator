package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

      //  BorderPane pane = new BorderPane();

        Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));

      //  root.minHeight(350);
      //  root.minWidth(390);
        Scene mScene = new Scene(root);
        //mScene.getStylesheets().add(0,"styles/my.css");

        primaryStage.setMinHeight(490);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle("MKHotSpotScriptCreator");
        primaryStage.setScene(mScene);
        primaryStage.show();

        Controller.setStage(primaryStage);
    }

}
