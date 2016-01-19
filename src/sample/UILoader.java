package sample;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by rjsk on 19/01/16.
 */
public class UILoader {
    private String controlName;
    private String resource;
    private Object obj;
    public UILoader(String controlName, String resource, Object obj) {
        this.controlName = controlName;
        this.resource = resource;
        this.obj=obj;
    }

    public void load() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        fxmlLoader.setRoot(obj);
        fxmlLoader.setController(obj);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
