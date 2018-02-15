package fr.antproject.antlookercore;

public class ConfigScreen {
//    private File chosenOne;
//
//    public void initialize(){
//        thresholdTypes.setItems(FXCollections.observableArrayList(ThresholdTypes.values()));
//        thresholdTypes.setValue(ThresholdTypes.BINARY_INVERTED);
//        ObservableList<ThresholdTypesOptional> list = FXCollections.observableArrayList(ThresholdTypesOptional.values());
//        list.add(null);
//        thresholdTypesOptional.setItems(list);
//        thresholdTypesOptional.setValue(null);
//        contourRetrievalMode.setItems(FXCollections.observableArrayList(ContourRetrievalMode.values()));
//        contourRetrievalMode.setValue(ContourRetrievalMode.LIST);
//        contourApproxMethod.setItems(FXCollections.observableArrayList(ContourApproxMethod.values()));
//        contourApproxMethod.setValue(ContourApproxMethod.TC89_KCOS);
//        imageView.fitWidthProperty().bind(imgPane.widthProperty());
//        imageView.fitHeightProperty().bind(imgPane.heightProperty());
//    }
//
//    public void openImage() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select an displayedImage to analyse");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
//        File initialD = new File("./data");     // gas gas gas
//        fileChooser.setInitialDirectory(initialD.exists() ? initialD : new File("."));
//        Profiler.INSTANCE.startSection("user_input");
//        chosenOne = fileChooser.showOpenDialog(AntLookerApp.INSTANCE.getPrimaryStage());
//        Profiler.INSTANCE.endSection();
//        if (chosenOne == null) {
//            Logger.info("No file selected", null);
//        } else {
//            progressIndicator.setVisible(true);
//            String selectedFile = chosenOne.getPath();
//            ConfigReload.scheduleReload(selectedFile, null);
////            OpenCVTestKt.test(selectedFile);
//        }
//    }
//
//    public void refresh(){
//        if (chosenOne != null) {
//            progressIndicator.setVisible(true);
//            ConfigReload.scheduleReload(chosenOne.getPath(), null);
//        }
//    }
//
//    public void reset(){
//        threshold.setValue(140);
//        maxFuseDistance.setValue(13.5);
//        thresholdTypes.setValue(ThresholdTypes.BINARY_INVERTED);
//        thresholdTypesOptional.setValue(null);
//        contourRetrievalMode.setValue(ContourRetrievalMode.LIST);
//        contourApproxMethod.setValue(ContourApproxMethod.TC89_KCOS);
//        minAcceptedArea.setValue(0.2);
//        refresh();
//    }
//
//    public void saveFile(){
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select a file to save the diagram");
//        File initialD = new File("./data");     // gas gas gas
//        fileChooser.setInitialDirectory(initialD.exists() ? initialD : new File("."));
//        File chosenTwo = fileChooser.showSaveDialog(AntLookerApp.INSTANCE.getPrimaryStage());
//        if (chosenOne == null || chosenTwo == null) {
//            Logger.info("No file selected", null);
//        } else {
//            ConfigReload.scheduleReload(chosenOne.getPath(), chosenTwo.getPath());
//        }
//    }
//
//    public void sendByMail(){
//
//    }
//
//    public Slider threshold;
//    public Slider maxFuseDistance;
//    public ChoiceBox<ThresholdTypes> thresholdTypes;
//    public ChoiceBox<ThresholdTypesOptional> thresholdTypesOptional;
//    public ChoiceBox<ContourRetrievalMode> contourRetrievalMode;
//    public ChoiceBox<ContourApproxMethod> contourApproxMethod;
//    public Slider minAcceptedArea;
//    public Pane imgPane;
//    public ImageView imageView;
//    public ProgressIndicator progressIndicator;
}
