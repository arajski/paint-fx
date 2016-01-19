package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class PaintController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridPane;

    @FXML
    private MenuItem polishLang;

    @FXML
    private MenuItem englishLang;

    private Command selectedTool;

    @FXML
    private Canvas canvas;

    @FXML
    private Label selectedToolLabel;

    @FXML
    private Label pixelSizeLabel;

    @FXML
    private ScrollBar sizeTool;

    @FXML
    private ColorPicker colorPicker;

    private void initPlugins(){
        System.out.println("Initializing plugins...");
        final BrushControl brushTool = new BrushControl();
        final EraserControl eraserTool = new EraserControl();
        final SphereControl sphereTool = new SphereControl();
        final RectangleControl rectangleTool = new RectangleControl();

        int col = 0;
        int row = 0;
        ArrayList<Command> nodes = new ArrayList<>();
        nodes.add(brushTool);
        nodes.add(eraserTool);
        nodes.add(sphereTool);
        nodes.add(rectangleTool);

        for(Command node : nodes) {
            gridPane.add((Node) node,col,row);
            col = (++col)%2;
            if(col == 0)
                row = (++row)%7;
            Class nodeClass = node.getClass();
            nodeClass.cast(node);

            node.setAction((event) -> {
                selectedToolLabel.setText(resources.getString(node.getName()));
                selectedTool = node;
            });
        }
    }
    @FXML
    void initialize() {

        initPlugins();
        polishLang.setOnAction(event -> {
            Main.setLocale(new Locale("pl", "PL"));
            Main reload = new Main();
            try {
                reload.reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        englishLang.setOnAction(event -> {
            Main.setLocale(new Locale("en", "US"));
            Main reload = new Main();
            try {
                reload.reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        GraphicsContext gc = canvas.getGraphicsContext2D();
        colorPicker.setValue(Color.BLACK);

        pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        sizeTool.valueProperty().addListener((event) -> {
            pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");

        });

        selectedToolLabel.setText(resources.getString("tool.none"));

//        brushTool.setOnAction((event) -> {
//            selectedToolLabel.setText(resources.getString(brushTool.getName()));
//            selectedTool = brushTool;
//        });
//        eraserTool.setOnAction((event) -> {
//            selectedToolLabel.setText(resources.getString(eraserTool.getName()));
//            selectedTool = eraserTool;
//        });
//        sphereTool.setOnAction((event) -> {
//            selectedToolLabel.setText(resources.getString(sphereTool.getName()));
//            selectedTool = sphereTool;
//        });
//        rectangleTool.setOnAction((event) -> {
//            selectedToolLabel.setText(resources.getString(rectangleTool.getName()));
//            selectedTool = rectangleTool;
//        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if(!selectedToolLabel.getText().equals(resources.getString("tool.none"))) {
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
