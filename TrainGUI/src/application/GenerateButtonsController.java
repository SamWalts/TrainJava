package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GenerateButtonsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private TableManager tableManager;
    private BlobManager blobManager;

    @FXML
    private Spinner<Integer> switchSpinner;

    @FXML
    private Spinner<Integer> stationSpinner;

    @FXML
    private Label switchLabel;

    @FXML
    private Label stationLabel;

    @FXML
    private Button submitSwitches;

    @FXML
    private Button submitStations;

    @FXML
    private Button fileChooser;

    @FXML
    private Label imagePath;

    int switchValue;
    int stationValue;
    String imagePathName;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final int initialValue = 1;
        SpinnerValueFactory<Integer> switchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15,
                initialValue);
        SpinnerValueFactory<Integer> stationValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15,
                initialValue);

        switchSpinner.setValueFactory(switchValueFactory);
        stationSpinner.setValueFactory(stationValueFactory);

        switchValue = switchSpinner.getValue();
        stationValue = stationSpinner.getValue();

        switchSpinner.valueProperty().addListener((observable, oldValue, newValue) -> updateSwitchLabel(newValue));
        stationSpinner.valueProperty().addListener((observable, oldValue, newValue) -> updateStationLabel(newValue));

        updateSwitchLabel(initialValue);
        updateStationLabel(initialValue);

         tableManager = TableManager.getInstance();
         blobManager = tableManager.createBlobManager();   
         }

    private void updateSwitchLabel(int newValue) {
        switchValue = newValue;
        switchLabel.setText(Integer.toString(switchValue));
    }

    public void submitSwitches(ActionEvent e) {
        int switchValue = switchSpinner.getValue();
        tableManager.createTable(switchValue, "Switch");
    }

    private void updateStationLabel(int newValue) {
        stationValue = newValue;
        stationLabel.setText(Integer.toString(stationValue));
    }

    public void submitStations(ActionEvent e) {
        int stationValue = stationSpinner.getValue();
        tableManager.createTable(stationValue, "Station");
    }

    public void homePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void layoutPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("layoutPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void fileChooser(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser();
        file.setTitle("Open File");
        String currentDirectory = System.getProperty("user.dir");
        file.setInitialDirectory(new File(currentDirectory));
        File imgFile = file.showOpenDialog(stage);

        if (imgFile != null) {
            imagePathName = imgFile.getAbsolutePath();
            imagePath.setText(imagePathName);
        }
    }

    public void submitImage(ActionEvent event) throws IOException {
        String pictureTableName = "picture_table";
        String columnName = "file_data";
        File file = new File(imagePathName);
        if (blobManager != null) {
            blobManager.storeBlob(pictureTableName, columnName, file);
        } else {
            System.out.println("BlobManager is not initialized");
        }
    }
}
