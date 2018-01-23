package fr.antproject.application

import fr.antproject.display
import fr.antproject.model.diagram.DiagramBase
import fr.antproject.model.diagram.IDiagram
import fr.antproject.model.diagram.transformer.IDiagramTransformer
import fr.antproject.model.diagram.transformer.PetriTransformer
import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.drawn.DrawnShape
import fr.antproject.model.shapes.drawn.ShapeRegistry
import fr.antproject.utils.MAX_FUSE_DISTANCE
import fr.antproject.utils.extractPolys
import fr.antproject.utils.wrappers.*
import java.util.*

object ImageProcessor {

    val config = Configuration()
    var diagramTransformer : IDiagramTransformer<*> = PetriTransformer()

    @get:Synchronized
    @set:Synchronized
    private var diagramBase: DiagramBase? = null

    fun process(fileName: String): IDiagram {
        processImage(fileName)
        return generateDiagram()
    }

    fun processImage(fileName: String) {
        Profiler.startSection("processing")
        Profiler.startSection("image")
        val srcImg = loadImage(fileName)
        val temp = ImageMat(srcImg)
        grayImage(img = temp, out = temp)
        threshold(grayImage = temp, threshold = config.threshold, maxValue = config.maxValue,
                algorithm = config.algorithm, optional = config.optional, out = temp)
        /*
        opencv_highgui.imshow("img", temp)
        opencv_highgui.cvWaitKey()
        */

        val contours = findContours(thresholdImageMat = temp, mode = config.mode, method = config.method)
        val ret = processContours(contours)

        display(ret, temp)

        Profiler.endStartSection("diagram_base")

        val averageArea = ret.sumByDouble(DrawnShape::getArea) / ret.size
        val diagramBase = DiagramBase(ret.filter { it.getArea() > config.minAcceptedArea * averageArea })
        display(diagramBase, ImageMat(srcImg))
        this.diagramBase = diagramBase

        Profiler.endSection()
        Profiler.endSection()
    }

    fun generateDiagram(): IDiagram {
        Profiler.startSection("diagram")
        val diagram = this.diagramTransformer.transformDiagram(
                Objects.requireNonNull(diagramBase, "No image has been previously processed"))
        Profiler.endSection()
        return diagram
    }

    /**
     * Attempts to convert a vector of extracted contours into a list of appropriate shapes
     *
     * @param contours a vector of contours extracted from an displayedImage matrix
     */
    private fun processContours(contours: MatVector): List<DrawnShape> {
        Profiler.startSection("processContours")
        var extracted = extractPolys(contours)
        extracted = filterDuplicates(extracted)
        val shapeConverters = this.diagramTransformer.validShapes
                .map { ShapeRegistry[it] ?: throw IllegalStateException("$it: this shape has no registered converter") }
        for (converter in shapeConverters) {
            extracted = extracted.map { converter.getFromPoly(it) ?: it }
        }
        val ret = extracted.filterIsInstance(DrawnShape::class.java)
        Profiler.endSection()
        return ret
    }

    /**
     * Attempts to remove any shape that has been detected twice
     *
     * For each polygon extracted from the source displayedImage, checks if there's another polygon that has vertices
     * closer than the [MAX_FUSE_DISTANCE].
     *
     * @param polys a list of unrefined polygons extracted from an displayedImage
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

}