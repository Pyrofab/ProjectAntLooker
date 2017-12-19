package fr.antproject;

import fr.antproject.application.ImageProcessor;
import fr.antproject.application.Logger;
import fr.antproject.application.Profiler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Main class of the AntLooker application.<br/>
 * <p>
 * Starts a javafx application to choose an image file and passes the result to the recognition system
 */
public class AntLookerApp extends Application {
    private static volatile String selectedFile;

    public static void main(String[] args) {
        Profiler.INSTANCE.startSection("root");
        if (args.length > 0)
            OpenCVTestKt.test(args[0]);
        else {
            launch(args);
        }
    }

    /**
     * Displays the file chooser. Ends the javafx thread right after a choice is made.
     */
    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image to analyse");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File initialD = new File("./data");     // gas gas gas
        fileChooser.setInitialDirectory(initialD.exists() ? initialD : new File("."));
        Profiler.INSTANCE.startSection("user_input");
        File file = fileChooser.showOpenDialog(primaryStage);
        Profiler.INSTANCE.endSection();
        if (file == null) {
            Logger.info("No file selected", null);
        } else {
            selectedFile = file.getPath();
            ImageProcessor.INSTANCE.process(selectedFile).export("diagram.pnml");
            OpenCVTestKt.test(selectedFile);
            Profiler.INSTANCE.endSection();
            Profiler.INSTANCE.getProfilingData("root/processing").forEach(r -> Logger.log(Logger.PROFILING, "[Profiling] " + r, null));
        }
        Platform.exit();
    }
}
