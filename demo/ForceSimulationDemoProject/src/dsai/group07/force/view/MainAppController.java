package dsai.group07.force.view;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MainAppController {
	
	private final int BACKGROUND_WIDTH = 2000;
	private final float ratioTwoBackGround = 5.0f;
	private final int numDuration = 50000;
	
	private Scene sce;
	
    @FXML
    private ImageView backGroundLeftUp;

    @FXML
    private ImageView backGroundRightUp;

    @FXML
    private ImageView backGroundLeftDown;

    @FXML
    private ImageView backGroundRightDown;
    


    @FXML
	public void initialize() {
		
		TranslateTransition translateTransition =
	            new TranslateTransition(Duration.millis(numDuration), backGroundLeftUp);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition.setInterpolator(Interpolator.LINEAR);
	
		TranslateTransition translateTransition2 =
            new TranslateTransition(Duration.millis(numDuration), backGroundRightUp);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition2.setInterpolator(Interpolator.LINEAR);
		
		TranslateTransition translateTransition3 =
				new TranslateTransition(Duration.millis(numDuration / ratioTwoBackGround ), backGroundLeftDown);
		translateTransition3.setFromX(0);
		translateTransition3.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition3.setInterpolator(Interpolator.LINEAR);
		TranslateTransition translateTransition4 =
				new TranslateTransition(Duration.millis(numDuration / ratioTwoBackGround), backGroundRightDown);
		translateTransition4.setFromX(0);
		translateTransition4.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition4.setInterpolator(Interpolator.LINEAR);

		ParallelTransition parallelTransitionUp = 
				new ParallelTransition( translateTransition, translateTransition2 );
		parallelTransitionUp.setCycleCount(Animation.INDEFINITE);
		
		ParallelTransition parallelTransitionDown = 
				new ParallelTransition( translateTransition3, translateTransition4 );
		parallelTransitionDown.setCycleCount(Animation.INDEFINITE);

		//
		// Sets the label of the Button based on the animation state
		//
	
		
		parallelTransitionUp.setRate(1);
		parallelTransitionDown.setRate(1);
		
		
		parallelTransitionUp.play();
		parallelTransitionDown.play();
		
		
	}
    
    public void setSce(Scene sce) {
    	this.sce = sce;
    	backGroundLeftUp.fitHeightProperty().bind(sce.heightProperty().multiply(0.7));
    	backGroundRightUp.fitHeightProperty().bind(sce.heightProperty().multiply(0.7));
    	backGroundLeftDown.fitHeightProperty().bind(sce.heightProperty().multiply(0.3));
    	backGroundRightDown.fitHeightProperty().bind(sce.heightProperty().multiply(0.3));
    }
}
