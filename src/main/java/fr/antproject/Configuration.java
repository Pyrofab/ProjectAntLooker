//package fr.antproject;
//
//import fr.antproject.utils.ProcessingHelpers;
//import fr.antproject.utils.wrappers.ContourApproxMethod;
//import fr.antproject.utils.wrappers.ContourRetrievalMode;
//import fr.antproject.utils.wrappers.ThresholdTypes;
//import fr.antproject.utils.wrappers.ThresholdTypesOptional;
//
//public class Configuration {
//    public double threshold = 140;
//    public double maxValue = 255;
//    public ThresholdTypes algorithm = ThresholdTypes.BINARY_INVERTED;
//    public ThresholdTypesOptional optional = null;
//
//    public ContourRetrievalMode mode = ContourRetrievalMode.LIST;
//    public ContourApproxMethod method = ContourApproxMethod.TC89_KCOS;
//
//    public double maxFuseDistance = ProcessingHelpers.MAX_FUSE_DISTANCE;
//    /**The fraction of the average under which detected shapes are considered errors*/
//    public double minAcceptedArea = 0.2;
//}
