package dsai.group07.force.controller;



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
    
    
    private EventDragDetected cirOnDragDectected = new EventDragDetected(cirFormat) ;
    private EventDragDetected recOnDragDectected = new EventDragDetected(recFormat) ;
    
    @FXML
   	public void initialize()  {
    	rec.widthProperty().bind(gridPaneObjectContainer.widthProperty().multiply(0.4));
    	rec.heightProperty().bind(gridPaneObjectContainer.heightProperty().multiply(0.8));
    	cir.radiusProperty().bind(gridPaneObjectContainer.widthProperty().multiply(0.2));
    	//TODO: more binding for circle
    	
    	
//        cir.setOnDragDetected(cirOnDragDectected);
//        rec.setOnDragDetected(recOnDragDectected);
        
        
        
        //Drag and drop
        
        gridPaneObjectContainer.setOnDragDropped(event -> 
    	{
    		Dragboard db = event.getDragboard();
        	
            if (db.hasContent(cirFormat))
            {
            	//TODO: another view for drag and drop ...
            	
            	gridPaneObjectContainer.add(cir, 1 , 0);
            	
            	event.setDropCompleted(true);
            }          
            
            else if (db.hasContent(recFormat))
            {
            	gridPaneObjectContainer.add(rec, 0 , 0);
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
    		ClipboardContent cc = new ClipboardContent();
    		cc.put(shapeFormat, "circle");
    		db.setContent(cc);
    	}
    	
    }
    
  
    
	
}
