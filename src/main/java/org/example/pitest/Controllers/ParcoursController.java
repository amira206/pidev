package org.example.pitest.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Services.ParcoursService;

import java.io.IOException;
import java.util.List;

public class ParcoursController {

    @FXML
    private TableView<Parcours> tableau;
    @FXML
    private TableColumn<Parcours, Integer> id;
    @FXML
    private TableColumn<Parcours, String> name;
    @FXML
    private TableColumn<Parcours, String> pickup;
    @FXML
    private TableColumn<Parcours, String> destination;
    @FXML
    private TableColumn<Parcours, Double> latDest;
    @FXML
    private TableColumn<Parcours, Double> lngDest;
    @FXML
    private TableColumn<Parcours, Double> latPickup;
    @FXML
    private TableColumn<Parcours, Double> lngPickup;
    @FXML
    private TableColumn<Parcours, Integer> distance;
    @FXML
    private TableColumn<Parcours, Integer> time;
    @FXML
    private TableColumn<Parcours, Void> deleteColumn;
    @FXML
    private TableColumn<Parcours, Void> editColumn;
    @FXML
    private TextField filterField;

    private final ParcoursService parcoursService = ParcoursService.getInstance();

    @FXML
    public void initialize() {
        // Use lambda expressions instead of PropertyValueFactory
        id.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getId()).asObject());
        name.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getName()));
        pickup.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getPickup()));
        destination.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDestination()));
        latDest.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                cellData.getValue().getLatDest()).asObject());
        lngDest.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                cellData.getValue().getLngDest()).asObject());
        latPickup.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                cellData.getValue().getLatPickup()).asObject());
        lngPickup.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                cellData.getValue().getLngPickup()).asObject());
        distance.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getDistance()).asObject());
        time.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getTime()).asObject());

        // Format double values to 2 decimal places
        latDest.setCellFactory(column -> new TableCell<Parcours, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Parcours parcours = getTableRow().getItem();
                    if (parcours != null) {
                        parcoursService.delete(parcours.getId());
                        loadParcours();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Edit button column
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.setOnAction(event -> {
                    Parcours parcours = getTableRow().getItem();
                    if (parcours != null) {
                        ouvrirAjoutModification(parcours);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Setup filter listener
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterParcours(newValue);
            }
        });

        loadParcours();
    }

    private void filterParcours(String filter) {
        List<Parcours> parcoursList = parcoursService.findAll();
        ObservableList<Parcours> filteredList = FXCollections.observableArrayList();

        for (Parcours parcours : parcoursList) {
            if (parcours.getName().toLowerCase().contains(filter.toLowerCase()) ||
                    parcours.getPickup().toLowerCase().contains(filter.toLowerCase()) ||
                    parcours.getDestination().toLowerCase().contains(filter.toLowerCase())) {
                filteredList.add(parcours);
            }
        }

        tableau.setItems(filteredList);
    }

    private void loadParcours() {
        List<Parcours> parcoursList = parcoursService.findAll();
        System.out.println("Parcours chargés : " + parcoursList);

        // Add debug output
        if (parcoursList.isEmpty()) {
            System.out.println("Warning: No parcours found!");
            tableau.setPlaceholder(new Label("Aucun parcours trouvé"));
        } else {
            for (Parcours p : parcoursList) {
                System.out.println("Debug - Parcours: " +
                        "id=" + p.getId() +
                        ", name=" + p.getName() +
                        ", pickup=" + p.getPickup() +
                        ", destination=" + p.getDestination());
            }
        }

        ObservableList<Parcours> observableList = FXCollections.observableArrayList(parcoursList);
        tableau.setItems(observableList);
        tableau.refresh();
    }

    @FXML
    private void ouvrirAjoutModification() {
        ouvrirAjoutModification(null);
    }

    private void ouvrirAjoutModification(Parcours parcours) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pitest/AjoutModificationParcours.fxml"));
            Parent root = loader.load();

            AjoutModificationParcoursController controller = loader.getController();
            if (parcours != null) {
                controller.initData(parcours);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadParcours();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture du formulaire: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}