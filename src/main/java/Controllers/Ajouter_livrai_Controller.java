package Controllers;

import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Ajouter_livrai_Controller {

    @FXML
    private ResourceBundle resources;

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
        livraison livraison = new livraison(text_start_location.getText(),
                text_delivery_location.getText(),
                checkBox_isDelivered.isSelected());
        ServiceLivraison sl = new ServiceLivraison();
        sl.ajouter(livraison);
    }

    @FXML
    void initialize() {
        assert checkBox_isDelivered != null : "fx:id=\"checkBox_isDelivered\" was not injected: check your FXML file 'ajout_livrai.fxml'.";
        assert text_delivery_location != null : "fx:id=\"text_delivery_location\" was not injected: check your FXML file 'ajout_livrai.fxml'.";
        assert text_start_location != null : "fx:id=\"text_start_location\" was not injected: check your FXML file 'ajout_livrai.fxml'.";

    }

}
