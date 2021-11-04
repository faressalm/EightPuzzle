import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainSceneController {

    @FXML
    private AnchorPane body;

    @FXML
    private Button closeWindow;

    @FXML
    private AnchorPane toolbar;

    @FXML
    void closeWindowAction(ActionEvent event) {
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }

}
