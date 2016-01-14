package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * Created by rjsk on 14/01/16.
 */
public interface Command {
    void execute(GraphicsContext gc,MouseEvent e);
}
