package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.object.Rotatable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class StatisticsPanelController {

	private Simulation simul;

	private StackPane topStackPane;
	private StackPane downStackPane;

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
	private Label massLabel;

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
	private CheckBox aForceCheckBox;

	@FXML
	private CheckBox fForceCheckBox;

	@FXML
	private CheckBox sumForceCheckBox;

	@FXML
	CheckBox massCheckBox;

	@FXML
	CheckBox appliedForceCheckBox;

	@FXML
	CheckBox frictionForceCheckBox;

	@FXML
	CheckBox netForceCheckBox;

	@FXML
    private ImageView rightNetForce;

    @FXML
    private ImageView rightAppliedForce;

    @FXML
    private ImageView rightFrictionForce;

    @FXML
    private ImageView leftNetForce;

    @FXML
    private ImageView leftAppliedForce;

    @FXML
    private ImageView leftFrictionForce;

	@FXML
	public void initialize() {
		

		angLabel.setText("Current Angle Position  : 0.00 *");
		angAccLabel.setText("Current Angular Accelerate: 0.00 */s^2");
		angVelLabel.setText("Current Angular Velocity: 0.00 */s");

		// Default 3 CheckBox above are invisible and disable
		setDisableThreeAngCheckBoxs(true);

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

		aForceLabel.visibleProperty().bind(this.aForceCheckBox.selectedProperty());
		fForceLabel.visibleProperty().bind(this.fForceCheckBox.selectedProperty());
		sumForceLabel.visibleProperty().bind(this.sumForceCheckBox.selectedProperty());

		this.massLabel.visibleProperty().bind(this.massCheckBox.selectedProperty());

		// this.simul.objProperty().addListener((obser));

	}

	public void setTopStackPane(StackPane topStackPane){
		this.topStackPane = topStackPane;
		StackPane.setAlignment(this.massLabel, Pos.BOTTOM_CENTER);
		

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null){
				System.out.println("Null Object in statistics panel controller class");
			}
			else if (newValue instanceof Cylinder){
				double bottom_value = ((Cylinder)this.simul.getObj()).getRadius() * 2 * this.downStackPane.getHeight(); 
				StackPane.setMargin(this.massLabel, new Insets(0, 0, bottom_value, 0));
			}
			else if (newValue instanceof Cube){
				double bottom_value = ((Cube)this.simul.getObj()).getSize() * this.downStackPane.getHeight() * 2;
				StackPane.setMargin(this.massLabel, new Insets(0, 0, bottom_value, 0));
			}
		});

		this.topStackPane.getChildren().add(this.massLabel);
		setupVector();
		updateVector();
	}


	
	public void setDownStackPane(StackPane downStackPane){
		this.downStackPane = downStackPane;
	}

	public void setSimul(Simulation simul) {
		this.simul = simul;

		angVelLabel.textProperty().bind(this.simul.getSysAngVel().asString("Current Angular Velocity : %.2f m/s"));

		this.simul.sysVelProperty().addListener(
				(observable, oldValue, newValue) -> {
					velLabel.textProperty().bind(newValue.valueProperty().asString("Current Velocity : %.2f m/s"));
					System.out.println(observable);
				});

		this.simul.objProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (newValue instanceof Cylinder) {
						setDisableThreeAngCheckBoxs(false);
					} else {
						setDisableThreeAngCheckBoxs(true);
					}
				});

		this.simul.sysAccProperty().addListener(
				(observable, oldValue, newValue) -> {
					accLabel.textProperty().bind(newValue.valueProperty().asString("Current Accelerate : %.2f m/s^2"));
				});

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.simul.setObject(newValue);

				ObservableStringValue posString = Bindings.createStringBinding(
						() -> "Current Position : " + String.format("%.2f", this.simul.getObj().getPos()) + " m",
						this.simul.getObj().posProperty());
				posLabel.textProperty().bind(posString);

				if (newValue instanceof Cylinder) {

					ObservableStringValue angPosString = Bindings.createStringBinding(
							() -> "Current Angle : "
									+ String.format("%.2f", ((Cylinder) this.simul.getObj()).getAngle()) + " *",
							((Cylinder) this.simul.getObj()).angleProperty());
					angLabel.textProperty().bind(angPosString);

					// ObservableStringValue angVelString = Bindings.createStringBinding(() ->
					// "Current Angular Velocity : " + ((Cylinder) this.simul.getObj()).getAngVel()
					// + " */s", ((Cylinder) this.simul.getObj()).angVelProperty());
					// angVelLabel.textProperty().bind(angVelString);

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

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null){
				System.err.println("Something wrong here");
			}
			else {
				this.massLabel.setText(this.simul.getObj().getMass() + " kg");
			}
		});
	};

	private void setDisableThreeAngCheckBoxs(boolean isAble) {
		angCheckBox.setDisable(isAble);
		angCheckBox.setVisible(!isAble);

		angAccCheckBox.setDisable(isAble);
		angAccCheckBox.setVisible(!isAble);

		angVelCheckBox.setDisable(isAble);
		angVelCheckBox.setVisible(!isAble);
	}

	public void setupVector(){
		
		this.topStackPane.getChildren().add(this.rightAppliedForce);
		this.topStackPane.getChildren().add(this.rightFrictionForce);
		this.topStackPane.getChildren().add(this.rightNetForce);
		
		this.topStackPane.getChildren().add(this.leftAppliedForce);
		this.topStackPane.getChildren().add(this.leftFrictionForce);
		this.topStackPane.getChildren().add(this.leftNetForce);

		StackPane.setAlignment(this.rightNetForce, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(this.rightAppliedForce, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(this.rightFrictionForce, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(this.leftNetForce, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(this.leftAppliedForce, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(this.leftFrictionForce, Pos.BOTTOM_CENTER);
	}

	public void updateVector(){
		this.simul.getaForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			// this.rightAppliedForce.setFitWidth((double) newValue);
			this.rightAppliedForce.fitWidthProperty().bind(this.topStackPane.heightProperty().multiply(Math.abs((double) newValue * 0.01)));
			this.leftAppliedForce.fitWidthProperty().bind(this.topStackPane.heightProperty().multiply(Math.abs((double) newValue * 0.01)));
			// this.leftAppliedForce.setFitWidth((double) newValue);
			
			double insetValue = this.rightAppliedForce.getFitWidth();
			MainObject obj = this.simul.getObj();
			if (obj instanceof Cylinder){
				
			}
			StackPane.setMargin(this.rightAppliedForce, new Insets(0, 0, 0, insetValue));
			StackPane.setMargin(this.leftAppliedForce, new Insets(0, insetValue, 0, 0));

			System.out.println(this.aForceCheckBox.isSelected());
			if ((double) newValue > 0 && this.aForceCheckBox.isSelected()){
				this.rightAppliedForce.setVisible(true);
				this.leftAppliedForce.setVisible(false);
			}
			else if ((double) newValue < 0 && this.aForceCheckBox.isSelected()){
				this.rightAppliedForce.setVisible(false);
				this.leftAppliedForce.setVisible(true);
			}
			else if ((double) newValue == 0 && this.aForceCheckBox.isSelected()){
				
			}
			
		});
		
		this.simul.getfForce().valueProperty().addListener((observable, oldValue, newValue) -> {

			this.rightFrictionForce.fitWidthProperty().bind(this.topStackPane.heightProperty().multiply(Math.abs((double) newValue * 0.01)));
			this.leftFrictionForce.fitWidthProperty().bind(this.topStackPane.heightProperty().multiply(Math.abs((double) newValue * 0.01)));


			if ((double) newValue > 0 && this.fForceCheckBox.isSelected()){
				this.rightFrictionForce.setVisible(true);
				this.leftFrictionForce.setVisible(false);
			}
			else if ((double) newValue < 0 && this.fForceCheckBox.isSelected()){
				this.rightFrictionForce.setVisible(false);
				this.leftFrictionForce.setVisible(true);
			}

			double insetValue = this.rightAppliedForce.getFitWidth();
			// this.rightFrictionForce.setFitWidth((double) newValue);
			// this.leftFrictionForce.setFitWidth((double) newValue);

			// double insetValue = -(double) newValue;

			StackPane.setMargin(this.rightFrictionForce, new Insets(0, 0, 0, insetValue));
			StackPane.setMargin(this.leftFrictionForce, new Insets(0, insetValue, 0, 0));

			// if ((double) newValue > 0 && this.fForceCheckBox.isSelected()){
			// 	this.rightFrictionForce.setVisible(true);
			// 	this.leftFrictionForce.setVisible(false);
			// }
			// else if ((double) newValue < 0 && this.fForceCheckBox.isSelected()){
			// 	this.rightFrictionForce.setVisible(false);
			// 	this.leftFrictionForce.setVisible(true);
			// }
		});

		this.simul.getNetForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			// this.rightNetForce.setFitWidth((double) newValue);
			// this.leftNetForce.setFitWidth((double) newValue);
			System.out.println("Test new value for net force: " + (double) newValue);
			this.rightNetForce.fitWidthProperty().bind(this.topStackPane.heightProperty().multiply(Math.abs((double) newValue * 0.01)));
			this.leftNetForce.fitWidthProperty().bind(this.topStackPane.heightProperty().multiply(Math.abs((double) newValue * 0.01)));

			double insetValue = this.rightNetForce.getFitHeight();
			// double insetValue = (double) newValue;

			StackPane.setMargin(this.rightNetForce, new Insets(0, 0, 0, insetValue));
			StackPane.setMargin(this.leftNetForce, new Insets(0, insetValue, 0, 0));

			if ((double) newValue > 0 && this.netForceCheckBox.isSelected()){
				this.rightNetForce.setVisible(true);
				this.leftNetForce.setVisible(false);
			}
			else if ((double) newValue < 0 && this.netForceCheckBox.isSelected()){
				this.rightNetForce.setVisible(false);
				this.leftNetForce.setVisible(true);
			}
		});
	}

	public void updateDirectionByForce(){
		this.simul.getaForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			if ((double) newValue > 0){
				this.rightAppliedForce.setVisible(true);
				this.leftAppliedForce.setVisible(false);
			}
			else if ((double) newValue < 0){
				this.rightAppliedForce.setVisible(false);
				this.leftAppliedForce.setVisible(true);
			}
		});

		this.simul.getfForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			if ((double) newValue > 0){
				this.rightFrictionForce.setVisible(true);
				this.leftFrictionForce.setVisible(false);
			}
			else if ((double) newValue < 0){
				this.rightFrictionForce.setVisible(false);
				this.leftFrictionForce.setVisible(true);
			}
		});

		this.simul.getNetForce().valueProperty().addListener((observable, oldValue, newValue) -> {
			if ((double) newValue > 0){
				this.rightNetForce.setVisible(true);
				this.leftNetForce.setVisible(false);
			}
			else if ((double) newValue < 0){
				this.rightNetForce.setVisible(false);
				this.leftNetForce.setVisible(true);
			}
		});
	}


	@FXML
	public void frictionForceView(){
		if (this.frictionForceCheckBox.isSelected()){
			if (this.simul.getfForce().getValue() == 0){
				this.rightFrictionForce.setVisible(false);
				this.leftFrictionForce.setVisible(false);
				this.frictionForceCheckBox.setSelected(false);
			}
			else if (this.simul.getfForce().getValue() > 0){
				this.rightFrictionForce.setVisible(true);
				this.leftFrictionForce.setVisible(false);
			}
			else if (this.simul.getfForce().getValue() < 0){
				this.rightFrictionForce.setVisible(false);
				this.leftFrictionForce.setVisible(true);
			}
		}
		else {
			this.rightFrictionForce.setVisible(false);
			this.leftFrictionForce.setVisible(false);
		}
	}

	@FXML
	public void appliedForceView(){
		if (this.appliedForceCheckBox.isSelected()){
			if (this.simul.getaForce().getValue() == 0){
				this.rightAppliedForce.setVisible(false);
				this.leftAppliedForce.setVisible(false);
				this.appliedForceCheckBox.setSelected(false);
			}
			else if (this.simul.getaForce().getValue() > 0){
				this.rightAppliedForce.setVisible(true);
				this.leftAppliedForce.setVisible(false);
			}
			else if (this.simul.getaForce().getValue() < 0){
				System.out.println("Test applied force");
				this.rightAppliedForce.setVisible(false);
				this.leftAppliedForce.setVisible(true);
			}
		}
		else {
			this.rightAppliedForce.setVisible(false);
			this.leftAppliedForce.setVisible(false);
		}
	}

	@FXML
	public void netForceView(){
		if (this.netForceCheckBox.isSelected()){
			if (this.simul.getNetForce().getValue() == 0){
				this.rightNetForce.setVisible(false);
				this.leftNetForce.setVisible(false);
				this.netForceCheckBox.setSelected(false);
			}
			else if (this.simul.getNetForce().getValue() > 0){
				this.rightNetForce.setVisible(true);
				this.leftNetForce.setVisible(false);
			}
			else if (this.simul.getNetForce().getValue() < 0){
				this.rightNetForce.setVisible(false);
				this.leftNetForce.setVisible(true);
			}
		}
		else {
			this.rightNetForce.setVisible(false);
			this.leftNetForce.setVisible(false);
		}
	}
}