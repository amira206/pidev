package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane ;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import java.io.IOException;


public class DashboardFront_Controller {

    @FXML
    private AnchorPane  Gestions_scene;

    @FXML
    private Button gestion_couvoiturage_butt;

    @FXML
    private Button gestion_evennement_butt;

    @FXML
    private Button gestion_livraison_butt;

    @FXML
    private Button gestion_reclamation_butt;

    @FXML
    private Button gestion_reservation_butt;

    @FXML
    private Button gestion_utilisateur_butt;

    @FXML
    private void handleGestionUtilisateur(ActionEvent event) {
        // Load the GestionUtilisateur.fxml content into the Gestions_scene AnchorPane 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionUtilisateur.fxml"));
            AnchorPane  gestionUtilisateurAnchorPane  = loader.load();
            Gestions_scene.getChildren().setAll(gestionUtilisateurAnchorPane );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGestionLivraison(ActionEvent event) {
        // Load the GestionLivraison.fxml content into the Gestions_scene pane
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionLivraison.fxml"));
            AnchorPane gestionLivraisonPane = loader.load();
            Gestions_scene.getChildren().setAll(gestionLivraisonPane);

            // Center the loaded AnchorPane inside the Gestions_scene
            AnchorPane.setTopAnchor(gestionLivraisonPane, 0.0);
            AnchorPane.setLeftAnchor(gestionLivraisonPane, 0.0);
            AnchorPane.setRightAnchor(gestionLivraisonPane, 0.0);
            AnchorPane.setBottomAnchor(gestionLivraisonPane, 0.0);

            // Bind the width and height of the loaded view to the Gestions_scene
            gestionLivraisonPane.prefWidthProperty().bind(Gestions_scene.widthProperty());
            gestionLivraisonPane.prefHeightProperty().bind(Gestions_scene.heightProperty());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGestionEvenement(ActionEvent event) {
        // Load the GestionEvenement.fxml content into the Gestions_scene pane
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement.fxml"));
            AnchorPane gestionEvenementPane = loader.load();
            Gestions_scene.getChildren().setAll(gestionEvenementPane);

            // Center the loaded AnchorPane inside the Gestions_scene
            AnchorPane.setTopAnchor(gestionEvenementPane, 0.0);
            AnchorPane.setLeftAnchor(gestionEvenementPane, 0.0);
            AnchorPane.setRightAnchor(gestionEvenementPane, 0.0);
            AnchorPane.setBottomAnchor(gestionEvenementPane, 0.0);

            // Bind the width and height of the loaded view to the Gestions_scene
            gestionEvenementPane.prefWidthProperty().bind(Gestions_scene.widthProperty());
            gestionEvenementPane.prefHeightProperty().bind(Gestions_scene.heightProperty());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
    private void handleGestionCouvoiturage(ActionEvent event) {

    }

    @FXML
    private void handleGestionReservation(ActionEvent event) {

    }

  @FXML
    private void handleGestionReclamation(ActionEvent event) {

    }

  /*  private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Gestions_scene.getChildren().clear();  // Clear current content
            Gestions_scene.getChildren().add(root);  // Load new content into Gestions_scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    public void initialize() {
        try {
            // Load the GestionUtilisateur.fxml into the Gestions_scene AnchorPane 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/GestionUtilisateur.fxml"));
            AnchorPane  gestionUtilisateurAnchorPane  = loader.load();  // Load the FXML

            // Add the loaded content into the Gestions_scene AnchorPane 
            Gestions_scene.getChildren().setAll(gestionUtilisateurAnchorPane );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
