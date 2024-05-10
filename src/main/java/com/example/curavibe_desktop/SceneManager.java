package com.example.curavibe_desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {

    private Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
