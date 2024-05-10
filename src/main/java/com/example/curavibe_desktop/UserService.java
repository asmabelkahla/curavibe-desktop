package com.example.curavibe_desktop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {
    // Méthode pour mettre à jour le mot de passe dans la base de données
    public void updatePassword(String email, String newPassword) throws SQLException {
        // Connexion à votre base de données
        Connexion connexion = Connexion.getInstance();
        Connection connection = connexion.getCnx();

        // Requête SQL pour mettre à jour le mot de passe
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setString(2, email);

            // Exécution de la requête
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur : " + email);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'e-mail : " + email);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            throw e;
        } finally {
            // Fermer la connexion à la base de données
            connection.close();
        }
    }
}
