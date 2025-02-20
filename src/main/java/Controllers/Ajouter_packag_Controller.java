package Controllers;
import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.entities.packag;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import piDev.GestionLIvariason.Livraison.services.ServicePackag;

public class Ajouter_packag_Controller {

    @FXML
    private TextField text_packaage_description;

    @FXML
    private Spinner<Integer> text_package_Weight;

    @FXML
    void ajouter_package(ActionEvent event) {
        packag packag = new packag(
                text_package_Weight.getValue(),
                text_packaage_description.getText()
                );
        ServicePackag sp = new ServicePackag();
        sp.ajouter(packag);

    }

}


