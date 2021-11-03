import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainSceneController {

    @FXML
    private Button DFS;

    @FXML
    private TextField ttTitle;

    @FXML
    void DFSSolve(ActionEvent event) {
        Stage mainWindow = (Stage) ttTitle.getScene().getWindow();
        String seq = ttTitle.getText();
        mainWindow.setTitle(seq);
    }

}
