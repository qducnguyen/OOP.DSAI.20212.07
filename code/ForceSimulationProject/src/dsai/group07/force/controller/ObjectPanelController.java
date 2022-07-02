package dsai.group07.force.controller;



import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.objectProperty.ObjectPropertySimulation;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.value.ObservableNumberValue;
import dsai.group07.force.model.vector.FrictionForce;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ObjectPanelController {

	
    private Simulation simul;
    
    private StackPane topStackPane;
    private StackPane downStackPane;
    
    private RotateTransition cirRotate;
    
	private final DataFormat cirFormat = new DataFormat("dsai.group07.force.circle");
	private final DataFormat recFormat = new DataFormat("dsai.group07.force.rec");
	
    @FXML
    private GridPane gridPaneObjectContainer;

    @FXML
    private Rectangle rec;

    @FXML
    private Circle cir;
    
//    @FXML
//    private CheckBox draggableCheckBox;
    ObjectPropertySimulation op;


	public void setDownStackPane(StackPane downStackPane) {
		this.downStackPane = downStackPane;
		
    	//TODO: more binding for circle

    	cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
    	rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
    	rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
    	
	}

	

	public void setTopStackPane(StackPane topStackPane) {
		this.topStackPane = topStackPane;
		
		 this.topStackPane.setOnDragDropped(event -> 
	    	{
	    		Dragboard db = event.getDragboard();
	        	
	            if (db.hasContent(cirFormat)) 
	            {
	            	StackPane.setAlignment(cir, Pos.BOTTOM_CENTER);
	            	
	            	//TODO: another view for drag and drop ...
	            	if (topStackPane.getChildren().contains(rec)) {
	            		gridPaneObjectContainer.add(rec, 0, 0);
	            	}
	            	
	            	topStackPane.getChildren().add(cir);
	            	
	            	try {
						this.simul.setObject(new Cylinder());
						op = new ObjectPropertySimulation(this.simul);
//						System.out.println("Object Panel Controller: " + this.simul.getObj());

//		            	System.out.println("Object Panel Controller: " + ((Cylinder)this.simul.getObj()).getRadius());

		            	((Cylinder)this.simul.getObj()).radiusProperty().addListener(
		            				(observable, oldValue, newValue) -> {
		            					double tmp = (double) newValue;
		            					System.out.println(newValue.getClass());
		            					System.out.println("Test final: " + newValue);
		            					cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(tmp));
		            				}
		            			);
					} catch (Exception e) {
						e.printStackTrace();
					}
	            	
	            	event.setDropCompleted(true);
	            	
	            }          
	            
	            else if (db.hasContent(recFormat)) {
	            	
	            	StackPane.setAlignment(rec, Pos.BOTTOM_CENTER);
	
	            	if (topStackPane.getChildren().contains(cir)) {
	            		gridPaneObjectContainer.add(cir, 1, 0);
	            	}
	            	
	            	topStackPane.getChildren().add(rec);
	            	
	            	try {
						this.simul.setObject(new Cube());
						op = new ObjectPropertySimulation(this.simul);
						((Cube)this.simul.getObj()).sizeProperty().addListener(
	            				(observable, oldValue, newValue) -> {
	            					double tmp = (double) newValue;
	            					System.out.println(newValue.getClass());
	            					System.out.println("Test final: " + newValue);
	            					rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(tmp));
	            					rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(tmp));
	            				}
	            			);
					} catch (Exception e) {
						e.printStackTrace();
					}
	            	
	            	event.setDropCompleted(true);
	            	
	            }
	            op = new ObjectPropertySimulation(this.simul);
	            try {
					op.start(new Stage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//	            if (this.simul.getObj() instanceof Cylinder) {
//	            	System.out.println("Object Panel Controller: " + this.simul.getObj());
//	            	cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(((Cylinder) this.simul.getObj()).getRadius()));
//	            	System.out.println("Object Panel Controller: " + ((Cylinder)this.simul.getObj()).getRadius());
//	            }
//	            else if (this.simul.getObj() instanceof Cube) {
//	            	
//	            	rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(((Cube) this.simul.getObj()).getSize() * 2));
//	            	rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(((Cube) this.simul.getObj()).getSize() * 2));
//	            }
//	            cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
		});
		 
	 this.topStackPane.setOnDragOver(event -> 
        {
        	Dragboard db = event.getDragboard();
    		if(db.hasContent(cirFormat) && cir.getParent()!= topStackPane) {

    			event.acceptTransferModes(TransferMode.MOVE);

    		}
    		else if(db.hasContent(recFormat) && this.rec.getParent()!= topStackPane)
    			event.acceptTransferModes(TransferMode.MOVE);	
        });
	 

	 
	}

	


	private EventDragDetected cirOnDragDectected = new EventDragDetected(cirFormat) ;
    private EventDragDetected recOnDragDectected = new EventDragDetected(recFormat) ;
    
    @FXML
   	public void initialize()  {
    	
    	//Setting image for the Circle
    	Image cirImage =  new Image("file:resources/images/plus-sign.png");
    	 
    	cir.setFill(new ImagePattern(cirImage));
        
    	
        //Drag and drop
    	cir.setOnDragDetected(cirOnDragDectected);
		rec.setOnDragDetected(recOnDragDectected);
    	
    	
        gridPaneObjectContainer.setOnDragDropped(event -> 
    	{
    		Dragboard db = event.getDragboard();
        	
            if (db.hasContent(cirFormat))
            {
            	//TODO: another view for drag and drop ...
            	cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
            	gridPaneObjectContainer.add(cir, 1 , 0);
            	//model
            	this.simul.setObject(null);
            	
            	event.setDropCompleted(true);
            }          
            
            else if (db.hasContent(recFormat))
            {
            	rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
            	rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
            	gridPaneObjectContainer.add(rec, 0 , 0);
            	
            	this.simul.setObject(null);
            	
            	event.setDropCompleted(true);
            }
	});
    
        gridPaneObjectContainer.setOnDragOver(event -> 
        {
        	Dragboard db = event.getDragboard();
    		if(db.hasContent(cirFormat) && cir.getParent()!= gridPaneObjectContainer) {
    			event.acceptTransferModes(TransferMode.MOVE);
    		}
    		else if(db.hasContent(recFormat) &&  rec.getParent()!= gridPaneObjectContainer)
    			event.acceptTransferModes(TransferMode.MOVE);	
        });
    }
    
	
    public void setSimul(Simulation simul) {
		this.simul = simul;
    	
    	this.simul.objProperty().addListener(
    			//TODO: unbind getSysAngAcc ..
    			(observable, oldValue, newValue) -> 
    			{	
//					this.simul.setObject(newValue);
    				if(newValue == null) {
    					System.out.println("Null Object");
//        		    	this.simul.getaForce().setValue(0);
    				}
    				else if (newValue instanceof Cylinder) {
    					System.out.println("Cylinder Time.......");
    					System.out.println(newValue.getClass());
    					((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
    					//this.simul.getSysAngAcc().bind(((Cylinder) newValue).angAccProperty());
//        		    	objectListener();
    				}
    				else
    				{
    					System.out.println("Cube Time......");
    					System.out.println(newValue.getClass());
    					((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
//        		    	objectListener();
    				}
    				
    			});

    	
    	
    	//Draggable bind to this.simul.isStartProperty()
    	this.simul.isStartProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{
    				if (newValue) {
    					rec.setOnDragDetected(null);
    					cir.setOnDragDetected(null);
    					
    					
    				}
    				else {
    					cir.setOnDragDetected(cirOnDragDectected);
        				rec.setOnDragDetected(recOnDragDectected);
        				
        				
    				}
    			});
    	
//    	this.simul.isPauseProperty().addListener(
//    			(observable, oldValue, newValue) -> 
//    			{
//    				if(newValue) {
//    					this.pauseCirAnimation();
//    				}
//    				else {
//    					if (cir.getParent() == topStackPane) {
//    						this.continueCirAnimation();
//    					}
//    				}
//    			});
    	
    	 //Circle Rotation
        setUpCircleRotation();
	}
    
	public void resetObjectPosition() {
		gridPaneObjectContainer.getChildren().clear();
		gridPaneObjectContainer.add(rec, 0 ,0);
		gridPaneObjectContainer.add(cir, 1 ,0);
	}

    
	private void setUpCircleRotation() {
		final int DURATION_ROTATE = 3;
		final double DEFAULT_ROTATE_VEL = 10;
		this.cirRotate = new RotateTransition(Duration.seconds(DURATION_ROTATE), cir);
		this.cirRotate.setByAngle(360);
		this.cirRotate.setInterpolator(Interpolator.LINEAR);
		this.cirRotate.setCycleCount(Animation.INDEFINITE);
		
		this.cirRotate.setRate(0.0);
		
		this.simul.objProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
					if (newValue instanceof Cylinder) {
						this.simul.getSysAngAcc().bind(((Cylinder) newValue).angAccProperty());
						this.simul.getSysAngVel().bind(((Cylinder) newValue).angVelProperty());
					}
				});
		
		this.cirRotate.rateProperty().bind(this.simul.getSysAngVel().multiply(1 / DEFAULT_ROTATE_VEL)); // Change later
		
	}
	
	public void startCirAmination() {
		if(cirRotate != null && this.simul.getObj() instanceof Cylinder) {
		cirRotate.play();
		}
	}
	
    public void continueCirAnimation() {
    	if(cirRotate != null && this.simul.getObj() instanceof Cylinder) {
    	cirRotate.play();
    	}
    }
    
    
	public void pauseCirAnimation() {
		if(cirRotate != null) {
		cirRotate.pause();
		}
	}
	
	public void resetCirAnimation() {
		if(cirRotate != null) {
		cirRotate.jumpTo(Duration.ZERO);
		cirRotate.stop();
		}
	}
	
	

	private class EventDragDetected implements EventHandler<MouseEvent>{
    	private final DataFormat shapeFormat;
    	public EventDragDetected(DataFormat data) {
    		this.shapeFormat = data;
		}
    	
    	@Override
    	public void handle(MouseEvent event) {
    		Shape s = (Shape) event.getSource();
    		Dragboard db = s.startDragAndDrop(TransferMode.MOVE);
    		
    		SnapshotParameters snapShotparams = new SnapshotParameters();
    		snapShotparams.setFill(Color.TRANSPARENT);
    		db.setDragView(s.snapshot(snapShotparams, null), event.getX(), event.getY());
    		if (s instanceof Circle) {
    			db.setDragViewOffsetX(event.getX() + cir.getRadius());
    			db.setDragViewOffsetY(event.getY() + cir.getRadius());
    		}
    		ClipboardContent cc = new ClipboardContent();
    		cc.put(shapeFormat, this.shapeFormat.toString());
    		db.setContent(cc);
    	}
    	
    }
    
	public void objectListener() {
		try {
			this.simul.getObj().massProperty().addListener(observable -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			this.simul.getObj().velProperty().valueProperty().addListener(observable -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
