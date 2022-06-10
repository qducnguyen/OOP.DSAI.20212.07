package dsai.group07.force.view;



import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class DraggableMaker {
	
	private double mouseAnchorX;
	private double mouseAnchorY;
	
	public void makeDraggable(Node node) {
		node.setOnMousePressed(mouseEvent -> {
			mouseAnchorX = mouseEvent.getX();
			mouseAnchorY = mouseEvent.getY();
		});
		
		node.setOnMouseDragged(mouseEvent -> {
			node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
			node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
		});
	}
	
	
}
