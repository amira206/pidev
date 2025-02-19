package org.example.pitest.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.pitest.Model.Offre;
import org.example.pitest.Services.OffreService;
import org.example.pitest.Services.ParcoursService;

import java.util.List;
import java.util.Optional;

public class OffreController {

    @FXML private TableView<Offre> tableau;
    @FXML private TableColumn<Offre, Integer> idtv;
    @FXML private TableColumn<Offre, Integer> nbplacestv;
    @FXML private TableColumn<Offre, String> usertv;
    @FXML private TableColumn<Offre, Double> prixtv;
    @FXML private TableColumn<Offre, String> parcourstv;
    @FXML private TableColumn<Offre, String> datetv;
    @FXML private TableColumn<Offre, String> bagagetv;
    @FXML private TableColumn<Offre, Button> deleteColumn;
    @FXML private TableColumn<Offre, Button> editColumn;
    @FXML private TextField filterField;

    private final OffreService offreService = OffreService.getInstance();
    private final ParcoursService parcoursService = ParcoursService.getInstance();
    private FilteredList<Offre> filteredData;

    @FXML
    public void initialize() {
        // Initialize columns
        idtv.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nbplacestv.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNbPlaces()));
        usertv.setCellValueFactory(cellData -> new SimpleStringProperty());
        prixtv.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));
        parcourstv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParcours().getName()));
        datetv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        bagagetv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isBagage() ? "Oui" : "Non"));

        // Setup delete column
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.getStyleClass().add("button-danger");
                deleteButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    handleDeleteOffre(offre);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        // Setup edit column
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.getStyleClass().add("button-primary");
                editButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    handleEditOffre(offre);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        // Initialize filter
        loadData();
        setupFilter();
    }

    private void setupFilter() {
        filteredData = new FilteredList<>(tableau.getItems(), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(offre -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(offre.getId()).contains(lowerCaseFilter)
                        || String.valueOf(offre.getNbPlaces()).contains(lowerCaseFilter)
                        || offre.getParcours().getName().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(offre.getPrix()).contains(lowerCaseFilter);
            });
        });
        tableau.setItems(filteredData);
    }

    @FXML
    private void handleAddOffre() {
        AddEditOffreDialog dialog = new AddEditOffreDialog(null, parcoursService);
        Optional<Offre> result = dialog.showAndWait();

        result.ifPresent(offre -> {
            offreService.add(offre);
            loadData();
        });
    }

    private void handleEditOffre(Offre offre) {
        AddEditOffreDialog dialog = new AddEditOffreDialog(offre, parcoursService);
        Optional<Offre> result = dialog.showAndWait();

        result.ifPresent(updatedOffre -> {
            offreService.update(updatedOffre, offre.getId());
            loadData();
        });
    }

    private void handleDeleteOffre(Offre offre) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirmer la suppression");
        confirmDialog.setHeaderText("Êtes-vous sûr de vouloir supprimer cette offre ?");
        confirmDialog.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            offreService.delete(offre.getId());
            loadData();
        }
    }

    private void loadData() {
        List<Offre> offres = offreService.findAll();
        ObservableList<Offre> observableList = FXCollections.observableArrayList(offres);
        tableau.setItems(observableList);
    }

    @FXML
    private void ExporterExcel() {
        // TODO: Implement Excel export functionality
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Excel");
        alert.setHeaderText(null);
        alert.setContentText("La fonctionnalité d'export Excel sera bientôt disponible!");
        alert.showAndWait();
    }
}