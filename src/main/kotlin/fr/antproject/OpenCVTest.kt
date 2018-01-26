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
import javafx.embed.swing.SwingFXUtils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import java.awt.image.BufferedImage
import java.util.logging.Level
import java.awt.image.DataBufferByte




/**
 * Delegates main method to the javafx application main
 */
fun main(args: Array<String>) = AntLookerApp.main(args)

/**
 * Loads the displayedImage file at the given path and attempts to detect shapes in it
 * @param fileName the path to the displayedImage file
 */
fun test(fileName: String) {
    Logger.info("Loading displayedImage at location $fileName")
    val src = loadImage(fileName)
    val dest = loadImage(fileName)
    val processedContours = processContours(src.grayImage()
            .threshold(threshold = ImageProcessor.config.threshold,optional = ImageProcessor.config.optional)
            .findContours(ImageProcessor.config.mode, ImageProcessor.config.method))
    display(processedContours, dest)
//    val processedContours = ImageProcessor.process(fileName)
}

fun display(processedContours: Collection<Shape>, dest: Mat) {
    processedContours.forEachIndexed { i, shape ->
        Logger.log(Level.FINER, "Shape #$i: $shape")
        when (shape) {
            is Polygon -> when (shape) {
                is DrawnRectangle -> shape.forEach {
                    Imgproc.drawMarker(dest, it,
                            Color.BLUE, 0, 20, 1, 8)
                }
                is DrawnCircle -> {
                    Imgproc.circle(dest, shape.approx.center, 3, Color.GREEN, -1, 8, 0)
                    Imgproc.circle(dest, shape.approx.center, shape.approx.radius, Color.RED, 3, 8, 0)
                }
                is DrawnArrow -> {
                    Imgproc.arrowedLine(dest, shape.approx.startPoint, shape.approx.lastPoint, Color.ORANGE)
//                    Logger.debug("Closest shape: ${processedContours.getClosestShape(shape.approx.lastPoint)}")
                }
                else -> {
                    shape.forEach {
                        Imgproc.drawMarker(dest, it, Color.GREEN, 0, 20, 1, 8)
                    }
                }
            }
            is Circle -> {
                Imgproc.circle(dest, shape.center, 3, Color.GREEN, -1, 8, 0)
                Imgproc.circle(dest, shape.center, shape.radius, Color.RED, 3, 8, 0)
            }
        }
    }
    Profiler.startSection("user_input")
    //imshow("img", dest)

    val bufferedImage = BufferedImage(dest.width(), dest.height(), BufferedImage.TYPE_BYTE_GRAY)
    val data = (bufferedImage.getRaster().getDataBuffer() as DataBufferByte).data
    dest.get(0,0, data)
    TaskConfigReload.displayedImage = SwingFXUtils.toFXImage(bufferedImage,null)

    //cvWaitKey()
    Profiler.endSection()
}
