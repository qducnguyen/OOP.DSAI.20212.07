/*
 * AnimationController.java including: Background transition
 */

package dsai.group07.force.controller;

import dsai.group07.force.controller.utils.GameAnimationTimer;
import dsai.group07.force.model.Simulation;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AnimationController {

	private final int BACKGROUND_WIDTH = 2000;
	private final float RATIO_TWO_BACKGROUNDS = 40.0f;
	private final int NUM_DURATION = 15000;

	private GameAnimationTimer timer;
	private Simulation simul;

	// for transition
	private ParallelTransition parallelTransitionUp;
	private ParallelTransition parallelTransitionDown;

	@FXML
	private StackPane topStackPane;
	@FXML
	private StackPane downStackPane;
	@FXML
	private ImageView backGroundMiddleUp;
	@FXML
	private ImageView backGroundRightUp;
	@FXML
	private ImageView backGroundMiddleDown;
	@FXML
	private ImageView backGroundRightDown;

	public ParallelTransition getParallelTransitionUp() {
		return this.parallelTransitionUp;
	}

	public StackPane getTopStackPane() {
		return topStackPane;
	}

	public StackPane getDownStackPane() {
		return downStackPane;
	}

	public void setSim(Simulation sim) {
		this.simul = sim;

		timer = new GameAnimationTimer() {
			@Override
			public void tick(double secondsSinceLastFrame) {
				simul.applyForceInTime(secondsSinceLastFrame);
			}
		};

		// Binding rateProperty --> sysVel
		this.simul.sysVelProperty().addListener((observable, oldValue, newValue) -> {

			parallelTransitionUp.rateProperty().bind(Bindings.when(newValue.valueProperty().isEqualTo(0, 0.2))
					.then(10e-5 * 5).otherwise(newValue.valueProperty().multiply(0.05)));
			parallelTransitionDown.rateProperty().bind(Bindings.when(newValue.valueProperty().isEqualTo(0, 0.2))
					.then(10e-5 * 5).otherwise(newValue.valueProperty().multiply(0.05)));
		});
	}

	@FXML
	public void initialize() {

		// Set transition for the background
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(NUM_DURATION * RATIO_TWO_BACKGROUNDS),
				backGroundRightUp); 
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(NUM_DURATION * RATIO_TWO_BACKGROUNDS),
				backGroundMiddleUp);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition3 = new TranslateTransition(
				Duration.millis(NUM_DURATION ), backGroundMiddleDown);
		translateTransition3.setFromX(0);
		translateTransition3.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition3.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition4 = new TranslateTransition(
				Duration.millis(NUM_DURATION ), backGroundRightDown);
		translateTransition4.setFromX(0);
		translateTransition4.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition4.setInterpolator(Interpolator.LINEAR);

		parallelTransitionUp = new ParallelTransition(translateTransition, translateTransition2);
		parallelTransitionUp.setCycleCount(Animation.INDEFINITE);

		parallelTransitionDown = new ParallelTransition(translateTransition3, translateTransition4);
		parallelTransitionDown.setCycleCount(Animation.INDEFINITE);

		// NOTE: We also try to group two parallel transitions but there is a bug so we
		// decided to keep it like this.

		// Set rate = 0 for both parallel
		parallelTransitionUp.setRate(0.0);
		parallelTransitionDown.setRate(0.0);

		
		
		// one specific class to handle responsive application
		backGroundRightUp.fitHeightProperty().bind(topStackPane.heightProperty());
		backGroundMiddleUp.fitHeightProperty().bind(topStackPane.heightProperty());
		backGroundRightDown.fitHeightProperty().bind(downStackPane.heightProperty());
		backGroundMiddleDown.fitHeightProperty().bind(downStackPane.heightProperty());
	}

	public void startAmination() {
		parallelTransitionUp.play();
		parallelTransitionDown.play();
		timer.start();
	}

	public void continueAnimation() {
		parallelTransitionUp.play();
		parallelTransitionDown.play();
		timer.play();
	}

	public void pauseAnimation() {
		parallelTransitionUp.pause();
		parallelTransitionDown.pause();
		timer.pause();
	}

	public void resetAnimation() {
		parallelTransitionUp.jumpTo(Duration.ZERO);
		parallelTransitionDown.jumpTo(Duration.ZERO);
		parallelTransitionUp.stop();
		parallelTransitionDown.stop();
		timer.stop();
	}

}
