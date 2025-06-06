
package GestionEvenement3a16.Controller.ReclamationController;

import GestionEvenement3a16.Entity.Reclamation;
import GestionEvenement3a16.Entity.Reponse;
import GestionEvenement3a16.Entity.User;
import GestionEvenement3a16.Services.EmailAPI;
import GestionEvenement3a16.Services.ReclamationService;
import GestionEvenement3a16.Services.ReponseService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AjouterRepense {
    @FXML
    private DatePicker datereponse;

    @FXML
    private TextArea textReponse;

    @FXML
    private Label DateError;

    @FXML
    private Label ReponseError;

    private Reclamation rec; // Assuming Reclamation class exists
    private AnchorPane detailanchpane; // Declare detailanchpane here

    public void setDetailAnchorPane(AnchorPane detailanchpane) {
        this.detailanchpane = detailanchpane;
    }
    public void setData(Reclamation rec) {
        this.rec = rec;
    }

    @FXML
    private void handleAjouterReponseButtonClick() {
        boolean isValidInput = true;

        // Create a new Reponse object
        Reponse newReponse = new Reponse();
        String descriptionValue = textReponse.getText();

        LocalDateTime dateValue = null;
        if (datereponse.getValue() != null) {
            if (datereponse.getValue().isBefore(LocalDate.now())) {
                DateError.setText("Date cannot be before the current date");
                isValidInput = false;
            } else {
                dateValue = datereponse.getValue().atStartOfDay();
                DateError.setText("");
                newReponse.setDate(dateValue);
            }
        } else {
            DateError.setText("Date cannot be null");
            isValidInput = false;
        }

        if (textReponse == null || textReponse.getText().trim().isEmpty()) {
            ReponseError.setText("Description cannot be empty");
            isValidInput = false;
        } else {
            ReponseError.setText("");
            newReponse.setReponse(descriptionValue);
        }

        if (isValidInput) {

            // Set the response text from the TextArea
            newReponse.setReponse(textReponse.getText());

            // Set the response date from the DatePicker
            newReponse.setDate(datereponse.getValue().atStartOfDay());

            // Set the reclamation for this response
            newReponse.setidReclamation(rec);

            User currentUser = rec.getUtilisateur(); // Get the user from the reclamation
            if(currentUser == null) {
                try {
                    throw new Exception("User is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            newReponse.setUtilisateur(currentUser);

            ReponseService reponseService = new ReponseService();
            reponseService.ajouterReponse(newReponse);

            rec.setEtat("Traité");
            ReclamationService reclamationService = new ReclamationService();
            reclamationService.modifierReclamation(rec);

            EmailAPI emailAPI = new EmailAPI();
            String username = rec.getNom()+ " " + rec.getPrenom();

            String htmlMessage = "<div style='font-family: Arial, sans-serif; background-color: #f2f2f2; padding: 20px;'>" +
                    "<div style='max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 5px;'>" +
                    "<h1 style='color: #3498db;'>Response Added</h1>" +
                    "<p style='color: #2c3e50;'>Dear " +username+" Nous vous informons que votre reclamation a recu une reponse. Veuillez consulter votre compte pour plus de details.</p>" +                    "<p style='color: #2c3e50; font-weight: bold;'>SPORTHUB</p>" +
                    "</div>" +
                    "</div>";

        /*    try {
                emailAPI.sendEmail(rec.getEmail(), "Response Added", htmlMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
*/
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Response Added");
            alert.setHeaderText(null);
            alert.setContentText("The response has been added successfully.");
            alert.showAndWait();
            try {
                SampleController.getInstance().loadRec();
            } catch (IOException e) {
                e.printStackTrace();
            }
            detailanchpane.getChildren().clear();
        }
    }
}
