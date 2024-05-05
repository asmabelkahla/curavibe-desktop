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

public class LoginController implements Initializable {


    @FXML
    private ImageView brandingImageView;
    @FXML
    private Label loginMessageLabel;

    @FXML

    private TextField Email;
    @FXML
    private PasswordField password;
    PreparedStatement st = null;
    Connection con = Connexion.getInstance().getCnx();
    ResultSet rs = null;

    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;



    @FXML
    private Button cancelbutton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);


    }



    public void loginButtonOnAction(ActionEvent event) throws SQLException {
        String email = Email.getText().trim(); // Supprimer les espaces avant et après
        String pass = password.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            loginMessageLabel.setText("Veuillez saisir à la fois l'e-mail et le mot de passe");
        } else if (!isValidEmail(email)) {
            loginMessageLabel.setText("Format d'e-mail invalide");
        } else {
            // Procéder à la requête de la base de données
            st = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("Valide"); // Ajoutez votre code ici pour la vérification réussie du mot de passe
            } else {
                loginMessageLabel.setText("E-mail ou mot de passe incorrect");
            }
        }
    }

    private boolean isValidEmail(String email) {
        // Validation simple de l'e-mail en utilisant une expression régulière
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    public void goToSignUp(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void cancelbuttonOnAction (ActionEvent event){
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();

    }

}