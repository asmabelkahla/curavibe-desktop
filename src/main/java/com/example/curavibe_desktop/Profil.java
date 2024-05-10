package com.example.curavibe_desktop;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Profil extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profil.fxml"));
        Parent root = loader.load();

        // Créer le contrôleur
        ProfileController controller = loader.getController();

        // Configurer la scène
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Votre Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

