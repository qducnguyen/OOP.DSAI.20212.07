package dsai.group07.force.controller;

import java.util.Optional;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.object.Cube;
import dsai.group07.force.model.object.Cylinder;
import dsai.group07.force.model.object.MainObject;
import dsai.group07.force.model.vector.FrictionForce;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;

public class ObjectPanelController {

	private Simulation simul;

	private StackPane topStackPane;
	private StackPane downStackPane;

	// For rotation
	private RotateTransition cirRotate;

	// For drag and drop
	private final DataFormat cirFormat = new DataFormat("dsai.group07.force.circle");
	private final DataFormat recFormat = new DataFormat("dsai.group07.force.rec");

	@FXML
	private GridPane gridPaneObjectContainer;

	@FXML
	private Rectangle rec;

	@FXML
	private Circle cir;

	public Rectangle getRec() {
		return rec;
	}

	public Circle getCir() {
		return cir;
	}

	public void setDownStackPane(StackPane downStackPane) {
		this.downStackPane = downStackPane;

		// TODO: more binding for circle

		cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
		rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
		rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));

	}

	public void setTopStackPane(StackPane topStackPane) {
		this.topStackPane = topStackPane;

		this.topStackPane.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();

			if (db.hasContent(cirFormat)) {
				StackPane.setAlignment(cir, Pos.BOTTOM_CENTER);

				// TODO: another view for drag and drop ...
				if (topStackPane.getChildren().contains(rec)) {
					gridPaneObjectContainer.add(rec, 0, 0);
					this.rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
					this.rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));

				}

				topStackPane.getChildren().add(cir);

				try {
					// For Cylinder
					cylinderInput();
				} catch (Exception e) {
					e.printStackTrace();
				}

				event.setDropCompleted(true);

			}

			else if (db.hasContent(recFormat)) {

				StackPane.setAlignment(rec, Pos.BOTTOM_CENTER);

				if (topStackPane.getChildren().contains(cir)) {
					gridPaneObjectContainer.add(cir, 1, 0);
					cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
				}

				topStackPane.getChildren().add(rec);
				try {
					// When object is a Cube
					cubeInput();
				} catch (Exception e) {
					e.printStackTrace();
				}

				event.setDropCompleted(true);

			}
		});

		this.topStackPane.setOnDragOver(event -> {
			Dragboard db = event.getDragboard();
			if (db.hasContent(cirFormat) && cir.getParent() != topStackPane) {

				event.acceptTransferModes(TransferMode.MOVE);

			} else if (db.hasContent(recFormat) && this.rec.getParent() != topStackPane)
				event.acceptTransferModes(TransferMode.MOVE);
		});

	}

	private EventDragDetected cirOnDragDectected = new EventDragDetected(cirFormat);
	private EventDragDetected recOnDragDectected = new EventDragDetected(recFormat);

	@FXML
	public void initialize() {

		// Setting image for the Circle
		Image cirImage = new Image("file:resources/images/plus-sign.png");

		cir.setFill(new ImagePattern(cirImage));

		// Drag and drop
		cir.setOnDragDetected(cirOnDragDectected);
		rec.setOnDragDetected(recOnDragDectected);

		gridPaneObjectContainer.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();

			if (db.hasContent(cirFormat)) {
				// TODO: another view for drag and drop ...
				cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
				gridPaneObjectContainer.add(cir, 1, 0);
				// model
				this.simul.setObject(null);

				event.setDropCompleted(true);
			}

			else if (db.hasContent(recFormat)) {
				rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
				rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
				gridPaneObjectContainer.add(rec, 0, 0);

				this.simul.setObject(null);

				event.setDropCompleted(true);
			}
		});

		gridPaneObjectContainer.setOnDragOver(event -> {
			Dragboard db = event.getDragboard();
			if (db.hasContent(cirFormat) && cir.getParent() != gridPaneObjectContainer) {
				event.acceptTransferModes(TransferMode.MOVE);
			} else if (db.hasContent(recFormat) && rec.getParent() != gridPaneObjectContainer)
				event.acceptTransferModes(TransferMode.MOVE);
		});
	}

	public void setSimul(Simulation simul) {
		this.simul = simul;

		this.simul.objProperty().addListener(
				// TODO: unbind getSysAngAcc ..
				(observable, oldValue, newValue) -> {
//					this.simul.setObject(newValue);
					if (newValue == null) {
						System.out.println("Null Object");
//        		    	this.simul.getaForce().setValue(0);
					} else if (newValue instanceof Cylinder) {
						System.out.println("Cylinder Time.......");
						((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
						// this.simul.getSysAngAcc().bind(((Cylinder) newValue).angAccProperty());
//        		    	objectListener();
					} else {
						System.out.println("Cube Time......");
						((FrictionForce) this.simul.getfForce()).setMainObj(newValue);
//        		    	objectListener();
					}

				});

		// Draggable bind to this.simul.isStartProperty()
		this.simul.isStartProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				rec.setOnDragDetected(null);
				cir.setOnDragDetected(null);

			} else {
				cir.setOnDragDetected(cirOnDragDectected);
				rec.setOnDragDetected(recOnDragDectected);

			}
		});

		setUpCircleRotation();
	}

	public void resetObjectPosition() {
		gridPaneObjectContainer.getChildren().clear();
		cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(0.3));
		rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
		rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(0.6));
		gridPaneObjectContainer.add(rec, 0, 0);
		gridPaneObjectContainer.add(cir, 1, 0);
	}

	private void setUpCircleRotation() {
		final int DURATION_ROTATE = 3;
		final double DEFAULT_ROTATE_VEL = 10;
		this.cirRotate = new RotateTransition(Duration.seconds(DURATION_ROTATE), cir);
		this.cirRotate.setByAngle(360);
		this.cirRotate.setInterpolator(Interpolator.LINEAR);
		this.cirRotate.setCycleCount(Animation.INDEFINITE);

		this.cirRotate.setRate(0.0);

		this.simul.objProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue instanceof Cylinder) {
				this.simul.getSysAngAcc().bind(((Cylinder) newValue).angAccProperty());
				this.simul.getSysAngVel().bind(((Cylinder) newValue).angVelProperty());
			}
		});

		this.cirRotate.rateProperty().bind(this.simul.getSysAngVel().multiply(1 / DEFAULT_ROTATE_VEL)); // Change later

	}

	public void startCirAmination() {
		if (cirRotate != null && this.simul.getObj() instanceof Cylinder) {
			cirRotate.play();
		}
	}

	public void continueCirAnimation() {
		if (cirRotate != null && this.simul.getObj() instanceof Cylinder) {
			cirRotate.play();
		}
	}

	public void pauseCirAnimation() {
		if (cirRotate != null) {
			cirRotate.pause();
		}
	}

	public void resetCirAnimation() {
		if (cirRotate != null) {
			cirRotate.jumpTo(Duration.ZERO);
			cirRotate.stop();
		}
	}

	private class EventDragDetected implements EventHandler<MouseEvent> {
		private final DataFormat shapeFormat;

		public EventDragDetected(DataFormat data) {
			this.shapeFormat = data;
		}

		@Override
		public void handle(MouseEvent event) {
			Shape s = (Shape) event.getSource();
			Dragboard db = s.startDragAndDrop(TransferMode.MOVE);

			SnapshotParameters snapShotparams = new SnapshotParameters();
			snapShotparams.setFill(Color.TRANSPARENT);
			db.setDragView(s.snapshot(snapShotparams, null), event.getX(), event.getY());
			if (s instanceof Circle) {
				db.setDragViewOffsetX(event.getX() + cir.getRadius());
				db.setDragViewOffsetY(event.getY() + cir.getRadius());
			}
			ClipboardContent cc = new ClipboardContent();
			cc.put(shapeFormat, this.shapeFormat.toString());
			db.setContent(cc);
		}

	}

	public void objectListener() {
		try {
			this.simul.getObj().massProperty().addListener(observable -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			this.simul.getObj().velProperty().valueProperty().addListener(observable -> {
				try {
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cubeInput() {
		// Pair<String, String> : as a dictionary in python -> return result
		Dialog<Pair<String, String>> dialog = new Dialog<>();

		dialog.initStyle(StageStyle.UNDECORATED);

		dialog.setTitle("Input Property for Cube");
		dialog.setHeaderText("Cube property");

		// Create a new button type: OKEType, set text of that button to "OK"
		ButtonType OKEType = new ButtonType("OK", ButtonData.OK_DONE);

		// add Button OK to dialog
		dialog.getDialogPane().getButtonTypes().add(OKEType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField cubeMass = new TextField();
		cubeMass.setPromptText("Input mass for cube");
		cubeMass.textProperty().addListener(event -> {
			cubeMass.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!cubeMass.getText().isEmpty() && !cubeMass.getText().matches("\\\\d+\\\\.\\\\d+"));
		});

		TextField cubeSide = new TextField();
		cubeSide.setPromptText("Input Side-length for cube");
		cubeSide.textProperty().addListener(event -> {
			cubeSide.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!cubeSide.getText().isEmpty() && !cubeSide.getText().matches("\\\\d+\\\\.\\\\d+"));
		});

		Node OKEButton = dialog.getDialogPane().lookupButton(OKEType);
		OKEButton.setDisable(true);

		grid.add(new Label("Cube Mass: (> 0, default " + MainObject.DEFAULT_MASS + ")"), 0, 0);
		grid.add(cubeMass, 1, 0);
		grid.add(new Label("Cube Side: (>= " + Cube.MIN_SIZE + " and <=" + Cube.MAX_SIZE + ", default "
				+ Cylinder.MAX_RADIUS + ")"), 0, 1);
		grid.add(cubeSide, 1, 1);

		// Set the disable property of the OKEButton
		cubeMass.textProperty().addListener((observable, oldValue, newValue) -> {
			OKEButton.setDisable(newValue.trim().isEmpty());
		});
		cubeSide.textProperty().addListener((observable, oldValue, newValue) -> {
			OKEButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKEType) {
				return new Pair<>(cubeMass.getText(), cubeSide.getText());
			}
			return null;

		});

		Platform.runLater(() -> {
			Optional<Pair<String, String>> result = dialog.showAndWait();

			result.ifPresent(objectProperty -> {
				try {
					// Get the mass and radius of cylinder
					double cubMass = Double.parseDouble(objectProperty.getKey());
					double cubSide = Double.parseDouble(objectProperty.getValue());

					// Create new Cube
					this.simul.setObject(new Cube(cubMass, cubSide));
					this.rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(cubSide * 2));
					this.rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(cubSide * 2));
				} catch (Exception e) {
					try {
						this.simul.setObject(new Cube(MainObject.DEFAULT_MASS, Cube.MAX_SIZE));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					this.rec.heightProperty().bind(this.downStackPane.heightProperty().multiply(Cube.MAX_SIZE * 2));
					this.rec.widthProperty().bind(this.downStackPane.heightProperty().multiply(Cube.MAX_SIZE * 2));
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText(e.getMessage());
					alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
					alert.showAndWait();
				}
			});
		});
		Platform.runLater(() -> cubeMass.requestFocus());
	}

	private void cylinderInput() {
		// Pair<String, String> : as a dictionary in python -> return result
		Dialog<Pair<String, String>> dialog = new Dialog<>();

		dialog.initStyle(StageStyle.UNDECORATED);

		dialog.setTitle("Input Property For Cylinder");
		dialog.setHeaderText("Cylinder property");
		// Create a new Button type: OKEType, set Text of that button to "OK"
		ButtonType OKEType = new ButtonType("OK", ButtonData.OK_DONE);
		// Add button to the dialog
		dialog.getDialogPane().getButtonTypes().add(OKEType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField cylinderMass = new TextField();
		cylinderMass.setPromptText("Input mass for cylinder");
		cylinderMass.textProperty().addListener(event -> {
			cylinderMass.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!cylinderMass.getText().isEmpty() && !cylinderMass.getText().matches("\\\\d+\\\\.\\\\d+"));
		});

		TextField cylinderRadius = new TextField();
		cylinderRadius.setPromptText("Input radius for cylinder");
		cylinderRadius.textProperty().addListener(event -> {
			cylinderRadius.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!cylinderRadius.getText().isEmpty() && !cylinderRadius.getText().matches("\\\\d+\\\\.\\\\d+"));
		});

		// Enable/Disable OKE Button
		Node OKEButton = dialog.getDialogPane().lookupButton(OKEType);
		OKEButton.setDisable(true);

		grid.add(new Label("Cylinder Mass: (> 0, default " + MainObject.DEFAULT_MASS + ")"), 0, 0);
		grid.add(cylinderMass, 1, 0);
		grid.add(new Label("Cylinder Radius: (>= " + Cylinder.MIN_RADIUS + " and <= " + Cylinder.MAX_RADIUS
				+ ", default " + Cylinder.MAX_RADIUS + ")"), 0, 1);
		grid.add(cylinderRadius, 1, 1);

		// Set the disable property of the OKEButton
		cylinderMass.textProperty().addListener((observable, oldValue, newValue) -> {
			// String.trim() : remove all whitespace
			OKEButton.setDisable(newValue.trim().isEmpty());
		});

		cylinderRadius.textProperty().addListener((observable, oldValue, newValue) -> {
			OKEButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKEType) {
				return new Pair<>(cylinderMass.getText(), cylinderRadius.getText());
			}
			return null;

		});

		Platform.runLater(() -> {
			Optional<Pair<String, String>> result = dialog.showAndWait();

			result.ifPresent(objectProperty -> {
				try {
					// Get the mass and radius of cylinder
					double cynMass = Double.parseDouble(objectProperty.getKey());
					double cynRadius = Double.parseDouble(objectProperty.getValue());

					// Create new Cylinder
					this.simul.setObject(new Cylinder(cynMass, cynRadius));
					this.cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(cynRadius));

				} catch (Exception e) {
					try {
						this.simul.setObject(new Cylinder(MainObject.DEFAULT_MASS, Cylinder.MAX_RADIUS));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					this.cir.radiusProperty().bind(this.downStackPane.heightProperty().multiply(Cylinder.MAX_RADIUS));
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText(e.getMessage());
					alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
					alert.showAndWait();
				}
			});
		});

		Platform.runLater(() -> cylinderMass.requestFocus());

	}
}
