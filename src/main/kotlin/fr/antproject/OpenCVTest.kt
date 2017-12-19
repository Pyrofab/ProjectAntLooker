package fr.antproject

import fr.antproject.application.ImageProcessor
import fr.antproject.application.Logger
import fr.antproject.application.Profiler
import fr.antproject.model.shapes.Circle
import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.Shape
import fr.antproject.model.shapes.drawn.DrawnArrow
import fr.antproject.model.shapes.drawn.DrawnCircle
import fr.antproject.model.shapes.drawn.DrawnRectangle
import fr.antproject.utils.Color
import fr.antproject.utils.processContours
import fr.antproject.utils.wrappers.*
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui.cvWaitKey
import org.bytedeco.javacpp.opencv_highgui.imshow
import org.bytedeco.javacpp.opencv_imgproc

/**
 * Delegates main method to the javafx application main
 */
fun main(args: Array<String>) = AntLookerApp.main(args)

/**
 * Loads the image file at the given path and attempts to detect shapes in it
 * @param fileName the path to the image file
 */
fun test(fileName: String) {
    Logger.info("Loading image at location $fileName")
    val src = ImageMat(loadImage(fileName))
    val dest = ImageMat(loadImage(fileName))
    val processedContours = processContours(src.grayImage()
            .threshold(threshold = ImageProcessor.config.threshold,optional = ImageProcessor.config.optional)
            .findContours(ImageProcessor.config.mode, ImageProcessor.config.method))
    display(processedContours, dest)
//    val processedContours = ImageProcessor.process(fileName)
}

fun display(processedContours: Collection<Shape>, dest: opencv_core.Mat) {
    processedContours.forEachIndexed { i, shape ->
        Logger.debug("Shape #$i: $shape")
        when (shape) {
            is Polygon -> when (shape) {
                is DrawnRectangle -> shape.forEach {
                    opencv_imgproc.drawMarker(dest, it,
                            Color.BLUE, 0, 20, 1, 8)
                }
                is DrawnCircle -> {
                    opencv_imgproc.circle(dest, shape.approx.center, 3, Color.GREEN, -1, 8, 0)
                    opencv_imgproc.circle(dest, shape.approx.center, shape.approx.radius, Color.RED, 3, 8, 0)
                }
                is DrawnArrow -> {
                    opencv_imgproc.arrowedLine(dest, shape.approx.startPoint, shape.approx.lastPoint, Color.ORANGE)
//                    Logger.debug("Closest shape: ${processedContours.getClosestShape(shape.approx.lastPoint)}")
                }
                else -> {
                    val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()
                    val furthest = shape.maxBy { it distTo average }
                    val index = shape.indexOf(furthest)
                    val prev = shape[(if (index == 0) shape.nbPoints() else index) - 1]
                    val next = shape[(if (index == shape.nbPoints() - 1) 0 else index + 1)]
                    shape.forEach {
                        opencv_imgproc.drawMarker(dest, it,
                                if (it == furthest) Color.RED else if (it == prev || it == next) Color.BLUE else Color.GREEN, 0, 20, 1, 8)
                    }
                }
            }
            is Circle -> {
                opencv_imgproc.circle(dest, shape.center, 3, Color.GREEN, -1, 8, 0)
                opencv_imgproc.circle(dest, shape.center, shape.radius, Color.RED, 3, 8, 0)
            }
        }
    }
    Profiler.startSection("user_input")
    imshow("img", dest)
    cvWaitKey()
    Profiler.endSection()
}