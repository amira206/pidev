package org.example.pitest.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.pitest.Model.Reservation;
import org.example.pitest.Model.User;
import org.example.pitest.Model.Offre;
import org.example.pitest.Services.ReservationService;

public class ReservationController {

    private ReservationService reservationService = ReservationService.getInstance();
    private User currentUser; // À récupérer depuis le système d'authentification
    private Offre selectedOffre; // À récupérer depuis la sélection utilisateur

    @FXML
    private Button btnReserver;

    public void initialize() {
        // Initialisation des données si nécessaire
    }

    @FXML
    private void handleReserver(ActionEvent event) {
        try {
            // Création de la réservation
            Reservation reservation = new Reservation();
            reservation.setUser(currentUser);
            reservation.setOffre(selectedOffre);
            reservation.setPrice(selectedOffre.getPrix()); // Supposons que Offre a un getPrice()

            // Ajout de la réservation
            reservationService.add(reservation);

            showAlert("Succès", "Réservation effectuée avec succès!");
            closeWindow();
        } catch (Exception e) {
            showAlert("Erreur", "Échec de la réservation: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnReserver.getScene().getWindow();
        stage.close();
    }

    // Méthodes pour injecter les données
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setSelectedOffre(Offre offre) {
        this.selectedOffre = offre;
    }
}