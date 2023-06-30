package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceMenu;

    @FXML
    private ImageView image;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button choiceButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button saveSliderValue;
    
    @FXML
    private Button nextPage;
    
    @FXML
    private Button rotateButton;
    
    @FXML
    private Slider sliderWidth;
    
    @FXML
    private Slider sliderHeight;
    
    private DraggableMaker draggableMaker = new DraggableMaker();
    // Lists to keep track of our new stations and where they are.
    private List<Switch> switches = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();
    private List<Node> sharedList = new ArrayList<>();

    // Maybe not best practice BUT
    // Populates the choiceMenu, and has all of the actions that will send down to a method.
    // Could also call of this from SceneBuilder, but this is a lot cleaner having it in one spot
    // Especially with testing this out.
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceMenu.getItems().add("Switch");
        choiceMenu.getItems().add("Station");

        nextPage.setOnAction(e -> nextPageButtonClicked());
        saveSliderValue.setOnAction(e -> saveSliderButtonClicked());
        choiceButton.setOnAction(e -> getChoice(choiceMenu.getValue()));
        undoButton.setOnAction(e -> undoButtonClicked());
        rotateButton.setOnAction(e -> rotateButtonClicked()); 
        draggableMaker.makeDraggable(image);
    }

    public double[] getSliderValues() {
        double heightSlider = sliderHeight.getValue();
        double widthSlider = sliderWidth.getValue();
        return new double[]{heightSlider, widthSlider};
    }
    // Currently applies to both of the objects, switch and station. This is where we would
    // Go to change this if we needed to.
    private void applySliderValues(Node shape, double widthSlider, double heightSlider) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            rectangle.setWidth(widthSlider);
            rectangle.setHeight(heightSlider);
            
            // Update the corresponding station or switch object
            if (switches.contains(rectangle)) {
                for (Switch switchObj : switches) {
                    if (switchObj.getRectangle() == rectangle) {
                        // Update the switch object with new width and height
                        switchObj.setWidth(widthSlider);
                        switchObj.setHeight(heightSlider);
                        break;
                    }
                }
            } else if (stations.contains(rectangle)) {
                for (Station stationObj : stations) {
                    if (stationObj.getRectangle() == rectangle) {
                        // Update the station object with new width and height
                        stationObj.setWidth(widthSlider);
                        stationObj.setHeight(heightSlider);
                        break;
                    }
                }
            }
        }
    }


    public void getChoice(String building) {
        System.out.println("This is a " + building);

        if (building.equals("Switch")) {
            Switch draggableSwitch = new Switch(anchorPane);
            addShapeToPane(draggableSwitch);
            switches.add(draggableSwitch);
            System.out.println("Switch added to the list");
            System.out.println(sharedList);
        } else if (building.equals("Station")) {
            Station draggableStation = new Station(anchorPane);
            addShapeToPane(draggableStation);
            // Adds to the stations list
            stations.add(draggableStation);
            System.out.println("Station added to the list");
            System.out.println(sharedList);
        }
    }
    
 //TODO: Must have the new values update inside of the lists
    //TODO: Must use something like getLocationOnPane or some shit.
    public void saveSliderButtonClicked() {
        double[] sliderValues = getSliderValues();
        for (Node shape : sharedList) {
            applySliderValues(shape, sliderValues[1], sliderValues[0]);
        }
    }

// Works for the Switch object
    public void addShapeToPane(Switch draggableSwitch) {
        Rectangle rectangle = draggableSwitch.getRectangle();
        if (!anchorPane.getChildren().contains(rectangle)) {
            anchorPane.getChildren().add(rectangle);
            sharedList.add(rectangle);
            applySliderValues(rectangle, sliderWidth.getValue(), sliderHeight.getValue());
        }
    }

    public void addShapeToPane(Station draggableStation) {
        Rectangle rectangle = draggableStation.getRectangle();
        if (!anchorPane.getChildren().contains(rectangle)) {
            anchorPane.getChildren().add(rectangle);
            sharedList.add(rectangle);
            applySliderValues(rectangle, sliderWidth.getValue(), sliderHeight.getValue());
        }
    }
    
    public void rotateButtonClicked() {
        int lastItem = sharedList.size() - 1;
        if (lastItem >= 0) {
            Node shape = sharedList.get(lastItem);
            shape.setRotate(shape.getRotate() + 30);
        }
    }

    public void undoButtonClicked() {
        int lastItem = sharedList.size() - 1;
        if (lastItem >= 0) {
            Node lastShape = sharedList.remove(lastItem);
            anchorPane.getChildren().remove(lastShape);

            if (lastShape instanceof Rectangle) {
                // Remove the corresponding shape object from switches or stations list
                if (switches.removeIf(switchObj -> switchObj.getRectangle() == lastShape)) {
                    System.out.println("Switch removed from the list");
                } else if (stations.removeIf(stationObj -> stationObj.getRectangle() == lastShape)) {
                    System.out.println("Station removed from the list");
                }
            }
        }

        // Print all items in the list
    }
    
    public void nextPageButtonClicked() {
        for (Node shape : sharedList) {
            System.out.println(shape);
        }
    }
}