package org.example.pitest.Controllers;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.pitest.Model.Offre;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Model.User;
import org.example.pitest.Services.ParcoursService;

import java.time.LocalDate;
import java.util.Optional;

public class AddEditOffreDialog extends Dialog<Offre> {
    private final TextField nbPlacesField = new TextField();
    private final TextField prixField = new TextField();
    private final ComboBox<Parcours> parcoursComboBox = new ComboBox<>();
    private final DatePicker datePicker = new DatePicker();
    private final CheckBox bagageCheckBox = new CheckBox();

    public AddEditOffreDialog(Offre offre, ParcoursService parcoursService) {
        setTitle(offre == null ? "Ajouter une offre" : "Modifier l'offre");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Add form fields
        grid.add(new Label("Nombre de places:"), 0, 0);
        grid.add(nbPlacesField, 1, 0);

        grid.add(new Label("Prix:"), 0, 1);
        grid.add(prixField, 1, 1);

        grid.add(new Label("Parcours:"), 0, 2);
        parcoursComboBox.getItems().addAll(parcoursService.findAll());
        grid.add(parcoursComboBox, 1, 2);

        grid.add(new Label("Date:"), 0, 3);
        grid.add(datePicker, 1, 3);

        grid.add(new Label("Bagage autorisé:"), 0, 4);
        grid.add(bagageCheckBox, 1, 4);

        // If editing, populate fields with existing data
        if (offre != null) {
            nbPlacesField.setText(String.valueOf(offre.getNbPlaces()));
            prixField.setText(String.valueOf(offre.getPrix()));
            parcoursComboBox.setValue(offre.getParcours());
            datePicker.setValue(offre.getDate());
            bagageCheckBox.setSelected(offre.isBagage());
        } else {
            datePicker.setValue(LocalDate.now());
        }

        getDialogPane().setContent(grid);

        // Convert the result to Offre object when the save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int nbPlaces = Integer.parseInt(nbPlacesField.getText());
                    double prix = Double.parseDouble(prixField.getText());
                    Parcours parcours = parcoursComboBox.getValue();
                    LocalDate date = datePicker.getValue();
                    boolean bagage = bagageCheckBox.isSelected();

                    // Create a dummy user with ID 1 as requested
                    User user = new User();
                    user.setId(1);

                    if (offre == null) {
                        // Creating new offer
                        return new Offre(0, nbPlaces, user, prix, parcours, date, bagage);
                    } else {
                        // Updating existing offer
                        return new Offre(offre.getId(), nbPlaces, user, prix, parcours, date, bagage);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez vérifier les valeurs numériques saisies.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    }
}