package Controllers;
import java.util.Comparator;
import piDev.GestionLIvariason.Livraison.entities.DistanceCalculator;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.List;

public class Afficher_livrai_Controller {

    @FXML
    private Button Delete_livrai_butt;
    @FXML
    private Button Return_butt;
    @FXML
    private TextField searchField;

    private ObservableList<livraison> fullList;

    @FXML
    private Button Sort_butt;

    // Make sure the column types match what you are binding to the TableView
    @FXML
    private TableView<livraison> livraison_table;

    @FXML
    private TableColumn<livraison, String> start_location_column;

    @FXML
    private TableColumn<livraison, String> delivery_location_column;

    @FXML
    private TableColumn<livraison, Boolean> is_delivered_column;

    @FXML
    void initialize() throws SQLException {
        ServiceLivraison ps = new ServiceLivraison();
        fullList = FXCollections.observableArrayList(ps.findAll()); // Ensure fullList is initialized

        livraison_table.setItems(fullList);
        start_location_column.setCellValueFactory(new PropertyValueFactory<>("start_location"));
        delivery_location_column.setCellValueFactory(new PropertyValueFactory<>("delivery_location"));
        is_delivered_column.setCellValueFactory(new PropertyValueFactory<>("is_delivered"));

        // Set delete button action
        if (Delete_livrai_butt != null) {
            Delete_livrai_butt.setOnAction(event -> deleteSelectedRow());
        } else {
            System.out.println("Delete_livrai_butt is NULL! Check FXML fx:id.");
        }

        if (Sort_butt != null) {
            Sort_butt.setOnAction(event -> sortTable());
        }

        livraison_table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Single-click detection
                handleRowClick();
            }
        });

        setupSearch(); // Ensure search functionality is initialized
    }


    @FXML
    private void deleteSelectedRow() {
        // Get the selected row
        livraison selectedLivraison = livraison_table.getSelectionModel().getSelectedItem();

        if (selectedLivraison != null) {
            // Confirmation alert
            javafx.scene.control.Alert confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to delete this delivery?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    // Delete from database
                    ServiceLivraison service = new ServiceLivraison();
                    service.supprimer(selectedLivraison);

                    // Remove from TableView
                    livraison_table.getItems().remove(selectedLivraison);

                    // Show success notification
                    showDeletionNotification(selectedLivraison);
                }
            });
        } else {
            // Show warning if no row is selected
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row to delete.");
            alert.showAndWait();
        }
    }

    private void showDeletionNotification(livraison deletedLivraison) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Deletion Successful");
        alert.setHeaderText(null);
        alert.setContentText("Deleted Livraison:\nStart: " + deletedLivraison.getStart_location() +
                "\nDestination: " + deletedLivraison.getDelivery_location());
        alert.showAndWait();
    }

    @FXML
    private void returnToAjout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout_livrai.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) Return_butt.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sortTable() {
        try {
            // Fetch sorted data from the database
            List<livraison> sortedResults = new ServiceLivraison().sortLivraisonsByStartLocation();

            // Set sorted results in the table
            livraison_table.setItems(FXCollections.observableArrayList(sortedResults));
        } catch (SQLException e) {
            System.out.println("Error sorting livraisons: " + e.getMessage());
        }
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                try {
                    // If search is empty, reload all data
                    fullList.setAll(new ServiceLivraison().findAll());
                    livraison_table.setItems(fullList);
                } catch (SQLException e) {
                    System.out.println("Error loading livraisons: " + e.getMessage());
                }
            } else {
                try {
                    // Fetch filtered results from the database
                    List<livraison> searchResults = new ServiceLivraison().searchLivraisons(newValue.trim());
                    livraison_table.setItems(FXCollections.observableArrayList(searchResults));
                } catch (SQLException e) {
                    System.out.println("Error searching livraisons: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    private void handleRowClick() {
        livraison selectedLivraison = livraison_table.getSelectionModel().getSelectedItem();

        if (selectedLivraison != null) {
            try {
                String city1 = selectedLivraison.getStart_location();
                String city2 = selectedLivraison.getDelivery_location();
                String apiKey = "e2c53ae1de5946e78e463a924771741d"; // Replace with your actual API key

                // Calculate the distance
                int distance = DistanceCalculator.getDistanceBetweenCities(city1, city2, apiKey);

                // Show the distance in an alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Distance Information");
                alert.setHeaderText(null);
                alert.setContentText("The distance between " + city1 + " and " + city2 + " is: " + distance + " km");
                alert.showAndWait();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

