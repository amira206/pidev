package org.example.pitest.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        try {
            String nom = nomField.getText();
            String pickup = pickupField.getText();
            String destination = destinationField.getText();
            double latDest = Double.parseDouble(latDestField.getText());
            double lngDest = Double.parseDouble(lngDestField.getText());
            double latPickup = Double.parseDouble(latPickupField.getText());
            double lngPickup = Double.parseDouble(lngPickupField.getText());
            int distance = Integer.parseInt(distanceField.getText());
            int time = Integer.parseInt(timeField.getText());

            if (parcours == null) {
                Parcours newParcours = new Parcours(0, nom, pickup, destination,
                        latDest, lngDest, latPickup, lngPickup, distance, time);
                System.out.println(newParcours);
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
            System.err.println("Erreur : Veuillez entrer des valeurs valides pour les coordonnées, la distance et le temps.");
        }
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