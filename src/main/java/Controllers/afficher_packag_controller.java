package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import piDev.GestionLIvariason.Livraison.entities.packag;
import piDev.GestionLIvariason.Livraison.services.ServicePackag;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class afficher_packag_controller {

    @FXML
    private TableColumn<packag, String> description_coloumn_affich;

    @FXML
    private TableView<packag> table_packag;

    @FXML
    private TableColumn<packag, Integer> weight_coloumn;

    @FXML // ✅ Add this annotation so JavaFX automatically calls it
    public void initialize() throws SQLException {
        // Step 1: Get package data from the database
        ServicePackag ps = new ServicePackag();
        ObservableList<packag> obs = FXCollections.observableArrayList(ps.findAll());

        // Step 2: Set table data
        table_packag.setItems(obs);

        // Step 3: Bind table columns to object attributes
        description_coloumn_affich.setCellValueFactory(new PropertyValueFactory<>("description_packag"));
        weight_coloumn.setCellValueFactory(new PropertyValueFactory<>("weight_packag"));
    }



    public void back_to_ajout(javafx.event.ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout_livrai.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();} catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
