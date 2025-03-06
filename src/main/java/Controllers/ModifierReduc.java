package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Reduction;
import services.ReductionService;

public class ModifierReduc {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox active;

    @FXML
    private CheckBox inactive;

    @FXML
    private TextField reductionM;

    @FXML
    private TextField userIdM;

    @FXML
    private DatePicker valableM;

    private ReductionService reductionService;
    private Reduction currentReduction;

    public void setReductionService(ReductionService reductionService) {
        this.reductionService = reductionService;
    }

    @FXML
    void annuler(ActionEvent event) {
        userIdM.clear();
        reductionM.clear();
        valableM.setValue(null);
        active.setSelected(false);
        inactive.setSelected(false);
    }

    @FXML
    void modifierRedu(ActionEvent event) {
        if (currentReduction == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune réduction à modifier", "Aucune réduction sélectionnée.");
            return;
        }

        int userId = 0;
        double reductionAmount = 0.0;

        try {
            userId = Integer.parseInt(userIdM.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID utilisateur invalide", "Veuillez entrer un ID utilisateur valide.");
            return;
        }

        try {
            reductionAmount = Double.parseDouble(reductionM.getText());
            if (reductionAmount <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Montant de réduction invalide", "Le montant de réduction doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Montant de réduction invalide", "Veuillez entrer un montant de réduction valide.");
            return;
        }

        if (valableM.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Date invalide", "Veuillez sélectionner une date de validité.");
            return;
        }

        LocalDate localDate = valableM.getValue();
        Date validUntil = java.sql.Date.valueOf(localDate);

        String status = active.isSelected() ? "active" : "inactive";

        boolean success = reductionService.modifierReduction(
                currentReduction.getReductionId(),
                userId,
                reductionAmount,
                validUntil.toString(),
                status
        );

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction modifiée", "La réduction a été modifiée avec succès.");
            annuler(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de modification", "Une erreur est survenue lors de la modification de la réduction.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setTextFields(Reduction reduction) {
        this.currentReduction = reduction;
        userIdM.setText(String.valueOf(reduction.getUserId()));
        reductionM.setText(String.valueOf(reduction.getReductionPercentage()));

        Date date = reduction.getValidUntil();

        if (date != null) {
            if (date instanceof java.sql.Date) {
                java.util.Date utilDate = new java.util.Date(date.getTime());
                LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                valableM.setValue(localDate);
            } else {
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                valableM.setValue(localDate);
            }
        } else {
            valableM.setValue(null);
        }

        if ("active".equalsIgnoreCase(reduction.getStatus())) {
            active.setSelected(true);
            inactive.setSelected(false);
        } else {
            active.setSelected(false);
            inactive.setSelected(true);
        }
    }

    @FXML
    void initialize() {
    }
}
