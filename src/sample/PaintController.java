package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;

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
    private FillerControl fillerTool;

    @FXML
    private RectangleControl rectangleTool;

    @FXML
    void initialize() {

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
        fillerTool.setOnAction((event) -> {
            selectedToolLabel.setText(fillerTool.getName());
            selectedTool = fillerTool;
        });
        rectangleTool.setOnAction((event) -> {
            selectedToolLabel.setText(rectangleTool.getName());
            selectedTool = rectangleTool;
        });

//        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//            }
//        });

    }

}
