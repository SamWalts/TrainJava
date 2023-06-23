package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private Button rotateButton;
    
    private DraggableMaker draggableMaker = new DraggableMaker();
    private List<Switch> switches = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();
    private List<Node> sharedList = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceMenu.getItems().add("Switch");
        choiceMenu.getItems().add("Station");

        // This is what happens when the choiceButton is pressed
        choiceButton.setOnAction(e -> getChoice(choiceMenu.getValue()));
        undoButton.setOnAction(e -> undoButtonClicked());
        rotateButton.setOnAction(e -> rotateButtonClicked()); 
        draggableMaker.makeDraggable(image);
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
// Works for the Switch object
    public void addShapeToPane(Switch draggableSwitch) {
        anchorPane.getChildren().add(draggableSwitch.getRectangle());
        sharedList.add(draggableSwitch.getRectangle());
    }
// Works for the Station
    public void addShapeToPane(Station draggableStation) {
        anchorPane.getChildren().add(draggableStation.getRectangle());
        sharedList.add(draggableStation.getRectangle());
    }
    
    @FXML
    public void rotateButtonClicked() {
        int lastItem = sharedList.size() - 1;
        if (lastItem >= 0) {
            Node shape = sharedList.get(lastItem);
            shape.setRotate(shape.getRotate() + 30);
        }
    }

    @FXML
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
        for (Node shape : sharedList) {
            System.out.println(shape);
        }
    }
}