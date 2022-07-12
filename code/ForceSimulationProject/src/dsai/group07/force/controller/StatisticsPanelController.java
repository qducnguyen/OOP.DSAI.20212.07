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
	private CheckBox angCheckBox;

	@FXML
	private CheckBox angAccCheckBox;

	@FXML
	private CheckBox angVelCheckBox;

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

	private StackPane topStackPane;

	private StackPane downStackPane;

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

		angLabel.setText("Current Angle Position  : 0.00 *");
		angAccLabel.setText("Current Angular Accelerate: 0.00 */s^2");
		angVelLabel.setText("Current Angular Velocity: 0.00 */s");

		massLabel.setText("");

		setCylinderCheckBoxes(false);

		accLabel.setText("Current Accelerate : 0.00 m/s^2");
		velLabel.setText("Current Velocity : 0.00 m/s");
		posLabel.setText("Current Position : 0.00 m");
		aForceLabel.setText("Applied Force : 0 N");
		fForceLabel.setText("Friction Force : 0 N");
		sumForceLabel.setText("Net Force : 0 N");

		angLabel.visibleProperty().bind(this.angCheckBox.selectedProperty());
		angAccLabel.visibleProperty().bind(this.angAccCheckBox.selectedProperty());
		angVelLabel.visibleProperty().bind(this.angVelCheckBox.selectedProperty());

		accLabel.visibleProperty().bind(this.accCheckBox.selectedProperty());
		velLabel.visibleProperty().bind(this.velCheckBox.selectedProperty());
		posLabel.visibleProperty().bind(this.posCheckBox.selectedProperty());

		aForceLabel.visibleProperty().bind(this.valueCheckBox.selectedProperty());
		fForceLabel.visibleProperty().bind(this.valueCheckBox.selectedProperty());
		sumForceLabel.visibleProperty().bind(this.valueCheckBox.selectedProperty());

		this.massLabel.visibleProperty().bind(this.massCheckBox.selectedProperty());
	}

	public void init(Simulation simul, Rectangle rec, Circle cir, StackPane topStackPane, StackPane downStackPane) {
		setSimul(simul);
		this.rec = rec;
		this.cir = cir;
		this.stackPane = topStackPane;
		this.downStackPane = downStackPane;
		setUpAppliedForce();
		setUpFrictionForce();
		setUpNetForce();

	}

	public void setSimul(Simulation simul) {
		this.simul = simul;

		this.aArrowLabel.visibleProperty().bind(
				this.forceCheckBox.selectedProperty().and(this.simul.getaForce().valueProperty().isNotEqualTo(0)));
		this.fArrowLabel.visibleProperty().bind(
				this.forceCheckBox.selectedProperty().and(this.simul.getfForce().valueProperty().isNotEqualTo(0)));
		this.nArrowLabel.visibleProperty().bind(this.sumForcesCheckBox.selectedProperty()
				.and(this.simul.getNetForce().valueProperty().isNotEqualTo(0)));

		angVelLabel.textProperty().bind(this.simul.getSysAngVel().asString("Current Angular Velocity : %.2f m/s"));

		this.simul.sysVelProperty().addListener((observable, oldValue, newValue) -> {
			velLabel.textProperty().bind(newValue.valueProperty().asString("Current Velocity : %.2f m/s"));
		});

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {

				// Restart
				this.posCheckBox.setSelected(false);
				this.angAccCheckBox.setSelected(false);
				this.angCheckBox.setSelected(false);
				this.angVelCheckBox.setSelected(false);
				this.sumForcesCheckBox.setSelected(false);
				this.angCheckBox.setSelected(false);
				this.massCheckBox.setSelected(false);
				this.forceCheckBox.setSelected(true);
				this.accCheckBox.setSelected(false);
				this.velCheckBox.setSelected(false);

				setCylinderCheckBoxes(false);
			}

			else if (newValue instanceof Rotatable) {
				setCylinderCheckBoxes(true);
			} else {
				setCylinderCheckBoxes(false);
			}
		});

		this.simul.sysAccProperty().addListener((observable, oldValue, newValue) -> {
			accLabel.textProperty().bind(newValue.valueProperty().asString("Current Accelerate : %.2f m/s^2"));
		});

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			this.massLabel.setText("");

			if (newValue != null) {
				this.simul.setObject(newValue);

				ObservableStringValue posString = Bindings.createStringBinding(
						() -> "Current Position : " + String.format("%.2f", this.simul.getObj().getPos()) + " m",
						this.simul.getObj().posProperty());
				posLabel.textProperty().bind(posString);

				this.massLabel.setText(this.simul.getObj().getMass() + " kg");

				if (newValue instanceof Cylinder) {

					ObservableStringValue angPosString = Bindings.createStringBinding(
							() -> "Current Angle : "
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngle()) + " *",
							((Cylinder) this.simul.getObj()).angleProperty());
					angLabel.textProperty().bind(angPosString);

					ObservableStringValue angVelString = Bindings.createStringBinding(
							() -> "Current Angular Velocity : "
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngVel()) + " */s",
							((Cylinder) this.simul.getObj()).angVelProperty());
					angVelLabel.textProperty().bind(angVelString);

					ObservableStringValue angAccString = Bindings.createStringBinding(
							() -> "Current Angular Accelerate : "
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngAcc()) + " */s^2",
							((Cylinder) this.simul.getObj()).angAccProperty());
					angAccLabel.textProperty().bind(angAccString);
				}
			}
		});

		ObservableStringValue aForceString = Bindings.createStringBinding(
				() -> "Current Applied Force : " + String.format("%.2f", this.simul.getaForce().getValue()) + " N",
				this.simul.getaForce().valueProperty());
		aForceLabel.textProperty().bind(aForceString);

		ObservableStringValue fForceString = Bindings.createStringBinding(
				() -> "Current Friction Force : " + String.format("%.2f", this.simul.getfForce().getValue()) + " N",
				this.simul.getfForce().valueProperty());
		fForceLabel.textProperty().bind(fForceString);

		ObservableStringValue netForceString = Bindings.createStringBinding(
				() -> "Current Net Force : " + String.format("%.2f", this.simul.getNetForce().getValue()) + " N",
				this.simul.getNetForce().valueProperty());
		sumForceLabel.textProperty().bind(netForceString);
	};

	private void setCylinderCheckBoxes(boolean isVi) {
		this.angCheckBox.setVisible(isVi);
		this.angVelCheckBox.setVisible(isVi);
		this.angAccCheckBox.setVisible(isVi);

		this.angAccCheckBox.setDisable(!isVi);
		this.angVelCheckBox.setDisable(!isVi);
		this.angCheckBox.setDisable(!isVi);
	}

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
					
					aArrowLabel.translateXProperty()
							.bind((translate.xProperty().multiply(2)).add(aArrowLabel.widthProperty().divide(2)).add(5));
					
					
				} else {
					rotate.setAngle(180);
					
					aArrowLabel.translateXProperty()
					.bind((translate.xProperty().multiply(2)).subtract(aArrowLabel.widthProperty().divide(2)).subtract(5));
					
				}
			}
			
			if (newValue.doubleValue() * this.simul.getfForce().getValue() >= 0) {
				fArrowLabel.translateXProperty().unbind();
				fArrowLabel.setTranslateX(0);
				System.out.println("Hello");
				}
			
			
			translate.setX(firstWidth * newValue.doubleValue() / 300 / 2);
			aArrow.setWidth(firstWidth * Math.abs(newValue.doubleValue()) / 300);

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
					
					fArrowLabel.translateXProperty()
							.bind((translate.xProperty().multiply(2)).add(fArrowLabel.widthProperty().divide(2)).add(5));
					
					
				} else {
					rotate.setAngle(180);
					
					fArrowLabel.translateXProperty()
					.bind((translate.xProperty().multiply(2)).subtract(fArrowLabel.widthProperty().divide(2)).subtract(5));
					
				}
			}
			
			
			
			translate.setX(firstWidth * newValue.doubleValue() / 300 / 2);
			fArrow.setWidth(firstWidth * Math.abs(newValue.doubleValue()) / 300);

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
//    				if (newValue.doubleValue() == 0) {
//    					this.nArrowLabel.setVisible(false);
//    				}
//    				else {
//    					this.nArrowLabel.setVisible(true);
//    				}

			if (newValue.doubleValue() >= 0) {
				rotate.setAngle(0);
			} else {
				rotate.setAngle(180);

			}
			translate.setX(firstWidth * newValue.doubleValue() / 300 / 2);
			nArrow.setWidth(firstWidth * Math.abs(newValue.doubleValue()) / 300);

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

		// Position Label
		nArrowLabel.translateXProperty().bind(translate.xProperty().multiply(2).add(20));
		nArrowLabel.translateYProperty().bind(nArrow.translateYProperty());

	};

	public void setTopStackPane(StackPane topStackPane) {
		this.topStackPane = topStackPane;
		StackPane.setAlignment(this.massLabel, Pos.BOTTOM_CENTER);

		StackPane.setMargin(this.massLabel, new Insets(0, 0, 5, 0));

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				this.massCheckBox.setSelected(false);
			} else if (newValue instanceof Cylinder) {
				double bottom_value = ((Cylinder) this.simul.getObj()).getRadius() * 2 * this.downStackPane.getHeight();
				StackPane.setMargin(this.massLabel, new Insets(0, 0, bottom_value, 0));

			} else if (newValue instanceof Cube) {
				double bottom_value = ((Cube) this.simul.getObj()).getSize() * this.downStackPane.getHeight() * 2;
				StackPane.setMargin(this.massLabel, new Insets(0, 0, bottom_value, 0));
			}

			this.massLabel.toFront();
		});

		this.topStackPane.getChildren().add(this.massLabel);
	}

	public void setDownStackPane(StackPane downStackPane) {
		this.downStackPane = downStackPane;
	}

}
