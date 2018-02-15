package fr.antproject.antlookercore;

import java.util.HashSet;
import java.util.Set;

import fr.antproject.antlookercore.application.ImageProcessor;
import fr.antproject.antlookercore.application.Logger;
import fr.antproject.antlookercore.application.Profiler;

/**
 * Main class of the AntLooker application.<br/>
 * <p>
 * Starts a javafx application to choose an displayedImage file and passes the result to the recognition system
 */
public class AntLookerApp /*extends Application*/ {
//
    public static AntLookerApp INSTANCE;
//
//    private Stage primaryStage;
//    private FXMLLoader loader;
//
    private Set<Runnable> closeHandlers = new HashSet<>();
//
    public static void main(String[] args) {
        Profiler.INSTANCE.startSection("root");
//        // if we get arguments passed in the command line, attempt to process the diagram directly with default settings
        if (args.length > 1) {
            ImageProcessor.INSTANCE.process(args[0]).export(args[1]);
//        } else {
//            launch(args);
        }
        Profiler.INSTANCE.endSection();
        Profiler.INSTANCE.getProfilingData("root/processing").forEach(r -> Logger.log(Logger.PROFILING, "[Profiling] " + r, null));
    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        INSTANCE = this;
//        this.primaryStage = primaryStage;
//        try {
//            loader = new FXMLLoader(getClass().getResource("configscreen.fxml"));
//            Parent root = loader.load();
//            primaryStage.setTitle("AntLook configuration screen");
//            primaryStage.setScene(new Scene(root));
//            primaryStage.setOnCloseRequest(event -> closeHandlers.forEach(Runnable::run));
//            primaryStage.show();
//        } catch (Exception e) {
//            Logger.log(Level.SEVERE, "The fxml root file could not be loaded.", e);
//            System.exit(1);
//        }
//    }
//
//    public Stage getPrimaryStage() {
//        return primaryStage;
//    }
//
//    public FXMLLoader getLoader() {
//        return loader;
//    }
//
    public void addExitListener(Runnable closeListener) {
        this.closeHandlers.add(closeListener);
    }
}
