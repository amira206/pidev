package org.example.pitest.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.pitest.Model.Offre;
import org.example.pitest.Model.User;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Services.OffreService;
import org.example.pitest.Services.UserService;
import org.example.pitest.Services.ParcoursService;

import java.time.LocalDate;
import java.util.List;

public class AjouterOffreController {
    @FXML
    private TextField nbPlacesField;
    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private TextField prixField;
    @FXML
    private ComboBox<Parcours> parcoursComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox bagageCheckBox;

    private final OffreService offreService = OffreService.getInstance();
    private final UserService userService = new UserService();
    private final ParcoursService parcoursService = new ParcoursService();

    @FXML
    public void initialize() {
        userComboBox.getItems().addAll(userService.findAll());
        parcoursComboBox.getItems().addAll(parcoursService.findAll());
    }


    @FXML
    private void AjouterOffre() {
        try {
            int nbPlaces = Integer.parseInt(nbPlacesField.getText());
            User user = userComboBox.getValue();
            double prix = Double.parseDouble(prixField.getText());
            Parcours parcours = parcoursComboBox.getValue();
            LocalDate date = datePicker.getValue();
            boolean bagage = bagageCheckBox.isSelected();

            if (user != null && parcours != null && date != null) {
                Offre offre = new Offre(0, nbPlaces, user, prix, parcours, date, bagage);
                offreService.add(offre);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre ajoutée avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer des valeurs valides.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}