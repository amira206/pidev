package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Role;
import models.User;
import services.UserService;
import utils.MyDabase;

public class Admin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> emailU;

    @FXML
    private TableColumn<?, ?> idU;

    @FXML
    private TableColumn<?, ?> userNameU;

    @FXML
    private TableColumn<?, ?> roleU;

    @FXML
    private TextField rechercherU;

    @FXML
    private TableView<User> tableViewU;

    @FXML
    void ModifierUser(ActionEvent event) {
        User user = tableViewU.getSelectionModel().getSelectedItem();
        if (user != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifierUser.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            ModifierUser mu = loader.getController();
            mu.setTextFields(user);
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } else {
            // Affichage d'une alerte si aucun utilisateur n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void SupprimerUser(ActionEvent event) {
        UserService userService = new UserService();
        User user = tableViewU.getSelectionModel().getSelectedItem();
        if (user != null) {
            int userId = user.getId();
            try {
                userService.delete(userId);
                refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("GOMOBILITE :: Success Message");
                alert.setHeaderText(null);
                alert.setContentText("User has been deleted.");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("GOMOBILITE :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while deleting the user.");
                alert.showAndWait();
                System.out.println(e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }


    @FXML
    void actualiser(ActionEvent event) {
        refresh();
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }



    @FXML
    void initialize() {
        searchRec();
    }


    public ObservableList<User> getlist() {
        Connection cnx = MyDabase.getInstance().getConnection();
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            if (cnx == null) {
                System.out.println("La connexion à la base de données a échoué.");
                return list;
            }

            String query = "SELECT * FROM user";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                while (rs.next()) {
                    String roleString = rs.getString("role");
                    Role role = Role.valueOf(roleString.toUpperCase());  // Assurez-vous que le rôle est bien converti
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("userName"),
                            rs.getString("email"),
                            rs.getString("password"),
                            role
                    );
                    list.add(user);
                }
            }

            System.out.println("Nombre d'utilisateurs récupérés : " + list.size());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return list;
    }



    public void refresh() {
        ObservableList<User> list = getlist();
        idU.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameU.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailU.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleU.setCellValueFactory(new PropertyValueFactory<>("role"));
        tableViewU.setItems(list);  // Mettre à jour les éléments dans la table
    }

    private void searchRec() {
        idU.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameU.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailU.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleU.setCellValueFactory(new PropertyValueFactory<>("role"));

        ObservableList<User> list = getlist();
        tableViewU.setItems(list);

        FilteredList<User> filteredData = new FilteredList<>(list, b -> true);
        rechercherU.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rec -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (rec.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (rec.getUserName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewU.comparatorProperty());
        tableViewU.setItems(sortedData);
    }



}
