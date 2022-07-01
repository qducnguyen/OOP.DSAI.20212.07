package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.vector.FrictionForce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class ForcePanelController {

	private Simulation simul;

	@FXML
	private StackPane forcePanel;

	@FXML
	private TextField forceTextField;

	@FXML
	public void initialize() {
		forceTextField.setDisable(true);
	}

	public void setSimul(Simulation simul) {
		this.simul = simul;

		// UPDATE: Add event to link to calculate Acc after aForce change when user
		// enter or unfocusing
		// UPDATE: Disable and Set 0 when the object is null, enable when object is not
		// null, auto start the simulation when value != 0
		// TODO: Unfocus forceTextField when click outside the textfield

		

//		fForceListener();
		netForceListener();
		
		this.simul.getaForce().valueProperty().addListener(observable -> {
			try {
				 System.out.println("Applied Force Changed.");
				((FrictionForce) this.simul.getfForce()).updateFrictionForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		
		this.simul.getaForce().valueProperty().addListener(
				(observable, oldValue, newValue) -> 
				{
//					
//					fForceListener();
//					netForceListener();
					/*
					this.simul.getObj().updateAcc(this.simul.getNetForce());
					
					//TODO: update in general
					if (simul.getObj() instanceof Cylinder) {
						// BUG: .......
						try {
							((Cylinder) this.simul.getObj()).updateAngAcc(this.simul.getfForce());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					*/
					
					
					if (!this.simul.getIsStart() && newValue.doubleValue() != 0.0)  // newValue.doubleValue() != 0: Prevent auto start when force == 0
						{ 
						this.simul.start();
					}
					else if (this.simul.getIsPause()) {
						this.simul.conti();
					}
		
					
					forceTextField.getParent().requestFocus();
					
				}
			);
		
		this.simul.objProperty().addListener(
				(observable, oldValue, newValue) -> {
//		            this.simul.setObject(newValue);
					if(newValue == null) {
						forceTextField.setDisable(true);
						forceTextField.setText("0");
						this.simul.setaForce(0);
					}
					else {
			            forceTextField.setDisable(false);
			            ((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
//			            fForceListener();
//			            netForceListener();
			            
			        	this.simul.getObj().velProperty().valueProperty().addListener(
								(observable1, oldValue1, newValue1) -> 
								{	
									((FrictionForce) this.simul.getfForce()).updateFrictionForce();
								}
						);
			            
					}
				}
			);
		
	
		// Unfocus forceTextField
		forceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				this.simul.setaForce(Integer.parseInt(forceTextField.getText()));
				System.out.println(
						"Current Force Value " + this.simul.getaForce().getValue() + " from Unfocus force Text");
			}
		});

	}

	@FXML
	void forceTextFieldOnAction(ActionEvent event) {
		this.simul.getaForce().setValue((double) Integer.parseInt(forceTextField.getText()));
		System.out.println(
				"Current Applied Force Value " + this.simul.getaForce().getValue() + " from On Action force Text");
	}

	public void fForceListener() {
		try {
			this.simul.getaForce().valueProperty().addListener(observable -> {
				try {
					 System.out.println("Applied Force Changed.");
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			this.simul.getObj().massProperty().addListener(observable -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			this.simul.getObj().velProperty().valueProperty().addListener(
					(observable, oldValue, newValue) -> 
					{
						if (newValue.doubleValue() * oldValue.doubleValue() < 0) {
							System.out.print("Velocity Changed Inside.");
							((FrictionForce) this.simul.getfForce()).updateFrictionForce();
						}
					}
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void netForceListener() {
		this.simul.getaForce().valueProperty().addListener(observable -> {
			try {
				this.simul.updateNetForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		this.simul.getfForce().valueProperty().addListener(observable -> {
			try {
				this.simul.updateNetForce();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
