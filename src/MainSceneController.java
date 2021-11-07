import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import classes.Algorithms;
import classes.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private TextField inputSeq;

    @FXML
    private ImageView oneBox;

    @FXML
    private ImageView sevenBox;

    @FXML
    private ImageView sixBox;

    @FXML
    private Button solveAEculidean;

    @FXML
    private Button solveAManhattan;

    @FXML
    private Button solveBFS;

    @FXML
    private Button solveDFS;

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

    private State state = new State((long) 13438895736L);
    private Stack<State> path;
    private Algorithms algorithm;

    public MainSceneController() {
        algorithm = new Algorithms();
    }

    @FXML
    void evaluateDFS(ActionEvent event) {
        initializeState();
        // path =algorithm.DFS(state);
        path = new Stack<State>();
        path.push(new State("564123078"));
        path.push(new State("564023178"));
        path.push(new State("564203178"));
        path.push(new State("564230178"));
        path.push(state);// 560234178

        buildPath();
    }

    @FXML
    void evaluateBFS(ActionEvent event) {
        initializeState();
        path = algorithm.BFS(state);
    }

    @FXML
    void evaluateAEuclidean(ActionEvent event) {
        initializeState();
        path = algorithm.AStarEuclideanDistance(state);
    }

    @FXML
    void evaluateAManhattan(ActionEvent event) {
        initializeState();
        path = algorithm.AStarManhattanDistance(state);
    }

    @FXML
    void closeWindowAction(MouseEvent event) {
        System.out.println(((Node) event.getSource()).getId());
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    void setInitialPuzlleIfEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            initializeState();
        }

    }

    private void initializeState() {
        String textState = inputSeq.getText();
        if (inputTextIsValid(textState)) {
            state = new State(textState);
            updateBoxes();
        }
    }

    private void updateBoxes() {
        int[][] arr = state.formatToTwoD();
        spaceBox.setImage(getNewImageByNumber(arr[0][0]));
        oneBox.setImage(getNewImageByNumber(arr[0][1]));
        twoBox.setImage(getNewImageByNumber(arr[0][2]));
        threeBox.setImage(getNewImageByNumber(arr[1][0]));
        fourBox.setImage(getNewImageByNumber(arr[1][1]));
        fiveBox.setImage(getNewImageByNumber(arr[1][2]));
        sixBox.setImage(getNewImageByNumber(arr[2][0]));
        sevenBox.setImage(getNewImageByNumber(arr[2][1]));
        eightBox.setImage(getNewImageByNumber(arr[2][2]));
    }

    private Image getNewImageByNumber(int number) {
        return new Image(getClass().getResourceAsStream("/assets/" + String.valueOf(number) + ".png"));
    }

    private boolean inputTextIsValid(String textState) {
        return textState != null && textState.length() == 9 && isNumeric(textState) && !textState.contains("9")
                && checkduplication(textState);
    }

    private boolean checkduplication(String textState) {
        Set<Character> duplicates = new HashSet<Character>();
        for (int i = 0; i < textState.length(); i++) {
            if (!duplicates.add(textState.charAt(i)))
                return false;
        }
        return true;
    }

    private boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void buildPath() {
        int pathlenght = path.size();
        for (int i = 0; i < pathlenght;i++) {
            state = path.pop();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(state.setStateToString());
            updateBoxes();
        }
    }
}
