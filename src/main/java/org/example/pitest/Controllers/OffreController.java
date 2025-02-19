package org.example.pitest.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.pitest.Model.Offre;
import org.example.pitest.Services.OffreService;

import java.util.List;

public class OffreController {

    @FXML
    private TableView<Offre> tableau;
    @FXML
    private TableColumn<Offre, Integer> idtv;
    @FXML
    private TableColumn<Offre, Integer> nbplacestv;
    @FXML
    private TableColumn<Offre, String> usertv;
    @FXML
    private TableColumn<Offre, Double> prixtv;
    @FXML
    private TableColumn<Offre, String> parcourstv;
    @FXML
    private TableColumn<Offre, String> datetv;
    @FXML
    private TableColumn<Offre, String> bagagetv;
    @FXML
    private TableColumn<Offre, Button> deleteColumn;
    @FXML
    private TableColumn<Offre, Button> editColumn;
    @FXML
    private TextField filterField;

    private final OffreService offreService = OffreService.getInstance();

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        idtv.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nbplacestv.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNbPlaces()));
        usertv.setCellValueFactory(cellData -> new SimpleStringProperty()); // User a un getNom()
        prixtv.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));
        parcourstv.setCellValueFactory(cellData -> new SimpleStringProperty()); // Parcours a un getNom()
        datetv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        bagagetv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isBagage() ? "Oui" : "Non"));


        // Ajouter les boutons Modifier et Supprimer
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    offreService.delete(offre.getId());
                    loadData(); // Rafraîchir la table après suppression
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    System.out.println("Modifier l'offre : " + offre);
                    // Ajouter ici la logique d'édition (ex. ouvrir un formulaire)
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Charger les données
        loadData();
    }

    private void loadData() {
        List<Offre> offres = offreService.findAll();
        ObservableList<Offre> observableList = FXCollections.observableArrayList(offres);
        tableau.setItems(observableList);
    }

    @FXML
    private void ExporterExcel() {
        System.out.println("Exporter vers Excel... (fonctionnalité à implémenter)");
    }
}
