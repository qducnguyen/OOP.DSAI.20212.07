package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.utils.GameAnimationTimer;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AnimationController {
	
	private final int BACKGROUND_WIDTH = 2000;
	private final float ratioTwoBackGround = 20.0f;
	private final int numDuration = 120000;
	private final double acce = 0.05;
	

	private GameAnimationTimer timer;
	private Simulation simul;

	private ParallelTransition parallelTransition;
	
	public ParallelTransition getParallelTransition() {
		return parallelTransition;
	}

	@FXML
	private StackPane topStackPane;
	
    public StackPane getTopStackPane() {
		return topStackPane;
	}
    
    @FXML
    private StackPane downStackPane;
    
    public StackPane getDownStackPane() {
    	return downStackPane;
    }

	@FXML
    private ImageView backGroundMiddleUp;

    @FXML
    private ImageView backGroundRightUp;

    @FXML
    private ImageView backGroundMiddleDown;

    @FXML
    private ImageView backGroundRightDown;

    @FXML
	public void initialize()  {
    	
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

		 
		 parallelTransition.setRate(0);
		
		
        // Responsive app
        backGroundRightUp.fitHeightProperty().bind(topStackPane.heightProperty());
        backGroundMiddleUp.fitHeightProperty().bind(topStackPane.heightProperty());
        backGroundRightDown.fitHeightProperty().bind(downStackPane.heightProperty());   
        backGroundMiddleDown.fitHeightProperty().bind(downStackPane.heightProperty());   
    }
    
    
    public void setSim(Simulation sim) {
		this.simul = sim;
		
		timer = new GameAnimationTimer() {
            @Override
            public void tick(float secondsSinceLastFrame) {
            	if(simul.getObj() != null) {
        		simul.getObj().setAcc(acce);
            	simul.getObj().updateVel(secondsSinceLastFrame);}
            	else {
            		System.out.println("There is something wrong ...");
            	}
            }
        };
        
        this.simul.sysVelProperty().addListener(
        		(observable, oldValue, newValue) -> 
        		{
        			parallelTransition.rateProperty().bind(newValue.valueProperty().multiply(0.5));
        		});
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
		parallelTransition.jumpTo(Duration.ZERO);
		parallelTransition.stop();
		timer.stop();
	}
	
}
