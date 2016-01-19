
package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.paint.Color;


public class BrushControl extends AnchorPane implements Command {

    private String controlName;

    public BrushControl() {
        this.controlName = "tool.brush";
        UILoader uiLoader = new UILoader(controlName,"brush.fxml",this);
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

        gc.setLineWidth(size);
        gc.setStroke(color);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        if(e.getEventType()==MouseEvent.MOUSE_DRAGGED) {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        }
        else if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();
        }
    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }
    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        public Object getBean() {
            return BrushControl .this;
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
