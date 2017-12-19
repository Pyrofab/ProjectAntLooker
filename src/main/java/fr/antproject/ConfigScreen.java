package fr.antproject;

import fr.antproject.application.ImageProcessor;
import fr.antproject.application.Logger;
import fr.antproject.application.Profiler;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class ConfigScreen {
    public void openImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image to analyse");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File initialD = new File("./data");     // gas gas gas
        fileChooser.setInitialDirectory(initialD.exists() ? initialD : new File("."));
        Profiler.INSTANCE.startSection("user_input");
        File file = fileChooser.showOpenDialog(AntLookerApp.INSTANCE.getPrimaryStage());
        Profiler.INSTANCE.endSection();
        if (file == null) {
            Logger.info("No file selected", null);
        } else {
            String selectedFile = file.getPath();
            ImageProcessor.INSTANCE.process(selectedFile).export("diagram.pnml");
            OpenCVTestKt.test(selectedFile);
        }
    }
}
