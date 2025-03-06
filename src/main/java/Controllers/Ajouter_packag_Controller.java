package Controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.entities.packag;

import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import piDev.GestionLIvariason.Livraison.services.ServicePackag;

import java.io.IOException;

public class Ajouter_packag_Controller {
    private int idLivraison; // Store the livraison ID

    // ✅ Add this method inside your Ajouter_packag_Controller class
    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
        System.out.println("✅ Received idLivraison in Ajouter_packag_Controller: " + idLivraison);
    }

    @FXML
    private TextField description_text;

    @FXML
    private TextField weight_text;

    @FXML
    void add_package(ActionEvent event) {
        if (idLivraison == 0) {
            System.out.println("❌ Error: No livraison ID received!");
            return;
        }

        if (weight_text.getText().isEmpty() || description_text.getText().isEmpty()) {
            System.out.println("❌ Error: Please fill in all fields.");
            showAlert("Missing Informations", "Fill in all fields!");
            return;

        }
        String description = description_text.getText();
        String weightInput = weight_text.getText();

        // 🚨 Validate description (letters & numbers only)
        if (!isValidText(description)) {
            showAlert("Invalid Input", "Description must contain only letters and numbers!");
            return;
        }

        // 🚨 Validate weight (numbers only)
        if (!isValidNumber(weightInput)) {
            showAlert("Invalid Input", "Weight must be a valid number!");
            return;
        }

        int weight;
        try {
            weight = Integer.parseInt(weight_text.getText()); // Convert input to integer
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Weight must be a number!");
            return;
        }

        // ✅ Create a package linked to the correct livraison
        packag packag = new packag(
                weight, // Now correctly using the weight from the TextField
                description_text.getText(),
                idLivraison
        );

        ServicePackag sp = new ServicePackag();
        sp.ajouter(packag);
        showAlert("Success", "Package added successfully!");


        System.out.println("✅ Package added for livraison ID: " + idLivraison);
try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affich_packag_controller.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();} catch (Exception e)
{
    throw new RuntimeException(e);
}
    }


    @FXML
    void afficher_packag(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affich_packag_controller.fxml"));
            Parent root = loader.load();

            // Get the current stage using the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the existing stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void update_packag(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        if (description_text == null) {
            System.out.println("❌ Error: description_text is null! Check FXML file.");
        } else {
            System.out.println("✅ description_text is correctly initialized.");
        }

        if (weight_text == null) {
            System.out.println("❌ Error: weight_text is null! Check FXML file.");
        } else {
            System.out.println("✅ weight_text is correctly initialized.");
        }
    }
    private boolean isValidText(String text) {
        return text.matches("[a-zA-Z0-9 ]+");
    }

    private boolean isValidNumber(String text) {
        return text.matches("\\d+");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



