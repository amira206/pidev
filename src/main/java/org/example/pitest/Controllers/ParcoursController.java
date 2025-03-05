package org.example.pitest.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Services.ParcoursService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

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

        tableau.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Parcours selectedParcours = tableau.getSelectionModel().getSelectedItem();
                if (selectedParcours != null) {
                    // Debug output
                    System.out.println("Selected Parcours Debug Info:");
                    System.out.println("Name: " + selectedParcours.getName());
                    System.out.println("Pickup Coordinates: " + selectedParcours.getLatPickup() + ", " + selectedParcours.getLngPickup());
                    System.out.println("Destination Coordinates: " + selectedParcours.getLatDest() + ", " + selectedParcours.getLngDest());

                    showMap(selectedParcours);
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

    private void validateCoordinates(Parcours parcours) {
        if (parcours == null) {
            throw new IllegalArgumentException("Parcours object is null");
        }

        // Validate latitude range (-90 to 90)
        if (parcours.getLatPickup() < -90 || parcours.getLatPickup() > 90 ||
                parcours.getLatDest() < -90 || parcours.getLatDest() > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
        }

        // Validate longitude range (-180 to 180)
        if (parcours.getLngPickup() < -180 || parcours.getLngPickup() > 180 ||
                parcours.getLngDest() < -180 || parcours.getLngDest() > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
        }
    }

    private String formatCoordinate(double coordinate) {
        // Use a DecimalFormat to ensure proper number formatting
        DecimalFormat df = new DecimalFormat("0.000000", DecimalFormatSymbols.getInstance(Locale.US));
        return df.format(coordinate);
    }

    private void showMap(Parcours parcours) {
        try {
            // Debug print
            System.out.println("Attempting to show map for parcours: " + parcours.getName());

            Stage mapStage = new Stage();
            mapStage.setTitle("Map View - " + parcours.getName());

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            String html = generateSimpleMapHTML(parcours);

            // Debug print the HTML
            System.out.println("Generated HTML:");
            System.out.println(html);

            webEngine.loadContent(html);
            Scene scene = new Scene(webView, 800, 600);
            mapStage.setScene(scene);
            mapStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error showing map: " + e.getMessage());
        }
    }

    private String generateSimpleMapHTML(Parcours parcours) {
        // Format coordinates with explicit locale
        DecimalFormat df = new DecimalFormat("0.000000", DecimalFormatSymbols.getInstance(Locale.US));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Parcours Map</title>");
        html.append("<meta charset='utf-8'>");
        html.append("<link rel='stylesheet' href='https://unpkg.com/leaflet@1.7.1/dist/leaflet.css'>");
        html.append("<script src='https://unpkg.com/leaflet@1.7.1/dist/leaflet.js'></script>");
        html.append("<style>");
        html.append("html, body, #map { height: 100%; width: 100%; margin: 0; padding: 0; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div id='map'></div>");
        html.append("<script>");

        // Add coordinates as JavaScript variables
        html.append("var pickupLat = ").append(df.format(parcours.getLatPickup())).append(";");
        html.append("var pickupLng = ").append(df.format(parcours.getLngPickup())).append(";");
        html.append("var destLat = ").append(df.format(parcours.getLatDest())).append(";");
        html.append("var destLng = ").append(df.format(parcours.getLngDest())).append(";");

        // Initialize map
        html.append("var map = L.map('map');");

        // Add tile layer
        html.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {");
        html.append("    maxZoom: 19,");
        html.append("    attribution: '© OpenStreetMap contributors'");
        html.append("}).addTo(map);");

        // Add markers
        html.append("var pickupMarker = L.marker([pickupLat, pickupLng])");
        html.append("    .bindPopup('Pickup: ").append(escapeJavaScript(parcours.getPickup())).append("')");
        html.append("    .addTo(map);");

        html.append("var destMarker = L.marker([destLat, destLng])");
        html.append("    .bindPopup('Destination: ").append(escapeJavaScript(parcours.getDestination())).append("')");
        html.append("    .addTo(map);");

        // Add route line
        html.append("var routeLine = L.polyline([[pickupLat, pickupLng], [destLat, destLng]], {");
        html.append("    color: 'blue',");
        html.append("    weight: 3");
        html.append("}).addTo(map);");

        // Fit bounds
        html.append("var bounds = L.latLngBounds([[pickupLat, pickupLng], [destLat, destLng]]);");
        html.append("map.fitBounds(bounds, {padding: [50, 50]});");

        html.append("</script>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private String escapeJavaScript(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    // Enhanced alert method for better error reporting
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Map Error");
        alert.setHeaderText("Error showing map");
        alert.setContentText(message);

        // Add exception details in expandable content
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setExpandableContent(new VBox(textArea));
        alert.showAndWait();
    }
    // Modify your showAlert method to be more informative

}