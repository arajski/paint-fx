package sample;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class UILoader {

    private String resource;
    private Object obj;
    public UILoader(String resource, Object obj) {
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
