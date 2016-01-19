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
        BrushControl brushTool = new BrushControl();
        EraserControl eraserTool = new EraserControl();
        SphereControl sphereTool = new SphereControl();
        RectangleControl rectangleTool = new RectangleControl();

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

    private void reloadUI(Locale locale){
        Main.setLocale(locale);
        Main reload = new Main();
        try {
            reload.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeCommand(GraphicsContext gc, MouseEvent event){
        if(!selectedToolLabel.getText().equals(resources.getString("tool.none"))) {
            selectedTool.execute(gc,event,colorPicker.getValue(),(int)sizeTool.getValue());
        }
    }

    @FXML
    void initialize() {

        initPlugins();

        polishLang.setOnAction(event -> reloadUI(new Locale("pl", "PL")));
        englishLang.setOnAction(event -> reloadUI(new Locale("en", "US")));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        colorPicker.setValue(Color.BLACK);

        pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");

        sizeTool.valueProperty().addListener((event) -> {
            pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        });

        selectedToolLabel.setText(resources.getString("tool.none"));
        
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> executeCommand(gc,event));
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> executeCommand(gc,event));

    }

}
