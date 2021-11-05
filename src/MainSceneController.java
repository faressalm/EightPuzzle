import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainSceneController {
    @FXML
    private AnchorPane body;

    @FXML
    private ImageView closeWindow;

    @FXML
    private ImageView eightBox;

    @FXML
    private ImageView fiveBox;

    @FXML
    private ImageView fourBox;

    @FXML
    private ImageView oneBox;

    @FXML
    private ImageView sevenBox;

    @FXML
    private ImageView sixBox;

    @FXML
    private ImageView spaceBox;

    @FXML
    private ImageView threeBox;

    @FXML
    private ImageView title;

    @FXML
    private AnchorPane toolbar;

    @FXML
    private ImageView twoBox;


    @FXML
    void closeWindowAction(MouseEvent  event) {
        System.out.println(((Node) event.getSource()).getId());
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }

}
