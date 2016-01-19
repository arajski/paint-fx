package sample;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class SphereControl extends AnchorPane implements Command {

    private String controlName;

    public SphereControl() {
        this.controlName = "tool.circle";
        UILoader uiLoader = new UILoader(controlName,"sphere.fxml",this);
        uiLoader.load();
    }

    public String getName() {
        return controlName;
    }

    @Override
    public void setAction(EventHandler<ActionEvent> value) {
        onActionProperty().set(value);
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

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }
    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        public Object getBean() {
            return SphereControl .this;
        }

        @Override
        public String getName() {
            return "onAction";
        }

        @Override protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }
    };
}
