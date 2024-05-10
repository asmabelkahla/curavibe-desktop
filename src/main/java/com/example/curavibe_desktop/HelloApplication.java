package com.example.curavibe_desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);

        // Initialisation du contrôleur de la vue de connexion (LoginController)
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));

        Parent loginRoot = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setSceneManager(sceneManager); // Initialisation de sceneManager

        stage.setScene(new Scene(loginRoot, 600, 400));
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) {
        Connexion connexion = Connexion.getInstance();
        if (connexion.getCnx() != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("La connexion a échoué.");
        }
        launch();
    }

    }

