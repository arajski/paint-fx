package plugin.circle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.Command;
import sample.UILoader;

public class SphereControl extends AnchorPane implements Command {

    private String controlName;

    @FXML
    private Button button;

    public SphereControl() {
        this.controlName = "tool.circle";
        UILoader uiLoader = new UILoader(getClass().getResource("sphere.fxml"),this);
        uiLoader.load();
    }

    public String getName() {
        return controlName;
    }

    @Override
    public void setAction(EventHandler<ActionEvent> value) {
        button.setOnAction(value);
    }

    @Override
    public void execute(GraphicsContext gc, MouseEvent e, Color color, int size) {
        gc.setFill(color);

       if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.fillOval(e.getX()-(size/2),e.getY()-(size/2),size,size);
            gc.stroke();
        }
    }
}
