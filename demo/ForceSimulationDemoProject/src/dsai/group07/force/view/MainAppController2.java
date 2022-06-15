package dsai.group07.force.view;

import dsai.group07.force.LoopTimer;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MainAppController2 {
	
	private final int BACKGROUND_WIDTH = 2000;
	private final float ratioTwoBackGround = 5.0f;
	private final int numDuration = 40000;
	private final double acce = 0.05;
	private LoopTimer timer;

	private ParallelTransition parallelTransition;
    @FXML
    private ImageView backGroundMiddleUp;

    @FXML
    private ImageView backGroundRightUp;

    @FXML
    private ImageView backGroundMiddleDown;

    @FXML
	Button btnControl;
    
    @FXML
    Label posPathLabel;
    
    @FXML
    Label posVelLabel;
    
    @FXML
    private ImageView backGroundRightDown;
    
    	

    @FXML
	public void initialize()  {
    	
    	DoubleProperty pos = new SimpleDoubleProperty(0);
    	DoubleProperty vel = new SimpleDoubleProperty(0);
    		
    	posPathLabel.textProperty().bind(pos.asString("Total Path: %.2f m"));
    	posVelLabel.textProperty().bind(vel.asString("Current Velocity: %.2f m/s"));
    	
		TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(numDuration), backGroundRightUp);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition.setInterpolator(Interpolator.LINEAR);
		
		TranslateTransition translateTransition2 =
				new TranslateTransition(Duration.millis(numDuration), backGroundMiddleUp);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition2.setInterpolator(Interpolator.LINEAR);
		
		
		TranslateTransition translateTransition3 =
				new TranslateTransition(Duration.millis(numDuration / ratioTwoBackGround), backGroundMiddleDown);
		translateTransition3.setFromX(0);
		translateTransition3.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition3.setInterpolator(Interpolator.LINEAR);
		
		TranslateTransition translateTransition4 =
				new TranslateTransition(Duration.millis(numDuration / ratioTwoBackGround), backGroundRightDown);
		translateTransition4.setFromX(0);
		translateTransition4.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition4.setInterpolator(Interpolator.LINEAR);

		ParallelTransition parallelTransitionUp = 
				new ParallelTransition( translateTransition,  translateTransition2 );
		parallelTransitionUp.setCycleCount(Animation.INDEFINITE);
		
		ParallelTransition parallelTransitionDown = 
				new ParallelTransition( translateTransition3, translateTransition4 );
		parallelTransitionDown.setCycleCount(Animation.INDEFINITE);
		
		 parallelTransition =
				new ParallelTransition(parallelTransitionUp,parallelTransitionDown );
		//
		// Sets the label of the Button based on the animation state
		//
	
		 parallelTransition.rateProperty().bind(vel.multiply(0.5));

		 parallelTransition.statusProperty().addListener((obs, oldValue, newValue) -> {
				if( newValue == Animation.Status.RUNNING ) {
					btnControl.setText( "||" );
				} else {
					btnControl.setText( ">" );
				}
			});
		 
		 
//		 parallelTransition.setRate(1);

		 
		 
		timer = new LoopTimer() {
            @Override
            public void tick(float secondsSinceLastFrame) {
            	vel.set(secondsSinceLastFrame * acce + vel.get());
            	pos.set(secondsSinceLastFrame  * vel.get() + pos.get() );
            }
        };
       
		
//        startAmination();
		
	}
    
    public void setSce(Scene sce) {
    	backGroundRightUp.fitHeightProperty().bind(sce.heightProperty().multiply(0.7));
    	backGroundMiddleUp.fitHeightProperty().bind(sce.heightProperty().multiply(0.7));
    	backGroundRightDown.fitHeightProperty().bind(sce.heightProperty().multiply(0.3));
    	backGroundMiddleDown.fitHeightProperty().bind(sce.heightProperty().multiply(0.3));
    }
    
    public void startAmination() {
		parallelTransition.play();
		timer.start();
			
	}
	
    public void continueAnimation() {
    	parallelTransition.play();
    	timer.play();
    }
    
    
	public void pauseAnimation() {
		parallelTransition.pause();
		timer.pause();
	}
	
	public void resetAnimation() {
		parallelTransition.stop();
		timer.stop();
	}
	
	@FXML
	boolean isStart = false;
	public void controlPressed() {
		if( parallelTransition.getStatus() == Animation.Status.RUNNING ) {
			pauseAnimation();
		} else {
			if (!isStart) {
				startAmination();
				isStart = true;
			}
			else {
			continueAnimation();}
		}
	}
}
