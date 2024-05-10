package com.example.curavibe_desktop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Charts extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DashbordeStat.fxml"));

        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

