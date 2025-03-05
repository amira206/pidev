package GestionEvenement3a16.Controller;

import GestionEvenement3a16.Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DashController {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private ScrollPane userListScrollPane = new ScrollPane();
    @FXML
    private Button exportButton=new Button();
    public AnchorPane DataView = new AnchorPane(), anchor=new AnchorPane(), EditProfileField, EditField = new AnchorPane();

    private UserService userService;
    @FXML
    public void loadRec() throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement3a16/ReclamationView.fxml"));
            Pane content = fxmlLoader.load(); // Load as Pane
            searchField.setVisible(false);
            userListScrollPane.setVisible(false);
            exportButton.setVisible(false);
            anchor.getChildren().setAll(content);
            anchor.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void loadRep() throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionEvenement3a16/ReponseView.fxml"));
            Pane content = fxmlLoader.load(); // Load as Pane
            searchField.setVisible(false);
            userListScrollPane.setVisible(false);
            exportButton.setVisible(false);
            anchor.getChildren().setAll(content);
            anchor.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/*
    @FXML
    public void loadEvenementLayout() {
        try {
            AnchorPane evenementLayout = FXMLLoader.load(getClass().getResource("/SportHub/Evenement.fxml"));
            anchor.getChildren().setAll(evenementLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

/*
    @FXML
public void loadTicketLayout() {
    try {
        AnchorPane ticketLayout = FXMLLoader.load(getClass().getResource("/SportHub/Ticket.fxml"));
        anchor.getChildren().setAll(ticketLayout);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
*/

    @FXML
    public void loadEvenementCards() {
        try {
            AnchorPane evenementLayout = FXMLLoader.load(getClass().getResource("/GestionEvenement3a16/EvenementBack.fxml"));
            anchor.getChildren().setAll(evenementLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadMoyenDeTransportCards() {
        try {
            AnchorPane evenementLayout = FXMLLoader.load(getClass().getResource("/GestionEvenement3a16/MoyenDeTransportBack.fxml"));
            anchor.getChildren().setAll(evenementLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}