package fr.antproject;

import fr.antproject.application.ImageProcessor;
import fr.antproject.application.Logger;
import fr.antproject.application.Profiler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Level;

/**
 * Main class of the AntLooker application.<br/>
 * <p>
 * Starts a javafx application to choose an image file and passes the result to the recognition system
 */
public class AntLookerApp extends Application {

    public static AntLookerApp INSTANCE;

    private Stage primaryStage;
    private FXMLLoader loader;

    public static void main(String[] args) {
        Profiler.INSTANCE.startSection("root");
        // if we get arguments passed in the command line, attempt to process the diagram directly with default settings
        if (args.length > 1)
            ImageProcessor.INSTANCE.process(args[0]).export(args[1]);
        else {
            launch(args);
        }
        Profiler.INSTANCE.endSection();
        Profiler.INSTANCE.getProfilingData("root/processing").forEach(r -> Logger.log(Logger.PROFILING, "[Profiling] " + r, null));
    }

    /**
     * Displays the file chooser. Ends the javafx thread right after a choice is made.
     */
    @Override
    public void start(Stage primaryStage) {
        INSTANCE = this;
        this.primaryStage = primaryStage;
        try {
            loader = new FXMLLoader(getClass().getResource("configscreen.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("AntLook configuration screen");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            Logger.log(Level.SEVERE, "The fxml root file could not be loaded.", e);
            System.exit(1);
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
