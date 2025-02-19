package GestionEvenement3a16.Controller;

import GestionEvenement3a16.Entity.Evenement;
import GestionEvenement3a16.Entity.Moyen_De_Transport;
import GestionEvenement3a16.Services.EvenementService;
import GestionEvenement3a16.Services.MoyenDeTransportService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class MoyenDeTransportCard {

    @FXML
    private Label Date;

    @FXML
    private Label Nbre_places;

    @FXML
    private Pane PaneMoyenDeTransport;

    @FXML
    private Label Prix;

    @FXML
    private Label Type;

    @FXML
    private Label name;

    @FXML
    private Button modifier;

    @FXML
    private ImageView modifiericon1;

    @FXML
    private ImageView suppicon;

    @FXML
    private Button supprimer;

    private Evenement event;
    private Moyen_De_Transport moyenDeTransport;
    private MoyenDeTransportService moyenDeTransportService = new MoyenDeTransportService();
    private EvenementService evenementService = new EvenementService();

    private MoyenDeTransportBack moyenDeTransportBack;

    public void setMoyenDeTransportBack(MoyenDeTransportBack moyenDeTransportBack) {
        this.moyenDeTransportBack = moyenDeTransportBack;
    }

    @FXML
    public void initialize() {
        supprimer.setOnAction(e -> deleteMoyenDeTransport());

        modifier.setOnAction(e -> {
            if (moyenDeTransportBack != null) {
                moyenDeTransportBack.populateFields(moyenDeTransport);
            }
        });
    }

public void setData(Moyen_De_Transport moyenDeTransport, String eventName) {
    this.moyenDeTransport = moyenDeTransport;
    try {
        this.moyenDeTransport = moyenDeTransportService.getMoyenDeTransportByEventId(moyenDeTransport.getEvenementId());
        this.event = evenementService.getEventById(moyenDeTransport.getEvenementId());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    name.setText(eventName);
    Date.setText(event.getDateEvenement().toString());
    Nbre_places.setText(Integer.toString(moyenDeTransport.getnbrePlaces()));
    Type.setText(moyenDeTransport.getType());
    Prix.setText(Integer.toString(moyenDeTransport.getPrix()));
}

/*
public void deleteTicket() {
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirmation");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Vous êtes sûr de supprimer ce ticket?");

    Optional<ButtonType> result = confirmationAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            ticketService.deleteTicket(ticket.getId());
            // Refresh the page
            PaneTicket.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Ticket supprimé avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

*/
@FXML
private void deleteMoyenDeTransport() {
    moyenDeTransportBack.setSelectedMoyenDeTransport(moyenDeTransport);
    moyenDeTransportBack.deleteMoyenDeTransport();
}

}