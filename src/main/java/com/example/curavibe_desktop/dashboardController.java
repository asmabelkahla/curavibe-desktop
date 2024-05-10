package com.example.curavibe_desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;
import javafx.scene.chart.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;


public class dashboardController implements Initializable {

    @FXML
    private Label total_userDon;

    @FXML
    private Label total_userRecycle;

    @FXML
    private Label total_User;

    @FXML
    private LineChart<String, Number> total_userDonChart;

    @FXML
    private AreaChart<String, Number> total_userRecycleChart;

    @FXML
    //private BarChart<String, Number> total_UserChart;
    private PieChart totalUserPieChart = new PieChart();


    @FXML
    private TextField medicament_search;

    private Connection con = Connexion.getInstance().getCnx();


    // Méthodes pour récupérer les données depuis la base de données et les afficher dans les composants JavaFX

    public void updateUserDon() {
        String sql = "SELECT COUNT(id) FROM don";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                total_userDon.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void updateUserRecycle() {
        String sql = "SELECT COUNT(id) FROM recycles";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                total_userRecycle.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserTotal() {
        String sql = "SELECT COUNT(id) FROM users";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                total_User.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserDonChart() {
        total_userDonChart.getData().clear();
        String sql = "SELECT created_at, COUNT(id) FROM don WHERE quantite GROUP BY created_at ORDER BY TIMESTAMP(created_at) ASC LIMIT 5";
        updateChart(total_userDonChart, sql);
    }

    public void updateUserRecycleChart() {
        total_userRecycleChart.getData().clear();
        String sql = "SELECT created_at, COUNT(id) FROM recycles WHERE quantite GROUP BY created_at ORDER BY TIMESTAMP(created_at) ASC LIMIT 5";
        updateChart(total_userRecycleChart, sql);
    }

    //public void updateUserChart() {
       // total_UserChart.getData().clear();
       // String sql = "SELECT created_at, COUNT(id) FROM users  GROUP BY created_at ORDER BY TIMESTAMP(created_at) ASC LIMIT 5";
       // updateChart(total_UserChart, sql);
    //}

    private void updateChart(XYChart<String, Number> chart, String sql) {
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (rs.next()) {
                // Vérifiez si la catégorie récupérée de la base de données est null
                String category = rs.getString("created_at");
                if (category != null) {
                    series.getData().add(new XYChart.Data<>(category, rs.getInt(2)));
                }
            }
            chart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserPieChart() {
        totalUserPieChart.getData().clear();

        // Obtenir le nombre total d'utilisateurs
        int totalUsers = getTotalUsers();

        // Obtenir le nombre total de dons
        int totalDonations = getTotalDonations();

        // Obtenir le nombre total de recyclages
        int totalRecycles = getTotalRecycles();

        // Calculer le pourcentage de dons et de recyclages par rapport au nombre total d'utilisateurs
        double donationPercentage = (double) totalDonations / totalUsers * 100;
        double recyclingPercentage = (double) totalRecycles / totalUsers * 100;

        // Ajouter les données au PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Dons", donationPercentage),
                new PieChart.Data("Recyclages", recyclingPercentage)
        );

        totalUserPieChart.setData(pieChartData);
    }

    private int getTotalUsers() {
        String sql = "SELECT COUNT(id) AS total_users FROM users";
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total_users");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retourner une valeur par défaut en cas d'erreur
    }

    private int getTotalDonations() {
        String sql = "SELECT COUNT(id) AS total_donations FROM don";
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total_donations");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retourner une valeur par défaut en cas d'erreur
    }

    private int getTotalRecycles() {
        String sql = "SELECT COUNT(id) AS total_recycles FROM recycles";
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total_recycles");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retourner une valeur par défaut en cas d'erreur
    }

    public void addMedicamentSearch() {
        // Ajoutez ici le code pour rechercher des médicaments
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

    public void close() {
        System.exit(0);
    }

  //  public void minimize() {
        //Stage stage = (Stage) total_UserChart.getScene().getWindow();
        //stage.setIconified(true);
    //}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUserDon();
        updateUserRecycle();
        updateUserTotal();
        updateUserDonChart();
        updateUserRecycleChart();
        updateUserPieChart();
    }
    private void loadPage(String pageName, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleMedicamentButtonAction(ActionEvent event) {
        loadPage("Medicament.fxml", event);
    }

    @FXML
    private void handleUsersButtonAction(ActionEvent event) {
        loadPage("Users.fxml", event);
    }
}
