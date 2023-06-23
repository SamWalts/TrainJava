package application;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Station extends DraggableObject {
    public Station(Node view) {
        super(view);
    }

    @Override
    public void handleDrop(double x, double y) {
        // Perform the specific action when the switch is dropped onto the image
        // You can access the x and y coordinates where the switch was dropped
        System.out.println("Station dropped at coordinates: " + x + ", " + y);
    }

    @Override
    public void startDragAndDrop(Dragboard dragboard, TransferMode... transferModes) {
        dragboard.setDragView(getView().snapshot(null, null));
        super.startDragAndDrop(dragboard, transferModes);
    }
}
