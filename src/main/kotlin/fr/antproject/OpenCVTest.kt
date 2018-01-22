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
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgproc
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.javacv.Java2DFrameConverter
import java.util.logging.Level


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
        Logger.log(Level.FINER, "Shape #$i: $shape")
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
                    shape.forEach {
                        opencv_imgproc.drawMarker(dest, it, Color.GREEN, 0, 20, 1, 8)
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
    //imshow("img", dest)

    val iplConverter = OpenCVFrameConverter.ToIplImage()
    val matConverter = OpenCVFrameConverter.ToMat()
    val frame = matConverter.convert(dest)
    val img = iplConverter.convert(frame)
    val result = img.clone()
    img.release()
    val grabberConverter = OpenCVFrameConverter.ToIplImage()
    val paintConverter = Java2DFrameConverter()
    val ptdr = grabberConverter.convert(result)
    TaskConfigReload.displayedImage = SwingFXUtils.toFXImage(paintConverter.getBufferedImage(ptdr, 1.0),null)

    //cvWaitKey()
    Profiler.endSection()
}
