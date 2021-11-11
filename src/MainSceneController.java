import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import classes.Algorithms;
import classes.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Button nextStip;

    @FXML
    private Label nodeExpanded;

    @FXML
    private ImageView oneBox;

    @FXML
    private Label runningTime;

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
    private Label stepsNum;

    @FXML
    private ImageView threeBox;

    @FXML
    private ImageView title;

    @FXML
    private AnchorPane toolbar;

    @FXML
    private ImageView twoBox;

    private State state = new State((Long) 13438895736L);
    private Stack<Long> path;
    private Algorithms algorithm;
    private int steps;
    Map<Button, Boolean> clickedButtons;

    public MainSceneController() {
        algorithm = new Algorithms();
    }

    @FXML
    void solver(ActionEvent event) {

        initializeState();
        setClickedColor(event);
        if (state.getCurrentState() == 13438895736L)
            return;
        String buttonType = ((Node) event.getSource()).getId();
        long startTime = System.currentTimeMillis();

        if (buttonType.compareTo("solveDFS") == 0)
            path = algorithm.DFS(state.getCurrentState());
        else if (buttonType.compareTo("solveAEculidean") == 0)
            path = algorithm.AStarEuclideanDistance(state);
        else if (buttonType.compareTo("solveAManhattan") == 0)
            path = algorithm.AStarManhattanDistance(state);
        else if (buttonType.compareTo("solveBFS") == 0)
            path = algorithm.BFS(state.getCurrentState());
        long stopTime = System.currentTimeMillis();
        if (path == null || path.isEmpty()) {
            stepsNum.setText("No Solution");
            nodeExpanded.setText(Integer.toString(0));
            runningTime.setText(Integer.toString(0));
        } else {
            steps = path.size();
            nodeExpanded.setText(Integer.toString(algorithm.getMaxDepth() == 0?steps:algorithm.getMaxDepth()));
            runningTime.setText(Integer.toString((int)(stopTime-startTime))+" ms");
            changeSteps();
        }

    }

    private void changeSteps() {

        stepsNum.setText(Integer.toString(steps));
    }

    private void initializeState() {
        String textState = inputSeq.getText();
        if (inputTextIsValid(textState)) {
            state = new State(textState);
            updateBoxes();
        }

    }

    @FXML
    void closeWindowAction(MouseEvent event) {
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    void setInitialPuzlleIfEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            initializeState();
        }

    }

    @FXML
    void nextStepInPath(ActionEvent event) {
        buildPath();
    }

    @FXML
    void changeButtonColor(MouseEvent event) {
        Node button = ((Node) event.getSource());
        button.setStyle("-fx-background-color: #E7E7E7; -fx-background-radius: 9");
    }

    @FXML
    void reChangeButtonColor(MouseEvent event) {
        Node button = ((Node) event.getSource());
        if (clickedButtons.get(button) == false)
            button.setStyle("-fx-background-color: #90EE90; -fx-background-radius: 9");
    }

    private void setClickedColor(ActionEvent event) {
        for (Map.Entry<Button, Boolean> entry : clickedButtons.entrySet()) {
            clickedButtons.put(entry.getKey(), false);
            entry.getKey().setStyle("-fx-background-color: #90EE90; -fx-background-radius: 9");
        }
        clickedButtons.put(((Button) event.getSource()), true);
        ((Button) event.getSource()).setStyle("-fx-background-color: #E7E7E7; -fx-background-radius: 9");

    }

    private void buildPath() {
        if (!path.isEmpty()) {
            steps--;
            changeSteps();

            state = new State(path.pop());
            updateBoxes();
        }
    }

    public void initializeColors() {
        clickedButtons = Stream
                .of(new Object[][] { { solveDFS, false }, { solveBFS, false }, { solveAEculidean, false },
                        { solveAManhattan, false }, })
                .collect(Collectors.toMap(data -> (Button) data[0], data -> (Boolean) data[1]));
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
}
