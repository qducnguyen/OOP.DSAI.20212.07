package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.Rotatable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class StatisticsPanelController {

	private Simulation simul;

	private StackPane stackPane;

	@FXML
	private Label angLabel;

	@FXML
	private Label angAccLabel;

	@FXML
	private Label angVelLabel;

	@FXML
	private Label posLabel;

	@FXML
	private Label accLabel;

	@FXML
	private Label velLabel;

	@FXML
	private Label aForceLabel;

	@FXML
	private Label fForceLabel;

	@FXML
	private Label sumForceLabel;

	@FXML
	private CheckBox posCheckBox;

	@FXML
	private CheckBox accCheckBox;

	@FXML
	private CheckBox velCheckBox;

	@FXML
	private CheckBox forceCheckBox;

	@FXML
	private CheckBox sumForcesCheckBox;

	private Rectangle rec;

	private Circle cir;

	private Rectangle aArrow;

	private Rectangle fArrow;

	private Rectangle nArrow;

	@FXML
	CheckBox massCheckBox;

	@FXML
	private Label massLabel;

	@FXML
	private CheckBox valueCheckBox;

	@FXML
	private Label aArrowLabel;

	@FXML
	private Label fArrowLabel;

	@FXML
	private Label nArrowLabel;

	@FXML
	public void initialize() {

		angLabel.setText("Angular Position\n0.00 θ");
		angAccLabel.setText("Angular Accelerate\n0.00 θ/s^2");
		angVelLabel.setText("Angular Velocity\n0.00 θ/s");
		massLabel.setText(null);
		accLabel.setText("Accelerate\n0.00 m/s^2");
		velLabel.setText("Velocity\n0.00 m/s");
		posLabel.setText("Position\n0.00 m");
		aForceLabel.setText("0.00 N");
		fForceLabel.setText("0.00 N");
		sumForceLabel.setText("0.00 N");

	}

	public void init(Simulation simul, Rectangle rec, Circle cir, StackPane topStackPane) {
		setSimul(simul);
		this.rec = rec;
		this.cir = cir;
		setTopStackPane(topStackPane);
		setUpAppliedForce();
		setUpFrictionForce();
		setUpNetForce();

	}

	public void setSimul(Simulation simul) {
		this.simul = simul;

		// The visibilities of the aArrowLabel and fArrowLabel is true of the forceCheckBox is checked and the value of
		// these 2 forces is not 0
		
		this.aArrowLabel.visibleProperty().bind(
				this.forceCheckBox.selectedProperty().and(this.simul.getaForce().valueProperty().isNotEqualTo(0)));
		this.fArrowLabel.visibleProperty().bind(
				this.forceCheckBox.selectedProperty().and(this.simul.getfForce().valueProperty().isNotEqualTo(0)));
		this.nArrowLabel.visibleProperty()
				.bind(this.sumForcesCheckBox.selectedProperty().and(this.simul.isStartProperty()));

		aForceLabel.visibleProperty()
				.bind(this.valueCheckBox.selectedProperty().and(this.forceCheckBox.selectedProperty())
						.and(this.simul.getaForce().valueProperty().isNotEqualTo(0)));
		fForceLabel.visibleProperty()
				.bind(this.valueCheckBox.selectedProperty().and(this.forceCheckBox.selectedProperty())
						.and(this.simul.getfForce().valueProperty().isNotEqualTo(0)));
		sumForceLabel.visibleProperty().bind(this.valueCheckBox.selectedProperty()
				.and(this.sumForcesCheckBox.selectedProperty()).and(this.simul.isStartProperty()));

		angVelLabel.textProperty().bind(this.simul.getSysAngVel().asString("Angular Velocity\n%.2f m/s"));
		aForceLabel.textProperty().bind(this.simul.getaForce().valueProperty().asString("%.2f N"));
		fForceLabel.textProperty().bind(this.simul.getfForce().valueProperty().asString("%.2f N"));
		sumForceLabel.textProperty().bind(this.simul.getNetForce().valueProperty().asString("%.2f N"));

		this.simul.sysVelProperty().addListener((observable, oldValue, newValue) -> {
			velLabel.textProperty().bind(newValue.valueProperty().asString("Velocity\n%.2f m/s"));
		});

		accLabel.visibleProperty().bind(this.accCheckBox.selectedProperty());
		velLabel.visibleProperty().bind(this.velCheckBox.selectedProperty());
		posLabel.visibleProperty().bind(this.posCheckBox.selectedProperty());

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			// Case 1: if the simulation model doesn't contain object
			if (newValue == null) {
				// Restart
				this.posCheckBox.setSelected(false);
				this.sumForcesCheckBox.setSelected(false);
				this.massCheckBox.setSelected(false);
				this.forceCheckBox.setSelected(true);
				this.accCheckBox.setSelected(false);
				this.velCheckBox.setSelected(false);
				this.valueCheckBox.setSelected(false);

				angAccLabel.visibleProperty().unbind();
				angVelLabel.visibleProperty().unbind();
				angLabel.visibleProperty().unbind();
				angAccLabel.setVisible(false);
				angVelLabel.setVisible(false);
				angLabel.setVisible(false);

			}
			
			// Case 2: if the object in the simulation is in type of Cylinder.
			else if (newValue instanceof Rotatable) {
				angAccLabel.visibleProperty().bind(this.accCheckBox.selectedProperty());
				angVelLabel.visibleProperty().bind(this.velCheckBox.selectedProperty());
				angLabel.visibleProperty().bind(this.posCheckBox.selectedProperty());
			} 
			
			// Case 3: if the object in the simulation is in type of Cube.
			else {
				angAccLabel.visibleProperty().unbind();
				angVelLabel.visibleProperty().unbind();
				angLabel.visibleProperty().unbind();
				angAccLabel.setVisible(false);
				angVelLabel.setVisible(false);
				angLabel.setVisible(false);
			}
		});

		this.massLabel.visibleProperty()
				.bind(this.massCheckBox.selectedProperty().and(this.simul.objProperty().isNotNull()));

		this.simul.sysAccProperty().addListener((observable, oldValue, newValue) -> {
			accLabel.textProperty().bind(newValue.valueProperty().asString("Accelerate\n%.2f m/s^2"));
		});

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			this.massLabel.setText(null);
			
			if (newValue != null) {

				ObservableStringValue posString = Bindings.createStringBinding(
						() -> "Position\n" + String.format("%.2f", this.simul.getObj().getPos()) + " m",
						this.simul.getObj().posProperty());
				posLabel.textProperty().bind(posString);

				this.massLabel.setText(this.simul.getObj().getMass() + " kg");
				this.massLabel.toFront();

				if (newValue instanceof Cylinder) {

					ObservableStringValue angPosString = Bindings.createStringBinding(
							() -> "Angular Position\n"
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngle()) + " θ",
							((Cylinder) this.simul.getObj()).angleProperty());
					angLabel.textProperty().bind(angPosString);

					ObservableStringValue angVelString = Bindings.createStringBinding(
							() -> "Angular Velocity\n"
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngVel()) + " θ/s",
							((Cylinder) this.simul.getObj()).angVelProperty());
					angVelLabel.textProperty().bind(angVelString);

					ObservableStringValue angAccString = Bindings.createStringBinding(
							() -> "Angular Accelerate\n"
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngAcc()) + " θ/s^2",
							((Cylinder) this.simul.getObj()).angAccProperty());
					angAccLabel.textProperty().bind(angAccString);
				}
			} else {
				posLabel.textProperty().unbind();
				posLabel.setText("Position\n0.00 m");
			}
		});

	};

	private void setUpAppliedForce() {
		// Setup Arrow

		aArrow = new Rectangle(200, 50);
		aArrow.visibleProperty().bind(this.forceCheckBox.selectedProperty());
		StackPane.setAlignment(aArrow, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(aArrow);
		aArrow.setFill(new ImagePattern(new Image("file:resources/images/aArrow_image.png")));
		aArrow.setStrokeWidth(0);
		aArrow.setStroke(Color.TRANSPARENT);

		// Label for arrow

		// Label aArrowLabel = new Label("Applied Force");
		StackPane.setAlignment(aArrowLabel, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(aArrowLabel);

		// Resize Arrow
		double firstWidth = aArrow.getWidth();
		Rotate rotate = new Rotate();
		rotate.setPivotX(0);
		rotate.setPivotY(aArrow.getHeight() / 2);
		aArrow.getTransforms().add(rotate);
		Translate translate = new Translate();
		aArrow.getTransforms().add(translate);
		aArrow.setWidth(0);

		aArrowLabel.translateYProperty().bind(aArrow.translateYProperty().subtract(aArrow.heightProperty().divide(2))
				.add(aArrowLabel.heightProperty().divide(2)));

		this.simul.getaForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.doubleValue() * oldValue.doubleValue() <= 0) {
				if (newValue.doubleValue() >= 0) {
					rotate.setAngle(0);

					aArrowLabel.translateXProperty().bind(
							(translate.xProperty().multiply(2)).add(aArrowLabel.widthProperty().divide(2)).add(5));

				} else {
					rotate.setAngle(180);

					aArrowLabel.translateXProperty().bind((translate.xProperty().multiply(2))
							.subtract(aArrowLabel.widthProperty().divide(2)).subtract(5));

				}
			}

			// Set fArrowLabel based on aArrowLabel
			if (newValue.doubleValue() * this.simul.getfForce().getValue() >= 0) {
				fArrowLabel.translateXProperty().unbind();
				fArrowLabel.setTranslateX(0);
				if (this.simul.getObj() instanceof Cube) {
					fArrowLabel.translateYProperty()
							.bind(((rec.heightProperty().divide(2)).subtract(fArrowLabel.heightProperty())
									.subtract(fArrow.heightProperty().divide(2))).multiply(-1));
				} else {
					fArrowLabel.translateYProperty().bind(((cir.radiusProperty()).subtract(fArrowLabel.heightProperty())
							.subtract(fArrow.heightProperty().divide(2))).multiply(-1));

				}

				fForceLabel.translateXProperty()
						.bind(fArrowLabel.translateXProperty().subtract(fArrowLabel.widthProperty().divide(2)));
				fForceLabel.translateYProperty()
						.bind(fArrowLabel.translateYProperty().add(fArrowLabel.heightProperty().multiply(0.6)));

			} else {
				// Rebind
				fArrowLabel.translateYProperty().bind(fArrow.translateYProperty()
						.subtract(fArrow.heightProperty().divide(2)).add(fArrowLabel.heightProperty().divide(2)));
				if (this.simul.getfForce().getValue() >= 0) {
					fArrowLabel.translateXProperty()
							.bind(fArrow.widthProperty().add(5).add(fArrowLabel.widthProperty().divide(2)));

				} else {
					fArrowLabel.translateXProperty().bind(
							fArrow.widthProperty().add(5).add(fArrowLabel.widthProperty().divide(2)).multiply(-1));

				}
			}

			translate.setX(firstWidth * newValue.doubleValue() / 375 / 2);
			aArrow.setWidth(firstWidth * Math.abs(newValue.doubleValue()) / 375);

		});

		// Label for arrow
		this.stackPane.getChildren().add(aForceLabel);
		StackPane.setAlignment(aForceLabel, Pos.BOTTOM_CENTER);

//		aArrow.widthProperty().addListener((observable, oldValue, newValue) -> {
		this.simul.getaForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			aForceLabel.toFront();
			double currentLabelWidth = aForceLabel.widthProperty().doubleValue();
			double currentNewValue = aArrow.getWidth();
			double currentSign = Math.signum(this.simul.getaForce().getValue());
			if (currentNewValue > currentLabelWidth * 1.3) {
				aForceLabel.translateXProperty().unbind();
				aForceLabel.translateYProperty().unbind();
				aForceLabel
						.setTranslateY(aArrow.getTranslateY() - aArrow.getHeight() / 2 + aForceLabel.getHeight() / 2);
				aForceLabel.setTranslateX(currentNewValue / 2 * currentSign - currentLabelWidth);
			} else {
				aForceLabel.translateXProperty()
						.bind(aArrowLabel.translateXProperty().subtract(aArrowLabel.widthProperty().divide(2)));
				aForceLabel.translateYProperty()
						.bind(aArrowLabel.translateYProperty().add(aArrowLabel.heightProperty().multiply(0.6)));

			}

		});

		// Position arrow in the center of the object, bring arrow and label to front
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue instanceof Cube) {
				aArrow.translateYProperty()
						.bind(rec.heightProperty().divide(2).subtract(aArrow.heightProperty().divide(2)).multiply(-1));
			} else {
				aArrow.translateYProperty()
						.bind(cir.radiusProperty().subtract(aArrow.heightProperty().divide(2)).multiply(-1));
			}

			aArrowLabel.toFront();
			aArrow.toFront();
		});

	}

	private void setUpFrictionForce() {
		fArrow = new Rectangle(200, 50);
		fArrow.visibleProperty().bind(this.forceCheckBox.selectedProperty());
		StackPane.setAlignment(fArrow, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(fArrow);
		fArrow.setFill(new ImagePattern(new Image("file:resources/images/fArrow_image.png")));
		fArrow.setStrokeWidth(0);
		StackPane.setAlignment(fArrowLabel, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(fArrowLabel);

		// Resize Arrow
		double firstWidth = fArrow.getWidth();
		Rotate rotate = new Rotate();
		rotate.setPivotX(0);
		rotate.setPivotY(fArrow.getHeight() / 2);
		fArrow.getTransforms().add(rotate);
		Translate translate = new Translate();
		fArrow.getTransforms().add(translate);
		fArrow.setWidth(0);

		fArrowLabel.translateYProperty().bind(fArrow.translateYProperty().subtract(fArrow.heightProperty().divide(2))
				.add(fArrowLabel.heightProperty().divide(2)));

		this.simul.getfForce().valueProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.doubleValue() * oldValue.doubleValue() <= 0) {
				if (newValue.doubleValue() >= 0) {
					rotate.setAngle(0);

					fArrowLabel.translateXProperty().bind(
							(translate.xProperty().multiply(2)).add(fArrowLabel.widthProperty().divide(2)).add(5));

					// Rebind
					fArrowLabel.translateYProperty().bind(fArrow.translateYProperty()
							.subtract(fArrow.heightProperty().divide(2)).add(fArrowLabel.heightProperty().divide(2)));

				} else {
					rotate.setAngle(180);

					fArrowLabel.translateXProperty().bind((translate.xProperty().multiply(2))
							.subtract(fArrowLabel.widthProperty().divide(2)).subtract(5));

					// Rebind
					fArrowLabel.translateYProperty().bind(fArrow.translateYProperty()
							.subtract(fArrow.heightProperty().divide(2)).add(fArrowLabel.heightProperty().divide(2)));
				}
			}

			translate.setX(firstWidth * newValue.doubleValue() / 375 / 2);
			fArrow.setWidth(firstWidth * Math.abs(newValue.doubleValue()) / 375);

		});

		// Label for arrow
		// Bind nforceLabel to arrow
		this.stackPane.getChildren().add(fForceLabel);
		StackPane.setAlignment(fForceLabel, Pos.BOTTOM_CENTER);

		this.simul.getfForce().valueProperty().addListener((observable, oldValue, newValue) -> {

			double currentLabelWidth = fForceLabel.widthProperty().doubleValue();
			double currentNewValue = fArrow.getWidth();
			double currentSign = Math.signum(this.simul.getfForce().getValue());
			fForceLabel.toFront();
			if (currentNewValue > currentLabelWidth * 1.25) {
				fForceLabel.translateXProperty().unbind();
				fForceLabel.translateYProperty().unbind();

				fForceLabel
						.setTranslateY(fArrow.getTranslateY() - fArrow.getHeight() / 2 + fForceLabel.getHeight() / 2);
				fForceLabel.setTranslateX(currentNewValue / 2 * currentSign - currentLabelWidth);
			} else {

				fForceLabel.translateXProperty()
						.bind(fArrowLabel.translateXProperty().subtract(fArrowLabel.widthProperty().divide(2)));
				fForceLabel.translateYProperty()
						.bind(fArrowLabel.translateYProperty().add(fArrowLabel.heightProperty().multiply(0.6)));
			}

		});

		// Position arrow in the center of the object, bring arrow and label to front
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue instanceof Cube) {
				fArrow.translateYProperty()
						.bind(rec.heightProperty().divide(2).subtract(fArrow.heightProperty().divide(2)).multiply(-1));
			} else {
				fArrow.translateYProperty()
						.bind(cir.radiusProperty().subtract(fArrow.heightProperty().divide(2)).multiply(-1));
			}

			fArrowLabel.toFront();
			fArrow.toFront();
		});

	};

	
	private void setUpNetForce() {
		nArrow = new Rectangle(200, 50);
		nArrow.visibleProperty().bind(this.sumForcesCheckBox.selectedProperty());
		StackPane.setAlignment(nArrow, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(nArrow);
		nArrow.setFill(new ImagePattern(new Image("file:resources/images/sumArrow_image.png")));

		nArrow.setStrokeWidth(0);

		// Label for arrow
		StackPane.setAlignment(nArrowLabel, Pos.BOTTOM_CENTER);
		this.stackPane.getChildren().add(nArrowLabel);

		// Resize Arrow
		double firstWidth = nArrow.getWidth();
		Rotate rotate = new Rotate();
		rotate.setPivotX(0);
		rotate.setPivotY(nArrow.getHeight() / 2);
		nArrow.getTransforms().add(rotate);
		Translate translate = new Translate();
		nArrow.getTransforms().add(translate);
		nArrow.setWidth(0);

		this.simul.getNetForce().valueProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.doubleValue() >= 0) {
				rotate.setAngle(0);
			} else {
				rotate.setAngle(180);
			}
			translate.setX(firstWidth * newValue.doubleValue() / 375 / 2);
			nArrow.setWidth(firstWidth * Math.abs(newValue.doubleValue()) / 375);

		});

		// Bind nforceLabel to arrow

		this.stackPane.getChildren().add(sumForceLabel);
		StackPane.setAlignment(sumForceLabel, Pos.BOTTOM_CENTER);

		nArrow.widthProperty().addListener((observable, oldValue, newValue) -> {
			sumForceLabel
					.setTranslateY(nArrow.getTranslateY() - nArrow.getHeight() / 2 + sumForceLabel.getHeight() / 2);
			double currentLabelWidth = sumForceLabel.widthProperty().doubleValue();
			double currentNewValue = newValue.doubleValue();
			double currentSign = Math.signum(this.simul.getNetForce().getValue());
			if (currentNewValue > currentLabelWidth * 1.5) {
				sumForceLabel.setTranslateX(currentNewValue / 2 * currentSign - currentLabelWidth);
			} else {
				if (currentSign > 0) {
					sumForceLabel.setTranslateX(newValue.doubleValue() - currentLabelWidth / 2);
				} else {
					sumForceLabel.setTranslateX(-newValue.doubleValue() - 3 * currentLabelWidth / 2);
				}
			}

			sumForceLabel.toFront();

		});

		// Position arrow in the center of the object, bring arrow and label to front
		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue instanceof Cube) {
				nArrow.translateYProperty()
						.bind(rec.heightProperty().subtract(nArrow.heightProperty().divide(2)).multiply(-1));
			} else {
				nArrow.translateYProperty().bind(
						cir.radiusProperty().multiply(2).subtract(nArrow.heightProperty().divide(2)).multiply(-1));
			}

			nArrowLabel.toFront();
			nArrow.toFront();
		});

		nArrowLabel.translateYProperty().bind(nArrow.translateYProperty().subtract(nArrowLabel.heightProperty())
				.subtract(nArrow.heightProperty().divide(2)));

	};

	public void setTopStackPane(StackPane topStackPane) {
		this.stackPane = topStackPane;
		
		StackPane.setAlignment(this.massLabel, Pos.BOTTOM_CENTER);
		StackPane.setMargin(this.massLabel, new Insets(0, 0, 5, 0));
		this.stackPane.getChildren().add(this.massLabel);

		StackPane.setAlignment(this.velLabel, Pos.TOP_CENTER);
		StackPane.setAlignment(this.accLabel, Pos.TOP_CENTER);
		StackPane.setAlignment(this.posLabel, Pos.TOP_CENTER);
		StackPane.setAlignment(this.angVelLabel, Pos.TOP_CENTER);
		StackPane.setAlignment(this.angAccLabel, Pos.TOP_CENTER);
		StackPane.setAlignment(this.angLabel, Pos.TOP_CENTER);

		StackPane.setMargin(this.velLabel, new Insets(50, 50, 0, 0));
		StackPane.setMargin(this.accLabel, new Insets(50, 50, 0, 0));
		StackPane.setMargin(this.posLabel, new Insets(50, 50, 0, 0));
		StackPane.setMargin(this.angVelLabel, new Insets(50, 50, 0, 0));
		StackPane.setMargin(this.angAccLabel, new Insets(50, 50, 0, 0));
		StackPane.setMargin(this.angLabel, new Insets(50, 50, 0, 0));

		// Reponsive app
		this.accLabel.translateXProperty().bind(topStackPane.widthProperty().divide(2.6).multiply(-1));
		this.angAccLabel.translateXProperty().bind(accLabel.translateXProperty());
		this.velLabel.translateXProperty().bind(this.accLabel.translateXProperty().add(this.angAccLabel.widthProperty())
				.add(this.velLabel.widthProperty().divide(2.5)));
		this.angVelLabel.translateXProperty().bind(velLabel.translateXProperty());
		this.posLabel.translateXProperty().bind(this.velLabel.translateXProperty().add(this.angVelLabel.widthProperty())
				.add(this.posLabel.widthProperty().divide(2.5)));
		this.angLabel.translateXProperty().bind(posLabel.translateXProperty());

		this.angVelLabel.translateYProperty().bind(this.velLabel.heightProperty());
		this.angAccLabel.translateYProperty().bind(this.velLabel.heightProperty());
		this.angLabel.translateYProperty().bind(this.velLabel.heightProperty());

		this.stackPane.getChildren().add(this.velLabel);
		this.stackPane.getChildren().add(this.accLabel);
		this.stackPane.getChildren().add(this.posLabel);
		this.stackPane.getChildren().add(this.angVelLabel);
		this.stackPane.getChildren().add(this.angAccLabel);
		this.stackPane.getChildren().add(this.angLabel);

	}

}
