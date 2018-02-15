@file:JvmName("OpenCVMethodWrappers")

package fr.antproject.antlookercore.utils.wrappers

import fr.antproject.antlookercore.application.Profiler
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.FileNotFoundException

/**
 * Loads an displayedImage from the provided location
 * @param location the location of the file relative to the current directory
 * @return [an object][opencv_core.IplImage] describing this displayedImage
 *
 * @throws FileNotFoundException
 */
fun loadImage(location: String): Mat {
    Profiler.startSection("loadImage")
    val ret = Imgcodecs.imread(location)
            ?: throw FileNotFoundException("$location: this file does not exist")
    Profiler.endSection()
    return ret
}

/**
 * Converts an displayedImage into a black and white version
 * @param img [a matrix][ImageMat] representing the source displayedImage
 * @param out [ matrix][ImageMat] in which the result will be stored
 * @return out, for operation chaining
 */
fun grayImage(img: Mat, out: Mat = Mat()): Mat {
    Profiler.startSection("grayImage")
    Imgproc.cvtColor(img, out, Imgproc.COLOR_BGR2GRAY)
    Profiler.endSection()
    return out
}

/**
 * An extension version of [grayImage]. Applies the transformation directly to the instance
 * @receiver [ImageMat]
 * @see grayImage
 */
fun Mat.grayImage(copy: Boolean = true) = grayImage(this, if (copy) Mat() else this)

/**
 * Applies a threshold to an displayedImage, making all data binary depending on passed parameters
 * @param grayImage [a matrix][ImageMat] representing the source displayedImage (gray it first for better results)
 * @param threshold the threshold value used by the algorithm
 * @param maxValue a maximum value used by some algorithms to replace values above the threshold
 * @param algorithm a threshold algorithm to use
 * @param optional an additional algorithm to get an optimal threshold value
 * @param out a matrix in which the result will be stored
 * @return out, for operation chaining
 *
 * @throws IllegalArgumentException if the provided displayedImage wasn't grayed first
 */
fun threshold(grayImage: Mat, threshold: Double = 127.0, maxValue: Double = 255.0,
              algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED, optional: ThresholdTypesOptional? = null,
              out: Mat = Mat()): Mat {
//    if (!grayImage.hasTransform(EnumImgTransforms.GRAY))
//        throw IllegalArgumentException("The source displayedImage must use 8 color channels. Use ImageMat#grayImage first.")
    Profiler.startSection("threshold")
    Imgproc.threshold(grayImage, out, threshold, maxValue, algorithm.value or (optional?.value ?: 0))
    Profiler.endSection()
    return out
}

fun Mat.threshold(threshold: Double, optional: ThresholdTypesOptional?) = threshold(this, threshold, optional = optional)


/**
 * Extracts contour information from an displayedImage
 * @param thresholdImageMat a matrix describing an displayedImage, preferably modified through a threshold operation
 * @param mode the retrieval mode for contours
 * @param method the method used for contour approximation
 * @param ret a matrix vector used to store the resulting data
 *
 * @return ret, for operation chaining
 */
fun findContours(thresholdImageMat: Mat, mode: ContourRetrievalMode = ContourRetrievalMode.LIST,
                 method: ContourApproxMethod = ContourApproxMethod.SIMPLE, ret: MutableList<MatOfPoint> = mutableListOf()): List<MatOfPoint> {
//    if (!thresholdImageMat.hasTransform(EnumImgTransforms.THRESHOLD))
//        Logger.warn("No threshold applied to the source displayedImage, good luck using that")
    Profiler.startSection("findContours")
    Imgproc.findContours(thresholdImageMat, ret, null, mode.value, method.value)
    Profiler.endSection()
    return ret
}

/**
 * An extension version of [findContours]. Applies the transformation directly to the instance
 * @receiver [ImageMat]
 * @see findContours(grayImage, threshold, maxValue,algorithm, optional,ret)
 */
fun Mat.findContours(mode: ContourRetrievalMode = ContourRetrievalMode.LIST, method: ContourApproxMethod = ContourApproxMethod.SIMPLE)
        = findContours(this, mode, method)

