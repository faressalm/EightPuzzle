import java.io.IOException;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
//         Button btn = new Button();
//         btn.setText("Say 'Hello World'");
//         btn.setOnAction(new EventHandler<ActionEvent>() {
 
//             @Override
//             public void handle(ActionEvent event) {
//                 System.out.println("Hello World!");
//             }
//         });
        
//         StackPane root = new StackPane();
//         root.getChildren().add(btn);
  
//   /*
  Parent root;
try {
    root = FXMLLoader.load(getClass().getResource("MainSceneController.fxml"));
    Scene scene = new Scene(root);
     
  primaryStage.setTitle("Hello World!");
  primaryStage.setScene(scene);
  primaryStage.show();
  
} catch (IOException e) {
   System.out.println("hereeeeeeeeeeeeeeeee");
    e.printStackTrace();
}
  
}
  //Scene scene = new Scene(root, 300, 250);
 
 
 public static void main(String[] args) {
        launch(args);
    }
}