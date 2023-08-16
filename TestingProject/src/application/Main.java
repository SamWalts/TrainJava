package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
        primaryStage.setTitle("Testing Things");
        //Scene scene = new Scene(root, Double.MAX_VALUE, Double.MAX_VALUE);
         
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);

        // Show the stage.
        primaryStage.show();
    }
    


    public static void main(String[] args) {
        launch(args);
    }
}