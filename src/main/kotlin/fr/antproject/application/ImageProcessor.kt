package fr.antproject.application

import fr.antproject.model.diagram.DiagramBase
import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.drawnshapes.DrawnArrow
import fr.antproject.model.shapes.drawnshapes.DrawnCircle
import fr.antproject.model.shapes.drawnshapes.DrawnRectangle
import fr.antproject.model.shapes.drawnshapes.DrawnShape
import fr.antproject.utils.MAX_FUSE_DISTANCE
import fr.antproject.utils.extractPolys
import fr.antproject.utils.wrappers.*

object ImageProcessor {
    private val config = Configuration()
    private val shapeConverters = listOf(
            DrawnArrow.ArrowConverter,
            DrawnCircle.CircleConverter,
            DrawnRectangle.RectangleConverter)

    fun process(fileName: String): Collection<DrawnShape> {
        Profiler.startSection("image_processing")
        Profiler.startSection("loadImage")
        val srcImg = loadImage(fileName)
        val temp = ImageMat(srcImg)
        Profiler.endStartSection("grayImage")
        grayImage(img = temp, out = temp)
        Profiler.endStartSection("threshold")
        threshold(grayImage = temp, threshold = config.threshold, maxValue = config.maxValue,
                algorithm = config.algorithm, optional = config.optional, out = temp)
        Profiler.endStartSection("findContours")
        val contours = findContours(thresholdImageMat = temp, mode = config.mode, method = config.method)
        Profiler.endStartSection("processContours")
        val ret = processContours(contours)
        Profiler.endSection()
        Profiler.endSection()
        return DiagramBase(ret)
    }

    /**
     * Attempts to convert a vector of extracted contours into a list of appropriate shapes
     *
     * @param contours a vector of contours extracted from an image matrix
     */
    private fun processContours(contours: MatVector) : List<DrawnShape> {
        var extracted = extractPolys(contours)
        extracted = filterDuplicates(extracted)

        for (converter in shapeConverters) {
            extracted = extracted.map { converter.getFromPoly(it) ?: it }
        }

        return extracted.filter { it is DrawnShape }.map { it as DrawnShape }
    }

    /**
     * Attempts to remove any shape that has been detected twice
     *
     * For each polygon extracted from the source image, checks if there's another polygon that has vertices
     * closer than the [MAX_FUSE_DISTANCE].
     *
     * @param polys a list of unrefined polygons extracted from an image
     */
    private fun filterDuplicates(polys: Collection<Polygon>): Collection<Polygon> {
        val temp = polys.toMutableList()
        var i = -1
        while(++i < temp.size) {        // can't use an iterator as the underlying list changes over time
            val polygon = temp[i]
            // For each polygon in the list, we check if it is different from the current polygon and that for each of its points,
            // there exists a point in the current polygon which distance to the first in less than the maximum fuse distance
            if (polys.any { it !== polygon && it.all { point -> polygon.any { point1 -> point distTo point1 < config.maxFuseDistance } } }) {
                Logger.debug("Duplicate detected for $polygon")
                temp -= polygon
            }
        }
        return temp
    }

    private class Configuration {
        // threshold
        val threshold: Double = 127.0
        val maxValue: Double = 255.0
        val algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED
        val optional: ThresholdTypesOptional? = ThresholdTypesOptional.OTSU

        // find contours
        val mode: ContourRetrievalMode = ContourRetrievalMode.LIST
        val method: ContourApproxMethod = ContourApproxMethod.SIMPLE

        val maxFuseDistance: Double = MAX_FUSE_DISTANCE
    }
}