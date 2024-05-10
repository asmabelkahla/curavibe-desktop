package com.example.curavibe_desktop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MedicamentController implements Initializable {
 Connection con = null;
 PreparedStatement st=null ;
 ResultSet rs=null;


    @FXML
    private Button addMed_btn;

    @FXML
    private Button addMedicines_addBtn;

    @FXML
    private TextField addMedicines_brand;

    @FXML
    private Button addMedicines_clearBtn;

    @FXML
    private TableColumn<?, ?> addMedicines_col_brand;

    @FXML
    private TableColumn<?, ?> addMedicines_col_date;

    @FXML
    private TableColumn<?, ?> addMedicines_col_medicineID;

    @FXML
    private TableColumn<?, ?> addMedicines_col_price;

    @FXML
    private TableColumn<?, ?> addMedicines_col_productName;

    @FXML
    private TableColumn<?, ?> addMedicines_col_status;

    @FXML
    private TableColumn<?, ?> addMedicines_col_type;

    @FXML
    private Button addMedicines_deleteBtn;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private ImageView addMedicines_imageView;

    @FXML
    private Button addMedicines_importBtn;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private TableView<?> addMedicines_tableView;

    @FXML
    private ComboBox<?> addMedicines_type;

    @FXML
    private Button addMedicines_updateBtn;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button close;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button pruchase_btn;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_amount;

    @FXML
    private Label purchase_balance;

    @FXML
    private ComboBox<?> purchase_brand;


    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<?> purchase_medicineID;
    @FXML
    private TableColumn<Medicament, String> colAMM;

    @FXML
    private TableColumn<Medicament, String> colClasse;

    @FXML
    private TableColumn<Medicament, String> colDCI;

    @FXML
    private TableColumn<Medicament, String> colDosage;

    @FXML
    private TableColumn<Medicament, Integer> colDuree;

    @FXML
    private TableColumn<Medicament, String> colForme;

    @FXML
    private TableColumn<Medicament, String> colNom;

    @FXML
    private TableColumn<Medicament, Integer> colid;
    @FXML
    private Button purchase_payBtn;

    @FXML
    private ComboBox<?> purchase_productName;

    @FXML
    private Spinner<?> purchase_quantity;

    @FXML
    private TableView<Medicament> purchase_tableView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMedicaments();
    }

    public ObservableList<Medicament> getMedicaments(){
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
        String query = "select * from medicaments";
        Connexion connexion = Connexion.getInstance(); // Use Connexion class
        con = connexion.getCnx(); // Access connection from Connexion class
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                Medicament md = new Medicament();
                md.setId(rs.getInt("id"));
                md.setNom(rs.getString("Nom"));
                md.setDosage(rs.getString("Dosage"));
                md.setForme(rs.getString("Forme"));
                md.setDCI(rs.getString("DCI"));
                md.setClasse(rs.getString("Classe"));
                md.setAMM(rs.getString("AMM"));
                md.setDuree(rs.getInt("Duree"));
                medicaments.add(md);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return medicaments;
    }


    public  void showMedicaments(){
        ObservableList <Medicament> list = getMedicaments();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Medicament,Integer> ("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Nom"));
colDosage.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Dosage"));
colForme.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Forme"));
colDCI.setCellValueFactory(new PropertyValueFactory<Medicament,String>("DCI"));
colClasse.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Classe"));
colAMM.setCellValueFactory(new PropertyValueFactory<Medicament,String>("AMM"));
colDuree.setCellValueFactory(new PropertyValueFactory<Medicament,Integer>("Durée"));
    }
    @FXML
    private Label purchase_total;

    @FXML
    private ComboBox<?> purchase_type;

    @FXML
    private TextField tAMM;

    @FXML
    private TextField tClasse;

    @FXML
    private TextField tDCI;

    @FXML
    private TextField tDosage;

    @FXML
    private TextField tDuree;

    @FXML
    private TextField tForme;

    @FXML
    private TextField tNom;

    @FXML
    private TableColumn<?, ?> purchase_col_brand;

    @FXML
    private TableColumn<?, ?> purchase_col_medicineId;

    @FXML
    private TableColumn<?, ?> purchase_col_price;

    @FXML
    private TableColumn<?, ?> purchase_col_productName;

    @FXML
    private TableColumn<?, ?> purchase_col_qty;

    @FXML
    private TableColumn<?, ?> purchase_col_type;

    @FXML
    private TableView<Medicament> table;

    @FXML
    private Label username;

    @FXML
    void addMedicament(ActionEvent event) {
String insert= "insert into medicaments ( Nom, Dosage, Forme, DCI, Classe, AMM, Duree) values (?,?,?,?,?,?,?,";
    }

    @FXML
    void addMedicineDelete(ActionEvent event) {

    }

    @FXML
    void addMedicineImportImage(ActionEvent event) {

    }

    @FXML
    void addMedicineListStatus(ActionEvent event) {

    }

    @FXML
    void addMedicineListType(ActionEvent event) {

    }

    @FXML
    void addMedicineSearch(KeyEvent event) {

    }

    @FXML
    void addMedicineSelect(MouseEvent event) {

    }

    @FXML
    void addMedicineUpdate(ActionEvent event) {

    }

    @FXML
    void addMedicinesAdd(ActionEvent event) {

    }

    @FXML
    void clearField(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void deleteMedicament(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event) {

    }

    @FXML
    void purchaseAdd(ActionEvent event) {

    }

    @FXML
    void purchaseAmount(ActionEvent event) {

    }

    @FXML
    void purchaseBrand(ActionEvent event) {

    }

    @FXML
    void purchaseMedicineId(ActionEvent event) {

    }

    @FXML
    void purchasePay(ActionEvent event) {

    }

    @FXML
    void purchaseProductName(ActionEvent event) {

    }

    @FXML
    void purchaseQuantity(MouseEvent event) {

    }

    @FXML
    void purchaseType(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

    }

    @FXML
    void updateMedicament(ActionEvent event) {

    }


}
