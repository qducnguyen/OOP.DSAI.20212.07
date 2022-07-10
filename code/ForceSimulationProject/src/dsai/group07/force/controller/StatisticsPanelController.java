package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.Rotatable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class StatisticsPanelController {
	
	private Simulation simul;
	
    private StackPane stackPane;
	
    @FXML
    private Label angLabel;

    @FXML
    private Label angAccLabel;

    @FXML
    private Label angVelLabel;
    
    @FXML
    private Label posLabel;

    @FXML
    private Label accLabel;

    @FXML
    private Label velLabel;

    @FXML
    private Label aForceLabel;

    @FXML
    private Label fForceLabel;

    @FXML
    private Label sumForceLabel;
    
    private Rectangle rec;
    
    private Circle cir;
    
    private Rectangle aArrow;
    
    private Rectangle fArrow;
    
    private Rectangle nArrow;
    
    
	
	@FXML
   	public void initialize()  {
		
		
		 angLabel.setText("Current Angle Position  : 0.00 *");
		 angAccLabel.setText("Current Angular Accelerate: 0.00 */s^2");
		 angVelLabel.setText("Current Angular Velocity: 0.00 */s");
		 
		 //Default 3 Label above are invisible.
		 setVisibleThreeAngLabels(false);
		 
		 
		 accLabel.setText("Current Accelerate : 0.00 m/s^2");
		 velLabel.setText("Current Velocity : 0.00 m/s");
		 posLabel.setText("Current Position : 0.00 m");
		 aForceLabel.setText("Applied Force : 0 N");
		 fForceLabel.setText("Friction Force : 0 N");
		 sumForceLabel.setText("Net Force : 0 N");
	}
	
	
	public void init(Simulation simul, Rectangle rec, Circle cir, StackPane topStackPane) {
		setSimul(simul);
		this.rec = rec;
		this.cir = cir;
		this.stackPane = topStackPane;
		setUpAppliedForce();
		setUpFrictionForce();
		setUpNetForce();
	}
	
	
	
	public void setSimul(Simulation simul) {
		this.simul = simul;
		
		
		
		angVelLabel.textProperty().bind(this.simul.getSysAngVel().asString("Current Angular Velocity : %.2f m/s"));

		
		this.simul.sysVelProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					velLabel.textProperty().bind(newValue.valueProperty().asString("Current Velocity : %.2f m/s"));
				});
		
	
		this.simul.objProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					if(newValue instanceof Rotatable) {
						 setVisibleThreeAngLabels(true);
					}
					else {
						setVisibleThreeAngLabels(false);
					}
				}
				);
		
		this.simul.sysAccProperty().addListener(
				(observable, oldValue, newValue) -> {
					accLabel.textProperty().bind(newValue.valueProperty().asString("Current Accelerate : %.2f m/s^2"));
				});
		
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.simul.setObject(newValue);
				
				ObservableStringValue posString = Bindings.createStringBinding(() -> 
				"Current Position : " +  String.format("%.2f",this.simul.getObj().getPos()) + " m", this.simul.getObj().posProperty());
				posLabel.textProperty().bind(posString);
				
				if (newValue instanceof Cylinder) {
					
					ObservableStringValue angPosString = Bindings.createStringBinding(() -> 
					"Current Angle : " + String.format("%.2f",((Cylinder) this.simul.getObj()).getAngle()) + " *", ((Cylinder) this.simul.getObj()).angleProperty());
					angLabel.textProperty().bind(angPosString);
					
					ObservableStringValue angVelString = Bindings.createStringBinding(() -> 					
					"Current Angular Velocity : " + String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngVel()) + " */s", ((Cylinder) this.simul.getObj()).angVelProperty());
					angVelLabel.textProperty().bind(angVelString);
										
					ObservableStringValue angAccString = Bindings.createStringBinding(() -> 
					"Current Angular Accelerate : " + String.format("%.2f",((Cylinder) this.simul.getObj()).getAngAcc()) + " */s^2", ((Cylinder) this.simul.getObj()).angAccProperty());
					angAccLabel.textProperty().bind(angAccString);
				}
			} 
		});

		ObservableStringValue aForceString = Bindings.createStringBinding(() -> 
		"Current Applied Force : " + String.format("%.2f",this.simul.getaForce().getValue()) + " N", this.simul.getaForce().valueProperty());
		aForceLabel.textProperty().bind(aForceString);

		ObservableStringValue fForceString = Bindings.createStringBinding(() -> 
		"Current Friction Force : " + String.format("%.2f",this.simul.getfForce().getValue()) + " N", this.simul.getfForce().valueProperty());
		fForceLabel.textProperty().bind(fForceString);

		//System.out.println(this.simul.getNetForce().getValue());
		ObservableStringValue netForceString = Bindings.createStringBinding(() -> 
		"Current Net Force : " + String.format("%.2f",this.simul.getNetForce().getValue()) + " N", this.simul.getNetForce().valueProperty());
		sumForceLabel.textProperty().bind(netForceString);
	};
	
	private void setVisibleThreeAngLabels(boolean isVi) {
		angLabel.setVisible(isVi);
		 angAccLabel.setVisible(isVi);
		 angVelLabel.setVisible(isVi);
	}
	
	
	
	private void setUpAppliedForce() {
		// Setup Arrow
		
		aArrow = new Rectangle(200 , 50);
		StackPane.setAlignment(aArrow, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(aArrow);	
		Image recImage =  new Image("file:resources/images/arrow.png");
		aArrow.setFill(new ImagePattern(recImage));
		aArrow.setStroke(Color.TRANSPARENT);
		
		//Label for arrow
		
		Label aArrowLabel = new Label("Applied Force");
		StackPane.setAlignment(aArrowLabel, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(aArrowLabel);	
		

		
		
		// Resize Arrow
    	double firstWidth  = aArrow.getWidth();
    	 Rotate rotate = new Rotate();
		 rotate.setPivotX(0);
		 rotate.setPivotY(aArrow.getHeight() / 2);
		 aArrow.getTransforms().add(rotate);
    	 Translate translate = new Translate();
    	 aArrow.getTransforms().add(translate);
    	 aArrow.setWidth(0);
    	
    	 
    	this.simul.getaForce().valueProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{	
    				if (newValue.doubleValue()  >= 0 ) {
    					rotate.setAngle(0);
    				}
    				else {
    					rotate.setAngle(180);

    				}
    				translate.setX(firstWidth  * newValue.doubleValue() / 100 / 2);
    				aArrow.setWidth(firstWidth  * Math.abs(newValue.doubleValue()) / 100);

    			});
    	
    	// Position arrow in the center of the object, bring arrow and label to front
    	
    	this.simul.objProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{
    				if (newValue instanceof Cube) {
    				aArrow.translateYProperty().bind(rec.heightProperty().divide(2).subtract(aArrow.heightProperty().divide(2)).multiply(-1));
    				}
    				else {
    					aArrow.translateYProperty().bind(cir.radiusProperty().subtract(aArrow.heightProperty().divide(2)).multiply(-1));
    				}
    				
    				aArrowLabel.toFront();
    				aArrow.toFront();
    			}
    			);
    	
    	
    	// Position Label
   	 	aArrowLabel.translateXProperty().bind(translate.xProperty().multiply(2).add(20));
   	 	aArrowLabel.translateYProperty().bind(aArrow.translateYProperty());
    
	}
	
	private void setUpFrictionForce()
	{
		fArrow = new Rectangle(200 , 50);
		StackPane.setAlignment(fArrow, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(fArrow);	
		Image recImage =  new Image("file:resources/images/arrow.png");
		fArrow.setFill(new ImagePattern(recImage));
		fArrow.setStroke(Color.TRANSPARENT);
		
		//Label for arrow
		
		Label fArrowLabel = new Label("Friction Force");
		StackPane.setAlignment(fArrowLabel, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(fArrowLabel);	
		

		
		
		// Resize Arrow
    	double firstWidth  = fArrow.getWidth();
    	 Rotate rotate = new Rotate();
		 rotate.setPivotX(0);
		 rotate.setPivotY(fArrow.getHeight() / 2);
		 fArrow.getTransforms().add(rotate);
    	 Translate translate = new Translate();
    	 fArrow.getTransforms().add(translate);
    	 fArrow.setWidth(0);
    	
    	 
    	this.simul.getfForce().valueProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{	
    				if (newValue.doubleValue()  >= 0 ) {
    					rotate.setAngle(0);
    				}
    				else {
    					rotate.setAngle(180);

    				}
    				translate.setX(firstWidth  * newValue.doubleValue() / 100 / 2);
    				fArrow.setWidth(firstWidth  * Math.abs(newValue.doubleValue()) / 100);

    			});
    	
    	// Position arrow in the center of the object, bring arrow and label to front
    	
    	this.simul.objProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{
    				if (newValue instanceof Cube) {
    				fArrow.translateYProperty().bind(rec.heightProperty().divide(2).subtract(fArrow.heightProperty().divide(2)).multiply(-1));
    				}
    				else {
    					fArrow.translateYProperty().bind(cir.radiusProperty().subtract(fArrow.heightProperty().divide(2)).multiply(-1));
    				}
    				
    				fArrowLabel.toFront();
    				fArrow.toFront();
    			}
    			);
    	
    	
    	// Position Label
   	 	fArrowLabel.translateXProperty().bind(translate.xProperty().multiply(2).add(20));
   	 	fArrowLabel.translateYProperty().bind(fArrow.translateYProperty());
	};
	private void setUpNetForce() 
	{
		nArrow = new Rectangle(200 , 50);
		StackPane.setAlignment(nArrow, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(nArrow);	
		Image recImage =  new Image("file:resources/images/arrow.png");
		nArrow.setFill(new ImagePattern(recImage));
		nArrow.setStroke(Color.TRANSPARENT);
		
		//Label for arrow
		
		Label nArrowLabel = new Label("Net Force");
		StackPane.setAlignment(nArrowLabel, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(nArrowLabel);	
		

		
		
		// Resize Arrow
    	double firstWidth  = nArrow.getWidth();
    	 Rotate rotate = new Rotate();
		 rotate.setPivotX(0);
		 rotate.setPivotY(nArrow.getHeight() / 2);
		 nArrow.getTransforms().add(rotate);
    	 Translate translate = new Translate();
    	 nArrow.getTransforms().add(translate);
    	 nArrow.setWidth(0);
    	
    	 
    	this.simul.getNetForce().valueProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{	
    				if (newValue.doubleValue()  >= 0 ) {
    					rotate.setAngle(0);
    				}
    				else {
    					rotate.setAngle(180);

    				}
    				translate.setX(firstWidth  * newValue.doubleValue() / 100 / 2);
    				nArrow.setWidth(firstWidth  * Math.abs(newValue.doubleValue()) / 100);

    			});
    	
    	// Position arrow in the center of the object, bring arrow and label to front
    	
    	this.simul.objProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{
    				if (newValue instanceof Cube) {
    				nArrow.translateYProperty().bind(rec.heightProperty().subtract(nArrow.heightProperty().divide(2)).multiply(-1));
    				}
    				else {
    					nArrow.translateYProperty().bind(cir.radiusProperty().multiply(2).subtract(nArrow.heightProperty().divide(2)).multiply(-1));
    				}
    				
    				nArrowLabel.toFront();
    				nArrow.toFront();
    			}
    			);
    	
    	
    	// Position Label
   	 	nArrowLabel.translateXProperty().bind(translate.xProperty().multiply(2).add(20));
   	 	nArrowLabel.translateYProperty().bind(nArrow.translateYProperty());
    
	};
	
}
