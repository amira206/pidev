package GestionEvenement3a16.Controller;

import GestionEvenement3a16.Entity.Evenement;
import GestionEvenement3a16.Entity.Moyen_De_Transport;
import GestionEvenement3a16.Services.EvenementService;
import GestionEvenement3a16.Services.MoyenDeTransportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MoyenDeTransportBack {

    @FXML
    private Button ajouterMoyenDeTransport;

    @FXML
    private Button hide;

    @FXML
    private ImageView modifiericon;

    @FXML
    private AnchorPane root3;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane moyenDeTransportContainerBack;

    @FXML
    private Button moyenDeTransport_add;

    @FXML
    private Button moyenDeTransport_clear;

    @FXML
    private ComboBox<String> moyenDeTransport_evenement;

    @FXML
    private TextField moyenDeTransport_nbre;

    @FXML
    private TextField moyenDeTransport_prix;

    @FXML
    private TextField moyenDeTransport_type;

    @FXML
    private AnchorPane ajouterPane;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    void addMoyenDeTransport(MouseEvent event) {

    }

    @FXML
    void moyenDeTransportClear(MouseEvent event) {

    }


    private EvenementService evenementService;
    private MoyenDeTransportService moyenDeTransportService;
    private int currentMoyenDeTransportId;

    // Add these getter methods to your MoyenDeTransportBack class
    public AnchorPane getAjouterPane() {
        return ajouterPane;
    }

    public TextField getMoyenDeTransport_prix() {
        return moyenDeTransport_prix;
    }

    public TextField getMoyenDeTransport_type() {
        return moyenDeTransport_type;
    }

    public TextField getMoyenDeTransport_nbre() {
        return moyenDeTransport_nbre;
    }

    public ComboBox<String> getMoyenDeTransport_evenement() {
        return moyenDeTransport_evenement;
    }

    public MoyenDeTransportBack() {
        evenementService = new EvenementService();
        moyenDeTransportService = new MoyenDeTransportService();
    }

    private Moyen_De_Transport selectedMoyenDeTransport;

    public void setSelectedMoyenDeTransport(Moyen_De_Transport moyenDeTransport) {
        this.selectedMoyenDeTransport = moyenDeTransport;
    }

    @FXML
    public void initialize() {

        // Bind the visibility of the hide button to the visibility of ajouterPane
        hide.visibleProperty().bind(ajouterPane.visibleProperty());

        ajouterPane.setVisible(false); // Make ajouterPane not visible

        ajouterMoyenDeTransport.setOnAction(e -> {
            ajouterPane.setVisible(true);
        });
        hide.setOnAction(e -> {
            ajouterPane.setVisible(false);
        });

        loadEvents();
        // Get all moyenDeTransports
        List<Moyen_De_Transport> moyenDeTransports = null;
        try {
            moyenDeTransports = moyenDeTransportService.getAllMoyenDeTransports();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Call showListMoyenDeTransport method
        if (moyenDeTransports != null) {
            showListMoyenDeTransport(moyenDeTransports);
        }


    }

private void loadEvents() {
        try {
            List<String> eventNames = evenementService.getAllEventNames();
            ObservableList<String> observableList = FXCollections.observableArrayList(eventNames);
            moyenDeTransport_evenement.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Unable to load events from the database.");
            alert.showAndWait();
        }
    }

@FXML
public void addMoyenDeTransport1() {
    try {
        Alert alert;
        int prix = 0;
        int nbre = 0;
        boolean isPrixInteger = true;
        boolean isNbreInteger = true;

        try {
            prix = Integer.parseInt(moyenDeTransport_prix.getText());
        } catch (NumberFormatException e) {
            isPrixInteger = false;
        }

        try {
            nbre = Integer.parseInt(moyenDeTransport_nbre.getText());
        } catch (NumberFormatException e) {
            isNbreInteger = false;
        }

        if (moyenDeTransport_prix.getText().isEmpty() || moyenDeTransport_type.getText().isEmpty() || moyenDeTransport_nbre.getText().isEmpty() || moyenDeTransport_evenement.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Les champs sont obligatoires");
            alert.showAndWait();
        } else if (!isPrixInteger && !isNbreInteger) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix et Nombre de moyenDeTransports sont des entiers");
            alert.showAndWait();
        } else if (!isPrixInteger) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix doit être un entier");
            alert.showAndWait();
        } else if (!isNbreInteger) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nombre de moyenDeTransports doit être un entier");
            alert.showAndWait();
        } else if (prix <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix doit être supérieur à zéro");
            alert.showAndWait();
        } else if (nbre <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nombre de moyenDeTransports doit être supérieur à zéro");
            alert.showAndWait();
        } else {
            String selectedEventName = moyenDeTransport_evenement.getSelectionModel().getSelectedItem();
            Evenement selectedEvent = evenementService.getEventByName(selectedEventName);
            Moyen_De_Transport existingMoyenDeTransport = moyenDeTransportService.getMoyenDeTransportByEventId(selectedEvent.getId());


                String type = moyenDeTransport_type.getText();

                Moyen_De_Transport moyenDeTransport = new Moyen_De_Transport();
                moyenDeTransport.setEvenementId(selectedEvent.getId());
                moyenDeTransport.setPrix(prix);
                moyenDeTransport.setType(type);
                moyenDeTransport.setnbrePlaces(nbre);

                moyenDeTransportService.addMoyenDeTransport(moyenDeTransport);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

               List<Moyen_De_Transport> moyenDeTransports = moyenDeTransportService.getAllMoyenDeTransports();
showListMoyenDeTransport(moyenDeTransports);

        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void showListMoyenDeTransport(List<Moyen_De_Transport> moyenDeTransports) {
        try {
            // Clear the container before repopulating it
            moyenDeTransportContainerBack.getChildren().clear();

            for (int i = 0; i < moyenDeTransports.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GestionEvenement3a16/MoyenDeTransportCard.fxml"));
                Pane pane = fxmlLoader.load();

                MoyenDeTransportCard controller = fxmlLoader.getController();
                controller.setData(moyenDeTransports.get(i), evenementService.getEventNameById(moyenDeTransports.get(i).getEvenementId()));
                controller.setMoyenDeTransportBack(this);

                // Add the pane to the GridPane at the specified column and row
                moyenDeTransportContainerBack.add(pane, i % 3, i / 3);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

public void populateFields(Moyen_De_Transport moyenDeTransport) {
    ajouterPane.setVisible(true);
    moyenDeTransport_prix.setText(String.valueOf(moyenDeTransport.getPrix()));
    moyenDeTransport_type.setText(moyenDeTransport.getType());
    moyenDeTransport_nbre.setText(String.valueOf(moyenDeTransport.getnbrePlaces()));
    try {
        moyenDeTransport_evenement.setValue(evenementService.getEventNameById(moyenDeTransport.getEvenementId()));
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    // Store the id of the moyenDeTransport to be updated
    currentMoyenDeTransportId = moyenDeTransport.getId();
}

@FXML
public void moyenDeTransportUpdate() {
    try {
        Alert alert;
        int prix = 0;
        int nbre = 0;
        boolean isPrixInteger = true;
        boolean isNbreInteger = true;

        try {
            prix = Integer.parseInt(moyenDeTransport_prix.getText());
        } catch (NumberFormatException e) {
            isPrixInteger = false;
        }

        try {
            nbre = Integer.parseInt(moyenDeTransport_nbre.getText());
        } catch (NumberFormatException e) {
            isNbreInteger = false;
        }

        if (moyenDeTransport_prix.getText().isEmpty() || moyenDeTransport_type.getText().isEmpty() || moyenDeTransport_nbre.getText().isEmpty() || moyenDeTransport_evenement.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Les champs sont obligatoires");
            alert.showAndWait();
        } else if (!isPrixInteger && !isNbreInteger) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix et Nombre de moyenDeTransports sont des entiers");
            alert.showAndWait();
        } else if (!isPrixInteger) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix doit être un entier");
            alert.showAndWait();
        } else if (!isNbreInteger) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nombre de moyenDeTransports doit être un entier");
            alert.showAndWait();
        } else if (prix <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix doit être supérieur à zéro");
            alert.showAndWait();
        } else if (nbre <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nombre de moyenDeTransports doit être supérieur à zéro");
            alert.showAndWait();
        } else {
            // Get the updated values from the input fields
            String selectedEventName = moyenDeTransport_evenement.getSelectionModel().getSelectedItem();
            String type = moyenDeTransport_type.getText();

            // Get the selected event
            Evenement selectedEvent = evenementService.getEventByName(selectedEventName);

            // Check if a moyenDeTransport with the same event already exists, excluding the current event of the modifying moyenDeTransport
            Moyen_De_Transport existingMoyenDeTransport = moyenDeTransportService.testMoyenDeTransportByEventId(selectedEvent.getId(), currentMoyenDeTransportId);

            // Update the moyenDeTransport
            moyenDeTransportService.updateMoyenDeTransport(currentMoyenDeTransportId, selectedEvent.getId(), prix, type, nbre);

            // Refresh the table view
       List<Moyen_De_Transport> moyenDeTransports = moyenDeTransportService.getAllMoyenDeTransports();
showListMoyenDeTransport(moyenDeTransports);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., show an error message to the user
    }
}

    public void deleteMoyenDeTransport() {
        // Check if a moyenDeTransport is selected
        if (selectedMoyenDeTransport == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a moyenDeTransport in the table.");
            alert.showAndWait();
            return;
        }

        // Show a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Moyen De Transport");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this moyenDeTransport?");
        Optional<ButtonType> result = alert.showAndWait();

        // If the user confirmed the deletion
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Delete the moyenDeTransport
                moyenDeTransportService.deleteMoyenDeTransport(selectedMoyenDeTransport.getId());

                // Refresh the table view
           List<Moyen_De_Transport> moyenDeTransports = moyenDeTransportService.getAllMoyenDeTransports();
showListMoyenDeTransport(moyenDeTransports);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clear() {
        moyenDeTransport_evenement.getSelectionModel().clearSelection();
        moyenDeTransport_prix.clear();
        moyenDeTransport_type.clear();
        moyenDeTransport_nbre.clear();
    }

}