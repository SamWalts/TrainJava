package application;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

public class DraggableMaker {
    private double mouseX;
    private double mouseY;

    public void makeDraggable(Node node) {
        node.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        node.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;

            // Adjust the mouse coordinates relative to the shape's rotation
            if (!node.getTransforms().isEmpty()) {
                Transform transform = node.getLocalToParentTransform();
                try {
                    Transform inverseTransform = transform.createInverse();
                    Point2D adjustedMousePos = inverseTransform.transform(new Point2D(event.getSceneX(), event.getSceneY()));
                    deltaX = adjustedMousePos.getX() - mouseX;
                    deltaY = adjustedMousePos.getY() - mouseY;
                } catch (NonInvertibleTransformException e) {
                    e.printStackTrace();
                }
            }

            // Apply translation
            node.setLayoutX(node.getLayoutX() + deltaX);
            node.setLayoutY(node.getLayoutY() + deltaY);

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
    }
}