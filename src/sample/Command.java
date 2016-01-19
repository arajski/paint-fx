package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public interface Command {

    void execute(GraphicsContext gc, MouseEvent e, Color color, int sizeTool);
    String getName();
    void setAction(EventHandler<ActionEvent> value);

}
