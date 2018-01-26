package fr.antproject.utils.wrappers

import org.opencv.imgproc.Imgproc

/**
 * Defines the various threshold types available for use in the [threshold] method
 * @see Imgproc.CV_THRESH_BINARY
 */
enum class ThresholdTypes(val value: Int) {
    /** value = value > fr.antproject.utils.wrappers.threshold ? max_value : 0       */
    BINARY(0/*Imgproc.CV_THRESH_BINARY*/),
    /** value = value > fr.antproject.utils.wrappers.threshold ? 0 : max_value       */
    BINARY_INVERTED(1/*Imgproc.CV_THRESH_BINARY_INV*/),
    /** value = value > fr.antproject.utils.wrappers.threshold ? fr.antproject.utils.wrappers.threshold : value   */
    TRUNCATED(2/*Imgproc.CV_THRESH_TRUNC*/),
    /** value = value > fr.antproject.utils.wrappers.threshold ? value : 0           */
    TO_ZERO(3/*Imgproc.CV_THRESH_TOZERO*/),
    /** value = value > fr.antproject.utils.wrappers.threshold ? 0 : value           */
    TO_ZERO_INVERTED(4/*Imgproc.CV_THRESH_TOZERO_INV*/),
    /** Not documented                                  */
    MASK(7/*Imgproc.CV_THRESH_MASK*/),
}

enum class ThresholdTypesOptional(val value: Int) {
    /** use Otsu algorithm to choose the optimal threshold value;
    combine the flag with one of the above CV_THRESH_* values */
    OTSU(8/*Imgproc.CV_THRESH_OTSU*/),
    /** use Triangle algorithm to choose the optimal threshold value;
    combine the flag with one of the above CV_THRESH_* values, but not
    with CV_THRESH_OTSU */
    TRIANGLE(16/*Imgproc.CV_THRESH_TRIANGLE*/)
}