
package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class EraserControl extends AnchorPane implements Command {

    private String controlName;

    public EraserControl() {
        this.controlName = "tool.eraser";
        UILoader uiLoader = new UILoader(controlName,"eraser.fxml",this);
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

        if(e.getEventType()==MouseEvent.MOUSE_DRAGGED) {
            gc.clearRect(e.getX() - 2, e.getY() - 2, size, size);
        }
    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }
    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        public Object getBean() {
            return EraserControl .this;
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
