package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ReductionService;

public class AjoutReduc {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox Active;

    @FXML
    private CheckBox Inactive;

    @FXML
    private TextField reductionA;

    @FXML
    private TextField userID;

    @FXML
    private DatePicker validUntilA;

    private ReductionService reductionService ;
    public AjoutReduc(){
        ReductionService reductionService = new ReductionService();
    }
    @FXML
    void ajouter(ActionEvent event) {
        try {
            String userIdText = userID.getText();
            String reductionText = reductionA.getText();
            String validUntilText = validUntilA.getValue().toString();

            if (userIdText.isEmpty() || reductionText.isEmpty() || validUntilText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Champs incomplets", "Veuillez remplir tous les champs.");
                return;
            }

            int userId = Integer.parseInt(userIdText);

            double reductionAmount = Double.parseDouble(reductionText);

            if (!Active.isSelected() && !Inactive.isSelected()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Statut manquant", "Veuillez sélectionner un statut (Actif/Inactif).");
                return;
            }

            String status = Active.isSelected() ? "active" : "inactive";

            boolean success = reductionService.ajouterReduction(userId, reductionAmount, validUntilText, status);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction ajoutée", "La réduction a été ajoutée avec succès.");
                navigateToList(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur d'ajout", "Une erreur est survenue lors de l'ajout de la réduction.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Données invalides", "Veuillez entrer des valeurs numériques valides.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void annuler(ActionEvent event) {
        userID.clear();
        reductionA.clear();
        validUntilA.setValue(null);
        Active.setSelected(false);
        Inactive.setSelected(false);
    }

    @FXML
    void initialize() {
        if (reductionService == null) {
            reductionService = new ReductionService(); // Assurez-vous que cette ligne crée une nouvelle instance de votre service
        }
    }

    private void navigateToList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReduc.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur", "Une erreur est survenue lors du chargement de l'écran de connexion.");
        }
    }

}
