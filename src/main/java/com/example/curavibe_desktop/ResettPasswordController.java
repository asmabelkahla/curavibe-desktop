package com.example.curavibe_desktop;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.mail.MessagingException;

public class ResettPasswordController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private TextField codeField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField confirmPasswordField;
    @FXML
    private ImageView brandingImageView;


    private String verificationCode;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);}

    @FXML
    void sendCode(ActionEvent event) {
        String email = emailField.getText();
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Adresse e-mail invalide.");
            return;
        }

        // Générer un code de vérification (à des fins de démonstration)
        verificationCode = generateVerificationCode();
        System.out.println("Code de vérification : " + verificationCode);

        // Envoyer le code de vérification par e-mail
        try {
            sendVerificationEmail(email, verificationCode);
            showAlert(Alert.AlertType.INFORMATION, "Code Envoyé", "Le code de vérification a été envoyé à votre e-mail.");
        } catch (MessagingException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'envoi du code de vérification : " + e.getMessage());
        }
    }

    @FXML
    void resetPassword(ActionEvent event) {
        String enteredCode = codeField.getText();
        if (!enteredCode.equals(verificationCode)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Code de vérification invalide.");
            return;
        }

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Mettre à jour le mot de passe dans la base de données
        try {
            UserService userService = new UserService();
            userService.updatePassword(emailField.getText(), newPassword);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le mot de passe a été réinitialisé avec succès.");

            // Retour à la page de connexion
            loadLoginScene(event);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la réinitialisation du mot de passe : " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        // Validation simple de l'e-mail
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private String generateVerificationCode() {
        // Générer un code aléatoire à 6 chiffres (à des fins de démonstration)
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void sendVerificationEmail(String email, String code) throws MessagingException {
        // Implémentez ici votre logique d'envoi d'e-mail
        // Vous pouvez utiliser JavaMail ou toute autre bibliothèque d'envoi d'e-mails
        // Exemple (utilisant EmailSender de votre exemple précédent) :
        EmailSender.sendEmail(email, "Code de Vérification", "Votre code de vérification est : " + code);

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void loadLoginScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            // Obtenez une référence au contrôleur de la vue de connexion
            LoginController loginController = loader.getController();

            // Passez l'e-mail de l'utilisateur réinitialisé au contrôleur de la vue de connexion
            loginController.initData(emailField.getText());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
