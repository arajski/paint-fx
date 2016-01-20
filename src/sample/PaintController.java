package sample;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
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
import org.json.JSONException;
import org.json.JSONObject;

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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject loadJSON() {

        URL jsonUrl = getClass().getResource("../plugin/brush/manifest.json");
        JSONObject json = null;
        ArrayList<Command> nodes = new ArrayList<>();

        try {

            InputStream is = jsonUrl.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private ArrayList loadPlugins() {

        ArrayList<Command> commands = new ArrayList<>();
        JSONObject manifest = loadJSON();

        String className = null;
        String packageName = null;
        String toolName = null;

        try {
            className = (String) manifest.get("class");
            packageName = (String) manifest.get("package");
            toolName = (String) manifest.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(className != null && packageName != null){
            URL classUrl = getClass().getResource("../plugin/brush/" + className + ".class");
            URL[] urls = new URL[]{classUrl};
            ClassLoader cl = new URLClassLoader(urls);

            try {
                Class cls = cl.loadClass(packageName+"."+className);
                commands.add((Command) cls.newInstance());
                System.out.print("Initialized " + toolName + "!\n");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return commands;
    }

    private void initPlugins(){

        System.out.println("Initializing plugins...\n");
        ArrayList<Command> nodes = loadPlugins();
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
