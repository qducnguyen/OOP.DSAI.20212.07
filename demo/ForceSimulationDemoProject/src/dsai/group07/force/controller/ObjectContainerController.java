package dsai.group07.force.controller;



import dsai.group07.force.model.Simulation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ObjectContainerController {

	
	private AnimationController aniController;
    private Simulation simul;

    
    
	private final DataFormat cirFormat = new DataFormat("dsai.group07.force.circle");
	private final DataFormat recFormat = new DataFormat("dsai.group07.force.rec");
	
    @FXML
    private GridPane gridPaneObjectContainer;

    @FXML
    private Rectangle rec;

    @FXML
    private Circle cir;
    
    @FXML
    private CheckBox draggableCheckBox;
    
    


	public void setAniController(AnimationController aniController) {
		this.aniController = aniController;
		
    	//TODO: more binding for circle

    	cir.radiusProperty().bind(this.aniController.getDownStackPane().heightProperty().multiply(0.3));
    	rec.widthProperty().bind(this.aniController.getDownStackPane().widthProperty().multiply(0.1));
    	rec.heightProperty().bind(this.aniController.getDownStackPane().heightProperty().multiply(0.8));
    	


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
        	
            if (db.hasContent(cirFormat))
            {
            	//TODO: another view for drag and drop ...
            	
            	gridPaneObjectContainer.add(cir, 1 , 0);
            	//model
            	this.simul.setObject(null);
            	
            	event.setDropCompleted(true);
            }          
            
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
        
        
        
        draggableCheckBox.selectedProperty().addListener(
        		(observable, oldValue, newValue) -> 
        		{
        			if (newValue) {
        				cir.setOnDragDetected(cirOnDragDectected);
        				rec.setOnDragDetected(recOnDragDectected);
        			}
        			else {
        				cir.setOnDragDetected(null);
        				rec.setOnDragDetected(null);
        			}
        		});
        
                
    }
    
    
    public DataFormat getCirFormat() {
    	return this.cirFormat;
    }
    
    public DataFormat getRecFormat() {
    	return this.recFormat;
    }
    
    public Circle getCircle() {
    	return this.cir;
    }
    
    public Rectangle getRectangle() {
    	return this.rec;
    }
	
    public void setSimul(Simulation simul) {
		this.simul = simul;
		
    	draggableCheckBox.selectedProperty().bind(this.simul.isStartProperty().not());

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
    			db.setDragViewOffsetY(event.getY()  + cir.getRadius());
    		}
    		ClipboardContent cc = new ClipboardContent();
    		cc.put(shapeFormat, this.shapeFormat.toString());
    		db.setContent(cc);
    	}
    	
    }
    
  
    
	
}
