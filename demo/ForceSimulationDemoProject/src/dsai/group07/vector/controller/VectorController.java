package dsai.group07.vector.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

public class VectorController extends ImageView{
	@FXML
	ImageView myVector;
	@FXML 
	CheckBox myVisible;
	
	int x = 0;
	int y = 0;
	
	public void increaseXvalue(int dx) {
		this.x += dx;
		this.myVector.setScaleX(x);
	}
	
	public void increaseYvalue(int dy) {
		this.y += dy;
	}
	
	public void checkBoxValue() {
		if (this.myVisible.isSelected()) {
			this.myVector.setVisible(true);
		}
		else {
			this.myVector.setVisible(false);
		}
	}
	
	
	
}
