package  API;
import com.sendgrid.*;
import java.io.IOException;
import java.util.Random;

public class EmailApiGenerator {
    private static final String SENDGRID_API_KEY = "TA_CLE_API"; // Mets ici ta clé API SendGrid
    private static final String FROM_EMAIL = "ton-email@example.com"; // Mets ici ton email vérifié
    private static String verificationCode;

    // Générer un code aléatoire à 6 chiffres
    public static void generateCode() {
        Random random = new Random();
        verificationCode = String.valueOf(100000 + random.nextInt(900000)); // Code entre 100000 et 999999
    }

    // Récupérer le code généré
    public static String getVerificationCode() {
        return verificationCode;
    }

    // Envoyer l'email avec le code de vérification
    public static boolean sendEmail(String toEmail) {
        // Créer l'email
        Email from = new Email(FROM_EMAIL);
        String subject = "Code de vérification - Réinitialisation du mot de passe";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Votre code de vérification est : " + verificationCode);
        Mail mail = new Mail(from, subject, to, content);

        // Envoi via SendGrid
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getStatusCode() == 202;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

   
}
