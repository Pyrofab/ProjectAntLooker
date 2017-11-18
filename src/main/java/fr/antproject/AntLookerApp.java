package fr.antproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AntLookerApp extends Application {
    public static void main(String[] args) {
        if(args.length > 0)
            OpenCVTestKt.test(args[0]);
        else
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image to analyse");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File initialD = new File("./data");     // gas gas gas
        fileChooser.setInitialDirectory(initialD.exists() ? initialD : new File("."));
        File file = fileChooser.showOpenDialog(primaryStage);
        if(file != null)
            OpenCVTestKt.test(file.getPath());
        System.out.println("No file selected");
        Platform.exit();
    }
}
