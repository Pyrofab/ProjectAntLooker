package fr.antproject.application

import fr.antproject.model.diagram.Diagram
import fr.antproject.model.diagram.DiagramBase
import fr.antproject.model.diagram.transformer.PetriTransformer
import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.ShapeRegistry
import fr.antproject.model.shapes.drawn.DrawnArrow
import fr.antproject.model.shapes.drawn.DrawnCircle
import fr.antproject.model.shapes.drawn.DrawnRectangle
import fr.antproject.model.shapes.drawn.DrawnShape
import fr.antproject.utils.MAX_FUSE_DISTANCE
import fr.antproject.utils.extractPolys
import fr.antproject.utils.wrappers.*

object ImageProcessor {

    private val config = Configuration()
    var diagramTransformer = PetriTransformer()
        set(value) {
            shapeConverters = value.validShapes.map { ShapeRegistry[it] ?: throw IllegalStateException() }
        }
    private var shapeConverters = listOf(
            DrawnArrow.ArrowConverter,
            DrawnCircle.CircleConverter,
            DrawnRectangle.RectangleConverter)

    fun process(fileName: String): Diagram {
        Profiler.startSection("processing")
        Profiler.startSection("image")
        val srcImg = loadImage(fileName)
        val temp = ImageMat(srcImg)
        grayImage(img = temp, out = temp)
        threshold(grayImage = temp, threshold = config.threshold, maxValue = config.maxValue,
                algorithm = config.algorithm, optional = config.optional, out = temp)
        val contours = findContours(thresholdImageMat = temp, mode = config.mode, method = config.method)
        val ret = processContours(contours)

        Profiler.endStartSection("diagram")
        val diagramBase = DiagramBase(ret)
        val diagram = this.diagramTransformer.transformDiagram(diagramBase)
        Profiler.endSection()
        Profiler.endSection()

        return diagram
    }

    /**
     * Attempts to convert a vector of extracted contours into a list of appropriate shapes
     *
     * @param contours a vector of contours extracted from an image matrix
     */
    private fun processContours(contours: MatVector): List<DrawnShape> {
        Profiler.startSection("processContours")
        var extracted = extractPolys(contours)
        extracted = filterDuplicates(extracted)

        for (converter in shapeConverters) {
            extracted = extracted.map { converter.getFromPoly(it) ?: it }
        }
        val ret = extracted.filter { it is DrawnShape }.map { it as DrawnShape }
        Profiler.endSection()
        return ret
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
        while (++i < temp.size) {        // can't use an iterator as the underlying list changes over time
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