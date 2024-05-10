package com.example.curavibe_desktop;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private ImageView brandingImageView;

    @FXML
    private ImageView mail;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView MP;

    @FXML
    private TextField Email;

    @FXML
    private PasswordField password;

    private Connection con;
    private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File MailFile = new File("img/mail.png");
        Image MailImage = new Image(MailFile.toURI().toString());
        mail.setImage(MailImage);

        File MPFile = new File("img/padlock.png");
        Image MPImage = new Image(MPFile.toURI().toString());
        MP.setImage(MPImage);
    }

    @FXML
    private void loginButtonOnAction(ActionEvent event) throws SQLException, IOException {
        String email = Email.getText().trim();
        String pass = password.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            loginMessageLabel.setText("Veuillez saisir à la fois l'e-mail et le mot de passe");
        } else if (!isValidEmail(email)) {
            loginMessageLabel.setText("Format d'e-mail invalide");
        } else {
            PreparedStatement st = null;
            ResultSet rs = null;
            con = Connexion.getInstance().getCnx();
            st = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            st.setString(1, email);
            st.setString(2, pass);
            rs = st.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("type_compte");
                if ("admin".equals(userType)) {
                    Parent root = FXMLLoader.load(getClass().getResource("DashbordeStat.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("Profil.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                loginMessageLabel.setText("E-mail ou mot de passe incorrect");
            }
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    @FXML
    private void goToSignUp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) brandingImageView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void PasswordN() throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("ResettPassword.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) brandingImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately, e.g., show an error message to the user
        }
    }
    private String userEmail;
    public void initData(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
