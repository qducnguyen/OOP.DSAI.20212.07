package dsai.group07.force.view;


//import DraggableMaker;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.TextField;

public class ForceSimulationController {
	
	private int BACKGROUND_WIDTH = 2000;
	@FXML
	Rectangle myRect;
	@FXML
	ImageView background1;

	@FXML
	ImageView background2;

	@FXML
	Button btnControl;
	
	@FXML
    private Button speedButton;
	
    @FXML
    private TextField speedTextField;
    
    DraggableMaker mydrag = new DraggableMaker();
	private ParallelTransition parallelTransition;
	
	@FXML
	public void initialize() {
		// Drag and Drop
		mydrag.makeDraggable(myRect);
	
		TranslateTransition translateTransition =
	            new TranslateTransition(Duration.millis(10000), background1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition.setInterpolator(Interpolator.LINEAR);
	
		TranslateTransition translateTransition2 =
            new TranslateTransition(Duration.millis(10000), background2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		parallelTransition = 
			new ParallelTransition( translateTransition, translateTransition2 );
		parallelTransition.setCycleCount(Animation.INDEFINITE);

		//
		// Sets the label of the Button based on the animation state
		//
		parallelTransition.statusProperty().addListener(new ChangeListener<Status>() {
			public void changed(ObservableValue<? extends Status> obs, Status oldValue, Status newValue) {
				if( newValue == Animation.Status.RUNNING ) {
					btnControl.setText( "||" );
				} else {
					btnControl.setText( ">" );
				}
			}
		});
		
		
		speedButton.setOnAction( 
				new EventHandler<ActionEvent>() {
					
					public void handle(ActionEvent event) {
						try {
						double speedNow = Double.valueOf(speedTextField.getText());
						
						double rateNow = speedNow / 30.0;
						
						System.out.println("Rate Now: " + rateNow);
						
						parallelTransition.setRate(rateNow);
						
						}
						catch (NumberFormatException e){
							speedTextField.setText("Enter Amount");
						}
					}
				});
		
		parallelTransition.setRate(0.0);
		startAmination();
		
	}
	
	public void startAmination() {
		parallelTransition.play();
	}
	
	public void pauseAnimation() {
		parallelTransition.pause();
	}
	
	@FXML
	public void controlPressed() {
		if( parallelTransition.getStatus() == Animation.Status.RUNNING ) {
			pauseAnimation();
		} else {
			startAmination();
		}
	}
	
	
}
