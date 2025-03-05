package org.example.pitest.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Services.ParcoursService;

public class AjoutModificationParcoursController {

    @FXML
    private TextField nomField, pickupField, destinationField, distanceField, timeField,
            latDestField, lngDestField, latPickupField, lngPickupField;

    private final ParcoursService parcoursService = ParcoursService.getInstance();
    private Parcours parcours;

    public void initData(Parcours parcours) {
        if (parcours != null) {
            this.parcours = parcours;
            nomField.setText(parcours.getName());
            pickupField.setText(parcours.getPickup());
            destinationField.setText(parcours.getDestination());
            latDestField.setText(String.valueOf(parcours.getLatDest()));
            lngDestField.setText(String.valueOf(parcours.getLngDest()));
            latPickupField.setText(String.valueOf(parcours.getLatPickup()));
            lngPickupField.setText(String.valueOf(parcours.getLngPickup()));
            distanceField.setText(String.valueOf(parcours.getDistance()));
            timeField.setText(String.valueOf(parcours.getTime()));
        }
    }

    @FXML
    private void saveParcours(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        try {
            String nom = nomField.getText().trim();
            String pickup = pickupField.getText().trim();
            String destination = destinationField.getText().trim();
            double latDest = Double.parseDouble(latDestField.getText().trim());
            double lngDest = Double.parseDouble(lngDestField.getText().trim());
            double latPickup = Double.parseDouble(latPickupField.getText().trim());
            double lngPickup = Double.parseDouble(lngPickupField.getText().trim());
            int distance = Integer.parseInt(distanceField.getText().trim());
            int time = Integer.parseInt(timeField.getText().trim());

            if (parcours == null) {
                Parcours newParcours = new Parcours(0, nom, pickup, destination,
                        latDest, lngDest, latPickup, lngPickup, distance, time);
                parcoursService.add(newParcours);
            } else {
                parcours.setName(nom);
                parcours.setPickup(pickup);
                parcours.setDestination(destination);
                parcours.setLatDest(latDest);
                parcours.setLngDest(lngDest);
                parcours.setLatPickup(latPickup);
                parcours.setLngPickup(lngPickup);
                parcours.setDistance(distance);
                parcours.setTime(time);
                parcoursService.update(parcours, parcours.getId());
            }

            closeWindow(event);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides pour les champs numériques.");
        }
    }

    private boolean validateInput() {
        // Vérifier si les champs sont vides après suppression des espaces
        if (nomField.getText().trim().isEmpty() || pickupField.getText().trim().isEmpty() ||
                destinationField.getText().trim().isEmpty() || latDestField.getText().trim().isEmpty() ||
                lngDestField.getText().trim().isEmpty() || latPickupField.getText().trim().isEmpty() ||
                lngPickupField.getText().trim().isEmpty() || distanceField.getText().trim().isEmpty() ||
                timeField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return false;
        }

        try {
            // Utiliser trim() pour éviter les espaces avant/après les nombres
            Double.parseDouble(latDestField.getText().trim());
            Double.parseDouble(lngDestField.getText().trim());
            Double.parseDouble(latPickupField.getText().trim());
            Double.parseDouble(lngPickupField.getText().trim());
            Integer.parseInt(distanceField.getText().trim());
            Integer.parseInt(timeField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les coordonnées, la distance et le temps doivent être des nombres valides.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private ParcoursController ParcoursController;

    public void setParcoursController(ParcoursController parcoursController) {
        this.ParcoursController = parcoursController;
    }
}