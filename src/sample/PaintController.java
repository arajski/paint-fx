package sample;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    @FXML
    private MenuItem newFile;

    @FXML
    private MenuItem closeApp;

    @FXML
    private MenuItem aboutApp;

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

        System.out.println("Initializing plugins...\n");
        PluginManager pluginManager = new PluginManager();
        ArrayList<Command> nodes = new ArrayList<>();

        nodes = pluginManager.loadPlugins();

        int col = 0;
        int row = 0;

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

    private void clearCanvas(GraphicsContext gc) {
        gc.clearRect(0, 0, 650, 350);
    }

    private void closeApplication() {
        System.exit(0);
    }

    private void showAboutModal() {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));
        dialogVbox.setSpacing(10);
        dialogVbox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(new Text(resources.getString("dialog.applications")));
        dialogVbox.getChildren().add(new Text(resources.getString("dialog.project")));
        dialogVbox.getChildren().add(new Text("Artur Rajski"));

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.setTitle(resources.getString("menu.help.about"));
        dialog.show();
    }

    @FXML
    void initialize() {

        initPlugins();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);
        pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        selectedToolLabel.setText(resources.getString("tool.none"));

        sizeTool.valueProperty().addListener((event) -> {
            pixelSizeLabel.setText(String.format( "%.0f", sizeTool.getValue()) + " px");
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> executeCommand(gc,event));
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> executeCommand(gc,event));
        polishLang.setOnAction(event -> reloadUI(new Locale("pl", "PL")));
        englishLang.setOnAction(event -> reloadUI(new Locale("en", "US")));
        newFile.setOnAction(event -> clearCanvas(gc));
        closeApp.setOnAction(event -> closeApplication());
        aboutApp.setOnAction(event -> showAboutModal());
    }

}
