package application;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Switch extends DraggableObject {
    public Switch(Node view) {
        super(view);
    }

    public void handleDrop(double x, double y) {
        // Perform the specific action when the switch is dropped onto the image
        // You can access the x and y coordinates where the switch was dropped
        System.out.println("Switch dropped at coordinates: " + x + ", " + y);
    }

    public void startDragAndDrop(Dragboard dragboard, TransferMode... transferModes) {
        dragboard.setDragView(getView().snapshot(null, null));
        super.startDragAndDrop(dragboard, transferModes);
    }
}
