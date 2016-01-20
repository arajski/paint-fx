package plugin.eraser;

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

public class EraserControl extends AnchorPane implements Command {

    private String controlName;

    @FXML
    private Button button;

    public EraserControl() {
        this.controlName = "tool.eraser";
        UILoader uiLoader = new UILoader(getClass().getResource("eraser.fxml"),this);
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

        if(e.getEventType()==MouseEvent.MOUSE_DRAGGED) {
            gc.clearRect(e.getX() - 2, e.getY() - 2, size, size);
        }
    }
}
