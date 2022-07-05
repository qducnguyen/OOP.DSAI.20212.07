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
	private final float ratioTwoBackGround = 20.0f;
	private final int numDuration = 120000;

	private GameAnimationTimer timer;
	private Simulation simul;

	// private ParallelTransition parallelTransition;
	private ParallelTransition parallelTransitionUp;
	private ParallelTransition parallelTransitionDown;

	public ParallelTransition getParallelTransitionUp() {
		return this.parallelTransitionUp;
	}

	public ParallelTransition getParallelTransitionDown() {
		return this.parallelTransitionDown;
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
	public void initialize() {

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(numDuration),
				backGroundRightUp);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(numDuration),
				backGroundMiddleUp);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition3 = new TranslateTransition(
				Duration.millis(numDuration / ratioTwoBackGround), backGroundMiddleDown);
		translateTransition3.setFromX(0);
		translateTransition3.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition3.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition4 = new TranslateTransition(
				Duration.millis(numDuration / ratioTwoBackGround), backGroundRightDown);
		translateTransition4.setFromX(0);
		translateTransition4.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition4.setInterpolator(Interpolator.LINEAR);

		parallelTransitionUp = new ParallelTransition(translateTransition, translateTransition2);
		parallelTransitionUp.setCycleCount(Animation.INDEFINITE);

		parallelTransitionDown = new ParallelTransition(translateTransition3, translateTransition4);
		parallelTransitionDown.setCycleCount(Animation.INDEFINITE);

		parallelTransitionUp.setRate(0.0);
		parallelTransitionDown.setRate(0.0);

		// Responsive Background (Binding two parts to scene)
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
				if (simul != null) {
					simul.applyForceInTime(secondsSinceLastFrame);
					//
					// System.out.println("aForce " + simul.getaForce().getValue());
					// System.out.println("fForce " + simul.getfForce().getValue());
					// System.out.println("netForce " + simul.getNetForce().getValue());
					//
					// System.out.println("acc " + simul.getObj().accProperty().getValue());
					// System.out.println("vel " + simul.getObj().velProperty().getValue());
					// System.out.println("Pos " + simul.getObj().getPos());
					//
					// if (simul.getObj() instanceof Cylinder) {
					// System.out.println("AngAcc " + ((Cylinder) simul.getObj()).getAngAcc());
					// System.out.println("AngVel " + ((Cylinder) simul.getObj()).getAngVel());
					// System.out.println("Angle " + ((Cylinder) simul.getObj()).getAngle());
					// }
				} else {
					System.out.println("There is something wrong ...");
				}
			}
		};

		// Binding rateProperty --> sysVel
		this.simul.sysVelProperty().addListener(
				(observable, oldValue, newValue) -> {
					parallelTransitionUp.rateProperty().bind(Bindings.when(newValue.valueProperty().isEqualTo(0, 10e-4))
							.then(10e-5).otherwise(newValue.valueProperty().multiply(0.5)));
					parallelTransitionDown.rateProperty()
							.bind(Bindings.when(newValue.valueProperty().isEqualTo(0, 10e-4)).then(10e-5)
									.otherwise(newValue.valueProperty().multiply(0.5)));
					// parallelTransitionDown.rateProperty().bind(newValue.valueProperty().multiply(0.5));
					// parallelTransitionUp.rateProperty().bind(newValue.valueProperty().multiply(0.5));
				});
		//
		// parallelTransitionUp.rateProperty().addListener((observable, oldValue,
		// newValue) -> {
		// System.out.println(newValue.doubleValue());
		// });
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
		// parallelTransitionUp.setRate(10e-4);
		// parallelTransitionDown.setRate(10e-4);
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
