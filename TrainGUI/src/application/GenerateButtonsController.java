package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;


public class GenerateButtonsController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private Spinner<Integer> switchSpinner;
	@FXML
	private Spinner<Integer> stationSpinner;
	@FXML
	private Label switchLabel;
	@FXML
	private Label stationLabel;
	
	
	int switchValue;
	int stationValue;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// New valueFactories for the switch and station spinners up to 15
		SpinnerValueFactory<Integer> switchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15);
		SpinnerValueFactory<Integer> stationValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15);
		// Start both off at 1
		switchValueFactory.setValue(1);
		stationValueFactory.setValue(1);
		// Update each spinner to get this value
		switchSpinner.setValueFactory(switchValueFactory);
		stationSpinner.setValueFactory(stationValueFactory);
		
		switchValue = switchSpinner.getValue();
		stationValue = stationSpinner.getValue();
		
		// The spinner numbers were tiny, so I am adding in labels imposed over it that will show the numbers
		stationLabel.setText(Integer.toString(stationValue));
		switchLabel.setText(Integer.toString(switchValue));
		// FIXME: Does not continually update, Does weird shit
		// adds listener that "should" work. It does not correctly change the label
		switchSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				
				stationValue = stationSpinner.getValue();
				// Should change the int from spinner to text and send		
				stationLabel.setText(Integer.toString(stationValue));
				}
		});
		// FIXME: Does not continually update, Does weird shit
		stationSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
			
				switchValue = switchSpinner.getValue();
				// Should change the int from spinner to text and send		
				switchLabel.setText(Integer.toString(switchValue));
			}
		});
	}
	
	public void homePage(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
