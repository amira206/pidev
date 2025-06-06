
package GestionEvenement3a16.Controller.ReclamationController;

import GestionEvenement3a16.Entity.Reponse;
import GestionEvenement3a16.Services.ReponseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditReponse {
    @FXML
    private TextArea reponseedit;
    @FXML
    private Label repEditError;


    private AnchorPane editreponse; // Declare detailanchpane here
    private AnchorPane detailanchpane; // Declare detailanchpane here
    private Reponse rep;
    private ReponseService s = new ReponseService();
    private ReponseController reponseController;

    public void setReponseController(ReponseController reponseController) {
        this.reponseController = reponseController;
    }


    public void setDetailReponseAnchorPane(AnchorPane editreponse) {
        this.editreponse = editreponse;
    }

    public void setDetailAnchorPane(AnchorPane detailanchpane) {
        this.detailanchpane = detailanchpane;
    }

    public void setData(Reponse reponse) {
        this.rep = reponse;
        reponseedit.setText(reponse.getReponse());
    }

    @FXML
    public void handleEditButtonClick() throws IOException {
        if (s == null || rep == null) {
            System.out.println(rep);
            System.err.println("Service or Reponse is null");
            return;
        }

        // Check if the reponseedit field is not empty
        if (reponseedit.getText().isEmpty()) {
            repEditError.setText("Response cannot be empty."); // Changed error message
            return;
        } else {
            repEditError.setText(""); // Clear the error message
        }

        rep.setReponse(reponseedit.getText());
        s.modifierReponse(rep);

        Alert a = new Alert(Alert.AlertType.INFORMATION, "votre reponse est modifier ");
        a.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEvenement3a16/DetailReponse.fxml"));
        Parent detailReeponseView = loader.load();
        DetailReponse detailReponse = loader.getController();
        detailReponse.setData(rep); // Passer la réclamation modifiée

        // Remplacer le contenu de detailanchpane avec la vue de détail actualisée
        detailanchpane.getChildren().setAll(detailReeponseView);
    }
}
