package com.example.curavibe_desktop;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import static javafx.application.Application.launch;

public class EmailSender {

    public static void sendEmail(String recipient, String subject, String body) throws MessagingException {

        // Configuration properties for SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Remplacez par votre serveur SMTP
        props.put("mail.smtp.port", "587"); // Remplacez par le port SMTP

        // Informations d'authentification SMTP
        String username = "wafabenfatma@gmail.com";
        String password = "xcectzqsxpejkkmv";

        // Création d'une session avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création d'un objet MimeMessage
            Message message = new MimeMessage(session);
            // Définition de l'adresse de l'expéditeur
            message.setFrom(new InternetAddress(username));
            // Définition de l'adresse du destinataire
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            // Définition du sujet de l'e-mail
            message.setSubject(subject);
            // Définition du corps de l'e-mail
            message.setText(body);
            // Envoi de l'e-mail
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + recipient);
        } catch (MessagingException e) {
            throw new MessagingException("Échec de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
