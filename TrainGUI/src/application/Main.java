package application;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	 @SuppressWarnings("unused")
	private TableManager tableManager;
	 
    @Override
    public void start(Stage stage) {
        try {
        	String fileName = "Train.db";
            // Call the static method from DBUtils
            DBUtils.createNewDatabase(fileName);
            
            tableManager = TableManager.getInstance();
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Background image from https://www.vecteezy.com/vector-art/7162557-cartoon-flat-panorama-of-spring-summer-beautiful-nature-green-grasslands-meadow-with-mountains-on-horizon-background-summer-mountain-landscape-dawn-over-the-valley-vector-illustration
// Accessed 6/6/2023, Author: Татьяна Павлюк