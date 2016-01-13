package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;

public class PaintController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label selectedToolLabel;

    @FXML
    private Label pixelSizeLabel;

    @FXML
    private ScrollBar sizeTool;

    @FXML
    private Button brushTool;

    @FXML
    private Button eraseTool;

    @FXML
    private Button bucketTool;

    @FXML
    private Button rectangleTool;
    @FXML
    void initialize() {
        pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        sizeTool.valueProperty().addListener((event) -> {
            pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        });
        selectedToolLabel.setText("None");
        brushTool.setOnAction((event) -> {
            selectedToolLabel.setText("Brush");
        });
        eraseTool.setOnAction((event) -> {
            selectedToolLabel.setText("Eraser");
        });
        bucketTool.setOnAction((event) -> {
            selectedToolLabel.setText("Filler");
        });
        rectangleTool.setOnAction((event) -> {
            selectedToolLabel.setText("Rectangle");
        });


    }

}
