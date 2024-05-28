package com.example.curavibe_desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TextField addressTextField;
    @FXML
    private ImageView profil;

    @FXML
    private TextField phoneTextField;
    @FXML
    private Label statusLabel;

    private Connection connection;

    public ProfileController() {
        connection = Connexion.getInstance().getCnx();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File profilFile = new File("img/téléchargement.png");
        Image profilImage = new Image(profilFile.toURI().toString());
        profil.setImage(profilImage);
    }

    private String emailUtilisateur = "wafabenfatma@gmail.com"; // Replace this with the authenticated user's email

    @FXML
    public void initialize() {
        // Retrieve user data
        try {
            String sql = "SELECT u.name, u.email, p.first_name, p.last_name, p.dob, p.address, p.phone FROM users u JOIN profiles p ON u.id = p.user_id WHERE u.email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailUtilisateur);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                // Update the UI with the retrieved data
                nameLabel.setText(name);
                emailTextField.setText(email);
                firstNameTextField.setText(firstName);
                lastNameTextField.setText(lastName);
                dobDatePicker.setValue(dob);
                addressTextField.setText(address);
                phoneTextField.setText(phone);
            } else {
                System.out.println("User not found.");
            }

            // Close resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserDetails() {
        String newEmail = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        LocalDate dob = dobDatePicker.getValue();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();

        try {
            // Update email in the users table
            String updateUserSql = "UPDATE users SET email = ? WHERE email = ?";
            PreparedStatement updateUserStatement = connection.prepareStatement(updateUserSql);
            updateUserStatement.setString(1, newEmail);
            updateUserStatement.setString(2, emailUtilisateur);
            updateUserStatement.executeUpdate();
            updateUserStatement.close();

            // Update other details in the profiles table
            String updateProfileSql = "UPDATE profiles SET first_name = ?, last_name = ?, dob = ?, address = ?, phone = ? WHERE user_id = (SELECT id FROM users WHERE email = ?)";
            PreparedStatement updateProfileStatement = connection.prepareStatement(updateProfileSql);
            updateProfileStatement.setString(1, firstName);
            updateProfileStatement.setString(2, lastName);
            updateProfileStatement.setDate(3, java.sql.Date.valueOf(dob)); // Convert LocalDate to java.sql.Date
            updateProfileStatement.setString(4, address);
            updateProfileStatement.setString(5, phone);
            updateProfileStatement.setString(6, emailUtilisateur); // Use the original email here
            updateProfileStatement.executeUpdate();
            updateProfileStatement.close();

            statusLabel.setText("User details updated successfully.");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to update user details.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }


    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
