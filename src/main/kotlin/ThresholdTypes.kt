import org.bytedeco.javacpp.opencv_imgproc

/**
 * @see opencv_imgproc.CV_THRESH_BINARY
 */
enum class ThresholdTypes(val value: Int) {
    /** value = value > threshold ? max_value : 0       */
    BINARY(opencv_imgproc.CV_THRESH_BINARY),
    /** value = value > threshold ? 0 : max_value       */
    BINARY_INVERTED(opencv_imgproc.CV_THRESH_BINARY_INV),
    /** value = value > threshold ? threshold : value   */
    TRUNCATED(opencv_imgproc.CV_THRESH_TRUNC),
    /** value = value > threshold ? value : 0           */
    TO_ZERO(opencv_imgproc.CV_THRESH_TOZERO),
    /** value = value > threshold ? 0 : value           */
    TO_ZERO_INVERTED(opencv_imgproc.CV_THRESH_TOZERO_INV),
    /** Not documented                                  */
    MASK(opencv_imgproc.CV_THRESH_MASK),
    /** use Otsu algorithm to choose the optimal threshold value;
    combine the flag with one of the above CV_THRESH_* values */
    OTSU(opencv_imgproc.CV_THRESH_OTSU),
    /** use Triangle algorithm to choose the optimal threshold value;
    combine the flag with one of the above CV_THRESH_* values, but not
    with CV_THRESH_OTSU */
    TRIANGLE(opencv_imgproc.CV_THRESH_TRIANGLE)
}