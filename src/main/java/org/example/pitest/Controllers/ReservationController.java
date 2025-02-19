package org.example.pitest.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.pitest.Model.Reservation;
import org.example.pitest.Services.ReservationService;

import java.util.List;

public class ReservationController {

    @FXML
    private TableView<Reservation> tableau;
    @FXML
    private TableColumn<Reservation, Integer> idColumn;
    @FXML
    private TableColumn<Reservation, String> userColumn;
    @FXML
    private TableColumn<Reservation, String> offreColumn;
    @FXML
    private TableColumn<Reservation, Double> priceColumn;
    @FXML
    private TableColumn<Reservation, Void> deleteColumn;
    @FXML
    private TableColumn<Reservation, Void> editColumn;
    @FXML
    private TextField filterField;

    private final ReservationService reservationService = ReservationService.getInstance();
    private ObservableList<Reservation> reservationList;

    @FXML
    public void initialize() {
        setupTable();
        loadReservations();
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.valueOf(cellData.getValue().getUser().getId())
                )
        );
        offreColumn.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.valueOf(cellData.getValue().getOffre().getId())
                )
        );
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addDeleteButtonToTable();
        addEditButtonToTable();
    }

    private void loadReservations() {
        List<Reservation> reservations = reservationService.findAll();
        reservationList = FXCollections.observableArrayList(reservations);
        tableau.setItems(reservationList);
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    reservationService.delete(reservation.getId());
                    loadReservations(); // Rafraîchir la table
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void addEditButtonToTable() {
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    // Implémenter la logique de modification
                    System.out.println("Modifier: " + reservation.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    @FXML
    private void exporterExcel() {
        System.out.println("Exportation Excel en cours...");
    }

    @FXML
    private void ajouterReservation() {
        System.out.println("Ajout d'une nouvelle réservation...");
    }
}
