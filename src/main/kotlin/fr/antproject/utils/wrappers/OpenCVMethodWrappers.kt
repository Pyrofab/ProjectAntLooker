@file:JvmName("OpenCVMethodWrappers")

package fr.antproject.utils.wrappers

import fr.antproject.application.Logger
import fr.antproject.application.Profiler
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgcodecs
import org.bytedeco.javacpp.opencv_imgproc
import java.io.FileNotFoundException

/**
 * Loads an image from the provided location
 * @param location the location of the file relative to the current directory
 * @return [an object][opencv_core.IplImage] describing this image
 *
 * @throws FileNotFoundException
 */
fun loadImage(location: String): opencv_core.IplImage {
    Profiler.startSection("loadImage")
    val ret = opencv_imgcodecs.cvLoadImage(location)
            ?: throw FileNotFoundException("$location: this file does not exist")
    Profiler.endSection()
    return ret
}

/**
 * Converts an image into a black and white version
 * @param img [a matrix][ImageMat] representing the source image
 * @param out [ matrix][ImageMat] in which the result will be stored
 * @return out, for operation chaining
 */
fun grayImage(img: ImageMat, out: ImageMat = ImageMat(img.imgTransformFlags)): ImageMat {
    Profiler.startSection("grayImage")
    opencv_imgproc.cvtColor(img, out, opencv_imgproc.COLOR_BGR2GRAY)
    out.addTransform(EnumImgTransforms.GRAY)
    Profiler.endSection()
    return out
}

/**
 * An extension version of [grayImage]. Applies the transformation directly to the instance
 * @receiver [ImageMat]
 * @see grayImage
 */
fun ImageMat.grayImage(copy: Boolean = true) = grayImage(this, if (copy) ImageMat(this.imgTransformFlags) else this)

/**
 * Applies a threshold to an image, making all data binary depending on passed parameters
 * @param grayImage [a matrix][ImageMat] representing the source image (gray it first for better results)
 * @param threshold the threshold value used by the algorithm
 * @param maxValue a maximum value used by some algorithms to replace values above the threshold
 * @param algorithm a threshold algorithm to use
 * @param optional an additional algorithm to get an optimal threshold value
 * @param out a matrix in which the result will be stored
 * @return out, for operation chaining
 *
 * @throws IllegalArgumentException if the provided image wasn't grayed first
 */
fun threshold(grayImage: ImageMat, threshold: Double = 127.0, maxValue: Double = 255.0,
              algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED, optional: ThresholdTypesOptional? = null,
              out: ImageMat = ImageMat(grayImage.imgTransformFlags)): ImageMat {
    if (!grayImage.hasTransform(EnumImgTransforms.GRAY))
        throw IllegalArgumentException("The source image must use 8 color channels. Use ImageMat#grayImage first.")
    Profiler.startSection("threshold")
    opencv_imgproc.threshold(grayImage, out, threshold, maxValue, algorithm.value or (optional?.value ?: 0))
    out.addTransform(EnumImgTransforms.THRESHOLD)
    Profiler.endSection()
    return out
}

/**
 * An extension version of [threshold]. Applies the transformation directly to the instance
 * @receiver [ImageMat]
 * @see threshold
 */
fun ImageMat.threshold(threshold: Double = 127.0, maxValue: Double = 255.0, algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED,
                       optional: ThresholdTypesOptional? = null, copy: Boolean = true)
        = threshold(this, threshold, maxValue, algorithm, optional, if (copy) ImageMat(this.imgTransformFlags) else this)

/**
 * Extracts contour information from an image
 * @param thresholdImageMat a matrix describing an image, preferably modified through a threshold operation
 * @param mode the retrieval mode for contours
 * @param method the method used for contour approximation
 * @param ret a matrix vector used to store the resulting data
 *
 * @return ret, for operation chaining
 */
fun findContours(thresholdImageMat: ImageMat, mode: ContourRetrievalMode = ContourRetrievalMode.LIST,
                 method: ContourApproxMethod = ContourApproxMethod.SIMPLE, ret: MatVector = MatVector()): MatVector {
    if (!thresholdImageMat.hasTransform(EnumImgTransforms.THRESHOLD))
        Logger.warn("No threshold applied to the source image, good luck using that")
    Profiler.startSection("findContours")
    opencv_imgproc.findContours(thresholdImageMat, ret, mode.value, method.value)
    Profiler.endSection()
    return ret
}

/**
 * An extension version of [findContours]. Applies the transformation directly to the instance
 * @receiver [ImageMat]
 * @see findContours(grayImage, threshold, maxValue,algorithm, optional,ret)
 */
fun ImageMat.findContours(mode: ContourRetrievalMode = ContourRetrievalMode.LIST, method: ContourApproxMethod = ContourApproxMethod.SIMPLE)
        = findContours(this, mode, method)

