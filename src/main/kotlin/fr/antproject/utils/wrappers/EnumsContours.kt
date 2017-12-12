package fr.antproject.utils.wrappers

import org.bytedeco.javacpp.opencv_imgproc

/**
 * Defines the various contour retrieval modes available for use in the [findContours] method
 * @see [opencv_imgproc.CV_RETR_EXTERNAL]
 */
enum class ContourRetrievalMode(val value: Int) {
    EXTERNAL(0),
    LIST(1),
    CCOMP(2),
    TREE(3),
    FLOOD_FILL(4)
}

/**
 * Defines optional contour approximation methods available for use in the [findContours] method
 * @see opencv_imgproc.CV_CHAIN_APPROX_NONE
 */
enum class ContourApproxMethod(val value: Int) {
    NONE(1),
    SIMPLE(2),
    TC89_L1(3),
    TC89_KCOS(4)
}