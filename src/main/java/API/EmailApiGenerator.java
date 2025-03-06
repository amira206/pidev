package  API;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailApiGenerator {

    // Méthode pour envoyer un email
    public static boolean sendEmail(String recipientEmail, String subject, String body) {
        // Configuration du serveur SMTP (par exemple, Gmail)
        String host = "smtp.gmail.com"; // Utilisez le serveur SMTP de votre fournisseur
        final String senderEmail = "your-email@gmail.com"; // Votre adresse email
        final String password = "your-email-password"; // Votre mot de passe

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Authentification de l'expéditeur
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            // Création du message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Envoi de l'email
            Transport.send(message);
            System.out.println("Email envoyé avec succès !");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur d'envoi de l'email !");
            return false;
        }
    }
}
