package application;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.ClipboardContent;

public interface Draggable {
    void handleDrop(double x, double y);

    default void startDragAndDrop(Dragboard dragboard, TransferMode... transferModes) {
        ClipboardContent content = new ClipboardContent();
        content.putString(" ");
        dragboard.setContent(content);
        dragboard.startDragAndDrop(transferModes);
    }
}