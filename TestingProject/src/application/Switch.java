package application;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Switch {

    private Rectangle rectangle;
    private int startPositionX = 50;
    private int startPositionY = 50;
    private int width = 10;
    private int height = 25;

    public Switch(AnchorPane anchorPane) {
        createDraggableSwitch(anchorPane);
    }
// This constructor is used in LayoutController.java
    private Rectangle createDraggableSwitch(AnchorPane anchorPane) {
        rectangle = new Rectangle(startPositionX, startPositionY, width, height);
        rectangle.setStroke(Color.GREEN);
        rectangle.setStrokeWidth(2);

        DraggableMaker draggableMaker = new DraggableMaker();
        draggableMaker.makeDraggable(rectangle);

       return rectangle;
    }

    public Switch() {
        this.startPositionX = 50;
        this.startPositionY = 50;
        this.width = 50;
        this.height = 10;
        rectangle = new Rectangle(startPositionX, startPositionY, width, height);
    }

    public Switch(int startPositionX, int startPositionY, int width, int height) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.width = width;
        this.height = height;
        rectangle = new Rectangle(startPositionX, startPositionY, width, height);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }
    public void removeStation(Pane root) {
        root.getChildren().remove(rectangle);
    }
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		
	}
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		
	}
}