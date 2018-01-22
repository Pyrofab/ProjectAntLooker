package fr.antproject;

import fr.antproject.application.ImageProcessor;
import fr.antproject.application.Logger;
import fr.antproject.application.Profiler;
import fr.antproject.utils.wrappers.ContourApproxMethod;
import fr.antproject.utils.wrappers.ContourRetrievalMode;
import fr.antproject.utils.wrappers.ThresholdTypes;
import fr.antproject.utils.wrappers.ThresholdTypesOptional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.concurrent.Executors;

public class ConfigScreen {
    private File chosenOne;

    public void openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an displayedImage to analyse");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File initialD = new File("./data");     // gas gas gas
        fileChooser.setInitialDirectory(initialD.exists() ? initialD : new File("."));
        Profiler.INSTANCE.startSection("user_input");
        chosenOne = fileChooser.showOpenDialog(AntLookerApp.INSTANCE.getPrimaryStage());
        Profiler.INSTANCE.endSection();
        if (chosenOne == null) {
            Logger.info("No file selected", null);
        } else {
            progressIndicator.setVisible(true);
            String selectedFile = chosenOne.getPath();
            ConfigReload.scheduleReload(selectedFile, "diagram.pnml");
//            OpenCVTestKt.test(selectedFile);
        }
    }

    public void initialize(){
        thresholdTypes.setItems(FXCollections.observableArrayList(ThresholdTypes.values()));
        thresholdTypes.setValue(ThresholdTypes.BINARY_INVERTED);
        ObservableList<ThresholdTypesOptional> list = FXCollections.observableArrayList(ThresholdTypesOptional.values());
        list.add(null);
        thresholdTypesOptional.setItems(list);
        thresholdTypesOptional.setValue(null);
        contourRetrievalMode.setItems(FXCollections.observableArrayList(ContourRetrievalMode.values()));
        contourRetrievalMode.setValue(ContourRetrievalMode.LIST);
        contourApproxMethod.setItems(FXCollections.observableArrayList(ContourApproxMethod.values()));
        contourApproxMethod.setValue(ContourApproxMethod.TC89_KCOS);
    }

    public void refresh(){
        if (chosenOne != null) {
            progressIndicator.setVisible(true);
            ConfigReload.scheduleReload(chosenOne.getPath(), null);
        }
    }

    public void reset(){
        threshold.setValue(140);
        maxFuseDistance.setValue(13.5);
        thresholdTypes.setValue(ThresholdTypes.BINARY_INVERTED);
        thresholdTypesOptional.setValue(null);
        contourRetrievalMode.setValue(ContourRetrievalMode.LIST);
        contourApproxMethod.setValue(ContourApproxMethod.TC89_KCOS);
        minAcceptedArea.setValue(0.2);
        refresh();
    }

    public Slider threshold;
    public Slider maxFuseDistance;
    public ChoiceBox<ThresholdTypes> thresholdTypes;
    public ChoiceBox<ThresholdTypesOptional> thresholdTypesOptional;
    public ChoiceBox<ContourRetrievalMode> contourRetrievalMode;
    public ChoiceBox<ContourApproxMethod> contourApproxMethod;
    public Slider minAcceptedArea;
    public javafx.scene.image.ImageView imageView;
    public ProgressIndicator progressIndicator;
}
