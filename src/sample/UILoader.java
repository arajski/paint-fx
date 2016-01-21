package sample;

import javafx.fxml.FXMLLoader;


import java.io.IOException;
import java.net.URL;

public class UILoader {

    private URL resource;
    private Object obj;

    public UILoader(URL resource, Object obj) {
        this.resource = resource;
        this.obj=obj;
    }

    public void load() {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setRoot(obj);
        fxmlLoader.setController(obj);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
