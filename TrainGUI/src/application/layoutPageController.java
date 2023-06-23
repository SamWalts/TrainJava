package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;


public class layoutPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private TableManager tableManager;
    private BlobManager blobManager;
    
    private Switch switchObject;
    private Station stationObject;

    @FXML
    private Button generateButtonsButton;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Rectangle square;

    @FXML
    private ImageView trainLayoutImage;

    @FXML
    private Button undoButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tableManager = TableManager.getInstance();
        blobManager = tableManager.createBlobManager(); 
        switchObject = new Switch(rectangle);
        stationObject = new Station(square);
        

        // TODO: Get Blob image of layout and have it placed in the window
        // TODO: Switch/station object able to be tilted and resized
        // TODO: Have switch_number or station_number of objects user can manipulate to place on the layout image
        // If not, have a counter for the number that will go down for every object dragged.
        // Must check or will try to save x & y coordinates and will throw exception and crash
        // TODO: What information will need to be created so that in the next Window, we can make buttons that will appear in the correct
        // place.
        try {
			pictureBlobRetrieval();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @FXML
    private void handleDragDetection(MouseEvent event) {
        Node source = (Node) event.getSource();
        Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putAll(content);
        dragboard.setContent(content);
        event.consume();
    }
        @FXML
    private void handleDragOver(DragEvent event) {
    	
    	event.acceptTransferModes(TransferMode.ANY);
    }
        
    // Will go back a page to generateButtons.FXML
    public void generateButtonsButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GenerateButtons.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void handleDrop(DragEvent event) {
    	//How to make that image show up
    }
    //Implement from the github link shown below
    public void undoButton(ActionEvent event) throws IOException {
        // TODO: Code to remove the last object placed
    	// https://github.com/FXMisc/UndoFX#demo
    }

    public void pictureBlobRetrieval() throws SQLException {
    	// This sets the image retrieved from the "picture_table" as the image.
        byte[] imageData = blobManager.retrieveBlob("picture_table", "file_data", 1);

        // Check if the image data is not null
        if (imageData != null) {
            // Create an Image object from the image data
            Image image = new Image(new ByteArrayInputStream(imageData));

            // Set the image in the ImageView
            trainLayoutImage.setImage(image);
        }
    }

}