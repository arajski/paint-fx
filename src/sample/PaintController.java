package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class PaintController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canvas;

    @FXML
    private Label selectedToolLabel;

    @FXML
    private Label pixelSizeLabel;

    @FXML
    private ScrollBar sizeTool;

    @FXML
    private BrushControl brushTool;

    private Command selectedTool;

    @FXML
    private EraserControl eraserTool;

    @FXML
    private SphereControl sphereTool;

    @FXML
    private RectangleControl rectangleTool;

    @FXML
    private ColorPicker colorPicker;


    @FXML
    void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        colorPicker.setValue(Color.BLACK);

        pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        sizeTool.valueProperty().addListener((event) -> {
            pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");

        });

        selectedToolLabel.setText("None");
        brushTool.setOnAction((event) -> {
            selectedToolLabel.setText(brushTool.getName());
            selectedTool = brushTool;
        });
        eraserTool.setOnAction((event) -> {
            selectedToolLabel.setText(eraserTool.getName());
            selectedTool = eraserTool;
        });
        sphereTool.setOnAction((event) -> {
            selectedToolLabel.setText(sphereTool.getName());
            selectedTool = sphereTool;
        });
        rectangleTool.setOnAction((event) -> {
            selectedToolLabel.setText(rectangleTool.getName());
            selectedTool = rectangleTool;
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if(!selectedToolLabel.getText().equals("None")) {
                selectedTool.execute(gc,event,colorPicker.getValue(),(int)sizeTool.getValue());
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(!selectedToolLabel.getText().equals("None")) {
                selectedTool.execute(gc,event,colorPicker.getValue(),(int)sizeTool.getValue());

            }
        });

    }

}
