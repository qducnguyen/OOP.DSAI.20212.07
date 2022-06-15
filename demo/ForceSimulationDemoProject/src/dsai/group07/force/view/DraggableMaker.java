package dsai.group07.force.view;



import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class DraggableMaker {
	
	private double startX;
	private double startY;
	
	public void makeDraggable(Node node) {
		node.setOnMousePressed(mouseEvent -> {
			startX = mouseEvent.getSceneX() - node.getTranslateX();
			System.out.println(startX);
			startY = mouseEvent.getSceneY() - node.getTranslateY();
			System.out.println(startY);
		});
		
		node.setOnMouseDragged(mouseEvent -> {
			if (mouseEvent.getSceneX()> 50) {
				node.setTranslateX(mouseEvent.getSceneX() - startX);
			}
			if (mouseEvent.getSceneY() > 200) {
				node.setTranslateY(mouseEvent.getSceneY() - startY);
			}
		});
		
		node.setOnMouseReleased(mouseEvent ->{
			if ((mouseEvent.getSceneX() > 50) & (mouseEvent.getSceneY() > 0 )){
				node.setTranslateX(startX);
				node.setTranslateY(startY);
				node.setTranslateX(463 - node.getLayoutX());
				node.setTranslateY(361 - node.getLayoutY());	
			}
		});
	}
	
	
}
