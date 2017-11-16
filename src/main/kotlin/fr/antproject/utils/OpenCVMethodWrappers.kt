package fr.antproject.utils

import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgcodecs
import org.bytedeco.javacpp.opencv_imgproc
import java.io.FileNotFoundException

fun loadImage(location: String): opencv_core.IplImage = opencv_imgcodecs.cvLoadImage(location)
        ?: throw FileNotFoundException("$location: this file does not exist")

/**
 * Converts an image into a black and white version
 * @param img a matrix representing the source image
 * @param ret a matrix in which the result will be stored
 * @return ret, for operation chaining
 */
fun grayImage(img : ImageMat, ret: ImageMat = ImageMat(img.imgTransformFlags)) : ImageMat {
    opencv_imgproc.cvtColor(img, ret, opencv_imgproc.COLOR_BGR2GRAY)
    ret.addTransform(EnumImgTransforms.GRAY)
    return ret
}

/**
 * An extension version of the above function
 * Applies the transformation directly to the instance
 */
fun ImageMat.grayImage(copy: Boolean = true) = grayImage(this, if (copy) ImageMat(this.imgTransformFlags) else this)

/**
 * Applies a threshold to an image, making all data binary depending on passed parameters
 * @param grayImage a matrix representing the source image (gray it first for better results)
 * @param threshold the threshold value used by the algorithm
 * @param maxValue a maximum value used by some algorithms to replace values above the threshold
 * @param algorithm a threshold algorithm to use
 * @param optional an additional algorithm to get an optimal threshold value
 * @param ret a matrix in which the result will be stored
 * @return ret, for operation chaining
 *
 * @throws IllegalArgumentException if the provided image wasn't grayed first
 */
fun threshold(grayImage: ImageMat, threshold: Double = 127.0, maxValue: Double = 255.0,
              algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED, optional: ThresholdTypesOptional? = null,
              ret: ImageMat = ImageMat(grayImage.imgTransformFlags)): ImageMat {
    if (!grayImage.hasTransform(EnumImgTransforms.GRAY))
        throw IllegalArgumentException("The source image must use 8 color channels. Use fr.antproject.utils.ImageMat#grayImage first.")
    opencv_imgproc.threshold(grayImage, ret, threshold, maxValue, algorithm.value or (optional?.value ?: 0))
    ret.addTransform(EnumImgTransforms.THRESHOLD)
    return ret
}

/**
 * An extension version of the above function <br/>
 * Applies the transformation directly to the instance
 */
fun ImageMat.threshold(threshold: Double = 127.0, maxValue: Double = 255.0, algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED,
                       optional: ThresholdTypesOptional? = null, copy: Boolean = true)
        = threshold(this, threshold, maxValue, algorithm, optional, if (copy) ImageMat(this.imgTransformFlags) else this)

/**
 * Extracts contour information from an image
 */
fun findContours(thresholdImageMat: ImageMat, mode: ContourRetrievalMode = ContourRetrievalMode.LIST,
                 method: ContourApproxMethod = ContourApproxMethod.SIMPLE, ret: MatVector = MatVector()): MatVector {
    if(!thresholdImageMat.hasTransform(EnumImgTransforms.THRESHOLD))
        println("No threshold applied to the source image, good luck using that")
    opencv_imgproc.findContours(thresholdImageMat, ret, mode.value, method.value)
    return ret
}

/**
 * An extension version of the above function
 * Applies the transformation directly to the instance
 */
fun ImageMat.findContours(mode: ContourRetrievalMode = ContourRetrievalMode.LIST, method: ContourApproxMethod = ContourApproxMethod.SIMPLE)
        = findContours(this, mode, method)

