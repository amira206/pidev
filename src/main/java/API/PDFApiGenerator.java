package API;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import models.User;

public class PDFApiGenerator {

    public static void generateUserPdf(User user) {
        // Définir le chemin du fichier PDF
        String dest = "C:/Users/user/Desktop/PDF.pdf" + user.getUserName() + ".pdf";  // Changez le chemin si nécessaire

        // Affichage du chemin pour vérification
        System.out.println("PDF sauvegardé dans : " + dest);

        try {
            // Créer un PdfWriter
            PdfWriter writer = new PdfWriter(dest);

            // Créer un PdfDocument
            PdfDocument pdf = new PdfDocument(writer);

            // Créer un Document pour ajouter des éléments
            Document document = new Document(pdf);

            // Ajouter les informations de l'utilisateur au PDF
            document.add(new Paragraph("Id: " + user.getId()));
            document.add(new Paragraph("Nom: " + user.getUserName()));
            document.add(new Paragraph("Email: " + user.getEmail()));
            document.add(new Paragraph("Role: " + user.getRole().name()));

            // Fermer le document
            document.close();

            // Confirmation dans la console
            System.out.println("PDF généré pour l'utilisateur : " + user.getUserName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
