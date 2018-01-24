@file:JvmName("OpenCVMethodWrappers")

package fr.antproject.utils.wrappers

import fr.antproject.application.Profiler
import fr.antproject.model.shapes.Polygon
import marvin.MarvinPluginCollection
import marvin.MarvinPluginCollection.floodfillSegmentationBlob
import marvin.image.MarvinImage
import org.bytedeco.javacpp.opencv_core
import java.io.FileNotFoundException

/**
 * Loads an displayedImage from the provided location
 * @param location the location of the file relative to the current directory
 * @return [an object][opencv_core.IplImage] describing this displayedImage
 *
 * @throws FileNotFoundException
 */
fun loadImage(location: String): MarvinImage {
    Profiler.startSection("loadImage")
    val ret = marvin.io.MarvinImageIO.loadImage(location)
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
fun grayImage(img : MarvinImage, out : MarvinImage): MarvinImage {
    Profiler.startSection("grayImage")
    MarvinPluginCollection.grayScale(img, out);
    return out
}

/**
 * Extracts contour information from an displayedImage
 * @param thresholdImageMat a matrix describing an displayedImage, preferably modified through a threshold operation
 * @param mode the retrieval mode for contours
 * @param method the method used for contour approximation
 * @param ret a matrix vector used to store the resulting data
 *
 * @return ret, for operation chaining
 */
fun findContours(img: MarvinImage): List<fr.antproject.model.shapes.Polygon>{
    val segments = floodfillSegmentationBlob(img);
    val ret = mutableListOf<Polygon>()
    for (segment in segments){
        val blob = segment.getBlob();
        val contour = blob.toContour();
        ret += Polygon(contour.points.map { Point(it.x, it.y) })
    }

    return ret
    }


/**
 * An extension version of [findContours]. Applies the transformation directly to the instance
 * @receiver [ImageMat]
 * @see findContours(grayImage, threshold, maxValue,algorithm, optional,ret)

fun ImageMat.findContours(mode: ContourRetrievalMode = ContourRetrievalMode.LIST, method: ContourApproxMethod = ContourApproxMethod.SIMPLE)
        = findContours(this, mode, method)

*/