package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;

public class Update_Livrai_Controller {

    @FXML
    private Button Update_butt;

    @FXML
    private CheckBox checkBox_isDelivered_update;

    @FXML
    private TextField text_delivery_location_update;

    @FXML
    private TextField text_start_location_update;

    @FXML
    private TextField id_delivery_update;

    @FXML
    void goToUpdateScene(ActionEvent event) {

    }
    @FXML
    private void returnToAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout_livrai.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void updateLivraison(ActionEvent event) {
        livraison livraison = new livraison(
                Integer.parseInt(id_delivery_update.getText()),
                text_start_location_update.getText(),
                text_delivery_location_update.getText(),
                checkBox_isDelivered_update.isSelected());
        ServiceLivraison sl = new ServiceLivraison();
        sl.modifier(livraison);

    }

}