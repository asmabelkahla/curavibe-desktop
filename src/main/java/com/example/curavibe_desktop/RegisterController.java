package com.example.curavibe_desktop;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import com.example.curavibe_desktop.Connexion;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class RegisterController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField Email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordconfir;

    @FXML
    private ImageView brandingImageView2;

    @FXML
    private Button cancelbutton;

    PreparedStatement st = null;
    Connection con = Connexion.getInstance().getCnx();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView2.setImage(brandingImage);
    }

    public void goToSignUp2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelbuttonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    public void registerButtonOnAction(ActionEvent event) {
        String firstName = prenom.getText();
        String lastName = nom.getText();
        String email = Email.getText();
        String userPassword = password.getText();
        String confirmPassword = passwordconfir.getText();

        // Vérification des champs vides
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || userPassword.isEmpty() || confirmPassword.isEmpty()) {

            return;
        }

        // Vérification du format de l'email
        if (!isValidEmail(email)) {
            // Afficher un message d'erreur à l'utilisateur ou effectuer une autre action appropriée
            return;
        }

        // Vérification de la force du mot de passe
        if (!isValidPassword(userPassword)) {
            // Afficher un message d'erreur à l'utilisateur ou effectuer une autre action appropriée
            return;
        }

        // Vérification si les mots de passe correspondent
        if (!userPassword.equals(confirmPassword)) {
            // Les mots de passe ne correspondent pas, afficher un message d'erreur ou effectuer une autre action appropriée
            return;
        }

        try {
            // Créer une requête SQL préparée pour insérer les données dans la base de données
            String query = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, userPassword);

            // Exécuter la requête
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Utilisateur inscrit avec succès !");
                // Redirection vers une autre vue ou effectuer une autre action appropriée
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs d'inscription, afficher un message d'erreur à l'utilisateur, etc.
        }
    }

    // Méthode pour vérifier le format de l'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Méthode pour vérifier la force du mot de passe
    private boolean isValidPassword(String password) {
        // Ajoutez ici vos conditions pour la validation du mot de passe
        // Par exemple, vous pouvez vérifier la longueur minimale, la présence de caractères spéciaux, etc.
        return password.length() >= 8; // Exemple : un mot de passe doit avoir au moins 8 caractères
    }
}
