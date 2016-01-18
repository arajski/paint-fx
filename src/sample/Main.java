package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    private Scene scene;
    public static Stage stage;
    private static Locale locale = new Locale("en", "US");

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.messages",locale));

        Parent root = loader.load();
        primaryStage.setTitle("- PaintFX - Artur Rajski -");
        primaryStage.setScene(new Scene(root, 600, 345));
        primaryStage.show();
    }

    public void reload() throws IOException {
        System.out.println("Reloading...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.messages",locale));
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }
    public static void setLocale(Locale newLocale) {
        locale = newLocale;
    }
}
