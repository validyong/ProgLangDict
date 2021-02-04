package main.java.app;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.controllers.FXMLController;
import main.java.db.PrgLngNDsgTableRetriever;

import java.nio.file.Paths;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        PrgLngNDsgTableRetriever.retrieve();

        URL fxmlURL = Paths.get("src\\main\\resources\\view\\RootLayout.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(fxmlURL);

        JFXDecorator jfxDecorator = new JFXDecorator(stage, root, false, true, true);

        Scene scene = new Scene(jfxDecorator);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
