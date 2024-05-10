package com.example.curavibe_desktop;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;


import com.example.curavibe_desktop.Connexion;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.File;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class RegisterController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField Phone;

    @FXML
    private TextField Email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordconfir;

    @FXML
    private ImageView brandingImageView2;

    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private ImageView CP;
    @FXML
    private Label loginMessageLabel ;
    @FXML
    private Label registerView;

    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    @FXML
    private Button cancelbutton;
    @FXML
    private static final String IMAGE_PATH = "img/";
    private Connection con;
    private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = Connexion.getInstance().getCnx();
        loadImage("logo.png", brandingImageView2);
    }
    private void loadImage(String fileName, ImageView imageView) {
        File imageFile = new File(IMAGE_PATH + fileName);
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
    }
    // Méthode pour valider la force du mot de passe
    private boolean isStrongPassword(String password) {
        // Au moins 8 caractères
        if (password.length() < 8) {
            return false;
        }else{
            return true;}
    }
    // Méthode pour valider le numéro de téléphone
    private boolean isValidPhoneNumber(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }
    public void goToSignUp2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void registerButtonOnAction(ActionEvent event) {
        String name = nom.getText();
        String phone = Phone.getText();
        String email = Email.getText();
        String passwordString = password.getText();

        String confirmPassword = passwordconfir.getText();

        // Validation des champs vides
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || passwordString.isEmpty() || confirmPassword.isEmpty()) {
            loginMessageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        if (!isStrongPassword(passwordString)) {
            loginMessageLabel.setText("Mot de passe faible. Utilisez au moins 8 caractères, une majuscule, une minuscule et un chiffre.");
            return;
        }
        // Validation du format de l'email
        if (!isValidEmail(email)) {
            loginMessageLabel.setText("Email invalide.");
            return;
        }
        // Validation de la force du mot de passe
        if (!isValidPassword(passwordString)) {
            loginMessageLabel.setText("Mot de passe doit contenir au moins 8 caractères.");
            return;
        }

        // Vérification si les mots de passe correspondent
        if (!passwordString.equals(confirmPassword)) {
            loginMessageLabel.setText("Les mots de passe ne correspondent pas.");
            return;
        }
        try {
            if (emailExists(email)) {
                loginMessageLabel.setText("L'email est déjà utilisé.");
                return;
            }

            String query = "INSERT INTO users (name, phone, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(query)) {

                statement.setString(1, name);
                statement.setString(2, phone);
                statement.setString(3, email);
                statement.setString(4, passwordString); // This line is setting the password


                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    registerView.setText("Inscription réussie.");
                    registerView.setStyle("-fx-text-fill: green;");
                    goToLogin(event);
                    // Redirection vers une autre vue ou effectuer une autre action appropriée
                }
            }
        } catch (SQLException e) {
            loginMessageLabel.setText("Erreur d'inscription.");
            e.printStackTrace();
        }
    }
    public void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Affichez un message d'erreur ou effectuez une autre action appropriée pour informer l'utilisateur du problème.
        }
    }


    private boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}