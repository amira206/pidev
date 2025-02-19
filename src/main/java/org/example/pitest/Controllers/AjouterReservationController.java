package org.example.pitest.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.pitest.Model.Reservation;
import org.example.pitest.Model.User;
import org.example.pitest.Model.Offre;
import org.example.pitest.Services.ReservationService;
import org.example.pitest.Services.UserService;
import org.example.pitest.Services.OffreService;

import java.util.List;

public class AjouterReservationController {

    @FXML
    private TextField userIdField;

    @FXML
    private ComboBox<Offre> offreComboBox;

    @FXML
    private TextField priceField;

    @FXML
    private Button addButton;

    private final ReservationService reservationService = ReservationService.getInstance();
    private final UserService userService = new UserService();
    private final OffreService offreService = new OffreService();

    @FXML
    public void initialize() {
        loadOffres();
    }

    private void loadOffres() {
        List<Offre> offres = offreService.findAll();
        ObservableList<Offre> offreList = FXCollections.observableArrayList(offres);
        offreComboBox.setItems(offreList);
    }

    @FXML
    private void handleAddReservation() {
        String userIdText = userIdField.getText();
        Offre selectedOffre = offreComboBox.getValue();
        String priceText = priceField.getText();

        if (userIdText.isEmpty() || selectedOffre == null || priceText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText);
            double price = Double.parseDouble(priceText);
            User user = userService.find(userId);

            if (user == null) {
                showAlert("Erreur", "Utilisateur introuvable !");
                return;
            }

            Reservation newReservation = new Reservation(0, user, selectedOffre, price);
            reservationService.add(newReservation);
            showAlert("Succès", "Réservation ajoutée avec succès !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID utilisateur et prix doivent être des nombres valides.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
