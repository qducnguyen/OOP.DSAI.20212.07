package test;

import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JFrame;

import javafx.scene.control.TextField;

public class MouseEventDemo extends JFrame{
	private TextField tfMouseX;
	private TextField tfMouseY;
	public MouseEventDemo() {
		setLayout(new FlowLayout());
		add(new Label("X-Click: "));
		tfMouseX = new TextField("10");
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

	}

}
