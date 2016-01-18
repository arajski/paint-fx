package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Created by rjsk on 14/01/16.
 */
public interface Command {
    void execute(GraphicsContext gc, MouseEvent e, Color color, int sizeTool);

}
