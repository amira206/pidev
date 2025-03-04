package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Reduction;
import services.ReductionService;

public class ListReduc {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> ValableL;

    @FXML
    private TextField recherche;

    @FXML
    private TableColumn<?, ?> reductionL;

    @FXML
    private TableColumn<?, ?> statutL;

    @FXML
    private TableView<Reduction> tableView;

    @FXML
    private TableColumn<?, ?> userIdL;

    private ReductionService reductionService;

    // Méthode d'initialisation du service
    public ListReduc() {
        reductionService = new ReductionService();  // Assurez-vous d'initialiser votre service
    }

    @FXML
    void actualiser(ActionEvent event) {
        loadReductions();  // Recharger les réductions après une action
    }

    @FXML
    void modifierRedu(ActionEvent event) {
        Reduction reduction = tableView.getSelectionModel().getSelectedItem();
        if (reduction != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReduc.fxml"));
                Parent parent = loader.load();

                // Récupérer le contrôleur de la fenêtre modifiée
                ModifierReduc mr = loader.getController();

                // Passer l'instance du service déjà existante dans ListReduc à ModifierReduc
                mr.setReductionService(this.reductionService);  // Injecter le service existant

                // Initialiser les champs de la réduction dans la fenêtre modifiée
                mr.setTextFields(reduction);

                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de chargement", "Une erreur est survenue lors de l'ouverture de la fenêtre de modification.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", null, "Veuillez sélectionner une réduction à modifier.");
        }
    }



    @FXML
    void supprimerRedu(ActionEvent event) {
        Reduction selectedReduction = tableView.getSelectionModel().getSelectedItem();

        if (selectedReduction != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer la réduction");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réduction ?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean success = reductionService.supprimerReduction(selectedReduction.getUserId());

                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction supprimée", "La réduction a été supprimée avec succès.");
                        loadReductions();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de suppression", "Une erreur est survenue lors de la suppression de la réduction.");
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucune réduction sélectionnée", "Veuillez sélectionner une réduction à supprimer.");
        }
    }

    private void loadReductions() {
        List<Reduction> reductions = reductionService.getAllReductions(); // Récupérer les réductions du service
        ObservableList<Reduction> observableReductions = FXCollections.observableArrayList(reductions);
        tableView.setItems(observableReductions);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        userIdL.setCellValueFactory(new PropertyValueFactory<>("userId"));
        reductionL.setCellValueFactory(new PropertyValueFactory<>("reductionPercentage"));  // Remplacez ici
        ValableL.setCellValueFactory(new PropertyValueFactory<>("validUntil"));
        statutL.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadReductions();
    }

    private void filterTable(String query) {
        ObservableList<Reduction> filteredList = FXCollections.observableArrayList();

        for (Reduction reduction : tableView.getItems()) {
            boolean matches =
                    String.valueOf(reduction.getUserId()).contains(query) ||
                            String.valueOf(reduction.getReductionPercentage()).contains(query);

            if (reduction.getValidUntil() != null && reduction.getValidUntil().toString().contains(query)) {
                matches = true;
            }

            if (reduction.getStatus().toLowerCase().contains(query.toLowerCase())) {
                matches = true;
            }

            if (matches) {
                filteredList.add(reduction); // Ajouter la réduction à la liste filtrée
            }
        }

        tableView.setItems(filteredList); // Mettez à jour la table avec les éléments filtrés
    }
    @FXML
    void handlekey() {
        String query = recherche.getText().toLowerCase();
        filterTable(query);
    }

}
