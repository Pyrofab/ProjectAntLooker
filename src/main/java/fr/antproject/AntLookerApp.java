package fr.antproject;

import fr.antproject.utils.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AntLookerApp extends Application {
    private static volatile String selectedFile;

    public static void main(String[] args) {
        if(args.length > 0)
            OpenCVTestKt.test(args[0]);
        else {
            launch(args);
            if(selectedFile != null)
                OpenCVTestKt.test(selectedFile);
        }
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
            selectedFile = file.getPath();
        else
            Logger.INSTANCE.info("No file selected", null);
        Platform.exit();
    }
}
