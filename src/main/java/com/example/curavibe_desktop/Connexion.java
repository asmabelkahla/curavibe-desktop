package com.example.curavibe_desktop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private String url="jdbc:mysql://localhost:3306/curavibe";
    private String login="root";
    private String pwd="";

    private static Connexion instance;
    Connection cnx;

    public Connexion(){
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie..");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static Connexion getInstance(){
        if(instance == null){
            instance = new Connexion();
        }
        return instance;
    }
}