package dsai.group07.force.controller;



import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ObjectContainerController {

	
    private Simulation simul;
    
    private StackPane topStackPane;
    private StackPane downStackPane;
    
    // DataFormat like casting but use for javafx
	private final DataFormat cirFormat = new DataFormat("dsai.group07.force.circle");
	private final DataFormat recFormat = new DataFormat("dsai.group07.force.rec");
	
    @FXML
    private GridPane gridPaneObjectContainer;

    @FXML
    private Rectangle rec;

    @FXML
    private Circle cir;
    
    // Note: Think about it
    @FXML
    private CheckBox draggableCheckBox;
    
    


	public void setDownStackPane(StackPane downStackPane) {
		this.downStackPane = downStackPane;
		
    	//TODO: more binding for circle

    	cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
    	rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
    	rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
    	
	}

	
	// TODO: separate listener for this class
	public void setTopStackPane(StackPane topStackPane) {
		this.topStackPane = topStackPane;
		
		 this.topStackPane.setOnDragDropped(event -> 
	    	{
	    		Dragboard db = event.getDragboard();
	        	
	    		// Drag and Drop Circle
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
					} 
	            	catch (Exception e) {
						e.printStackTrace();
					}
	            	
	            	event.setDropCompleted(true);
	            	
	            }          
	            
	            // Drag and drop rectangle
	            else if (db.hasContent(recFormat)) {
	            	
	            	StackPane.setAlignment(rec, Pos.BOTTOM_CENTER);
	
	            	if (topStackPane.getChildren().contains(cir)) {
	            		gridPaneObjectContainer.add(cir, 1, 0);
	            	}
	            	
	            	topStackPane.getChildren().add(rec);
	            	
	            	try {
						this.simul.setObject(new Cube());
					} catch (Exception e) {
						e.printStackTrace();
					}
	            	
	            	event.setDropCompleted(true);
	            	
	            }
		});
		 
	 this.topStackPane.setOnDragOver(event -> 
        {
        	Dragboard db = event.getDragboard();
    		if(db.hasContent(cirFormat) && this.cir.getParent()!= topStackPane) {
    			event.acceptTransferModes(TransferMode.MOVE);
    		}
    		else if(db.hasContent(recFormat) && this.rec.getParent()!= topStackPane) {
    			event.acceptTransferModes(TransferMode.MOVE);	
    		}
    			
        });
	}

	


	private EventDragDetected cirOnDragDectected = new EventDragDetected(cirFormat) ;
    private EventDragDetected recOnDragDectected = new EventDragDetected(recFormat) ;
    
    @FXML
   	public void initialize()  {
    	
    	
    	
        //Drag and drop
        
    	cir.setOnDragDetected(cirOnDragDectected);
		rec.setOnDragDetected(recOnDragDectected);
    	
    	
        gridPaneObjectContainer.setOnDragDropped(event -> 
    	{
    		Dragboard db = event.getDragboard();
        	
    		// Add circle 
            if (db.hasContent(cirFormat))
            {
            	//TODO: another view for drag and drop ...
            	
            	gridPaneObjectContainer.add(cir, 1 , 0);
            	//model
            	this.simul.setObject(null);
            	
            	event.setDropCompleted(true);
            }          
            
            // Add rectangle
            else if (db.hasContent(recFormat))
            {
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
        
        
        
        
        this.draggableCheckBox.selectedProperty().addListener(
        		(observable, oldValue, newValue) -> 
        		{
        			if (newValue) {
        				cir.setOnDragDetected(cirOnDragDectected);
        				rec.setOnDragDetected(recOnDragDectected);
        			}
        			else {
            				rec.setOnDragDetected(null);
        					cir.setOnDragDetected(null);
        			}
        		});
        
                
    }
    
	
    public void setSimul(Simulation simul) {
		this.simul = simul;
		
    	this.draggableCheckBox.selectedProperty().bind(this.simul.isStartProperty().not());
    	
    	this.simul.objProperty().addListener(
    			(observable, oldValue, newValue) -> 
    			{	
    				if(newValue == null) {
    					System.out.println("Null Object");
    					
    				}
    				else {
    					System.out.println(newValue.getClass());
    				
    				}
    			});

	}

	private class EventDragDetected implements EventHandler<MouseEvent>{
    	private final DataFormat shapeFormat;
    	public EventDragDetected(DataFormat data) {
    		this.shapeFormat = data;
		}
    	// 23-6-2022
    	@Override
    	public void handle(MouseEvent event) {
    		Shape s = (Shape) event.getSource();
    		Dragboard db = s.startDragAndDrop(TransferMode.MOVE);
    		
    		SnapshotParameters snapShotparams = new SnapshotParameters();
    		snapShotparams.setFill(Color.TRANSPARENT);
    		db.setDragView(s.snapshot(snapShotparams, null), event.getX(), event.getY());
    		if (s instanceof Circle) {
    			db.setDragViewOffsetX(event.getX() + cir.getRadius());
    			db.setDragViewOffsetY(event.getY()  + cir.getRadius());
    		}
    		ClipboardContent cc = new ClipboardContent();
    		cc.put(shapeFormat, this.shapeFormat.toString());
    		db.setContent(cc);
    	}
    	
    }
    
	public void resetObjectPosition() {
		topStackPane.getChildren().remove(rec);
		topStackPane.getChildren().remove(cir);
		gridPaneObjectContainer.getChildren().clear();
		gridPaneObjectContainer.add(rec, 0 ,0);
		gridPaneObjectContainer.add(cir, 1 ,0);
	}
    
	
}
