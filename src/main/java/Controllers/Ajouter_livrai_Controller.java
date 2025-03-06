package Controllers;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;
import piDev.GestionLIvariason.Livraison.entities.packag;
import piDev.GestionLIvariason.Livraison.services.ServicePackag;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.List;


public class Ajouter_livrai_Controller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button Update_butt;
    @FXML
    private Button afficher_livrai_button;


    @FXML
    private URL location;

    @FXML
    private CheckBox checkBox_isDelivered;

    @FXML
    private TextField text_delivery_location;

    @FXML
    private TextField text_start_location;


    @FXML
    void ajouter(ActionEvent event) {
        livraison livraison = new livraison(
                text_start_location.getText(),
                text_delivery_location.getText(),
                checkBox_isDelivered.isSelected()
        );
        String startLocation = text_start_location.getText();
        String deliveryLocation = text_delivery_location.getText();

        // 🚨 Validate input (no special characters)
        if (!isValidText(startLocation) || !isValidText(deliveryLocation)) {
            showAlert("Invalid Input", "Start location and delivery location must contain only letters and numbers and not empty!");
            return;
        }

        ServiceLivraison sl = new ServiceLivraison();
        sl.ajouter(livraison); // The generated ID is now inside 'livraison'
        showAlert("Success", "Livraison added successfully!");

        int idLivraison = livraison.getId_livrai();

        if (idLivraison == 0) {
            System.out.println("❌ Error: Failed to retrieve generated id_livrai");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout_packag.fxml"));
            Parent root = loader.load();

            // ✅ Get the controller instance after loading the FXML
            Ajouter_packag_Controller controller = loader.getController();

            // ✅ Call setIdLivraison() to pass the ID
            controller.setIdLivraison(idLivraison);

            // ✅ Open the new page
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("✅ Navigated to ajout_packag.fxml with idLivraison: " + idLivraison);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading ajout_packag.fxml: " + e.getMessage());
        }
    }




    @FXML
    private void afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affich_livrai.fxml"));
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
    void goToUpdateScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/update_livrai.fxml"));
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
    void initialize() {
        assert checkBox_isDelivered != null : "fx:id=\"checkBox_isDelivered\" was not injected: check your FXML file 'ajout_livrai.fxml'.";
        assert text_delivery_location != null : "fx:id=\"text_delivery_location\" was not injected: check your FXML file 'ajout_livrai.fxml'.";
        assert text_start_location != null : "fx:id=\"text_start_location\" was not injected: check your FXML file 'ajout_livrai.fxml'.";
    }
    private boolean isValidText(String text) {
        return text.matches("[a-zA-Z0-9 ]+");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
