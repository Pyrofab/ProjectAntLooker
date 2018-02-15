package fr.antproject.antlookercore

import fr.antproject.antlookercore.application.ImageProcessor
import fr.antproject.antlookercore.application.Logger
import fr.antproject.antlookercore.application.Profiler
import fr.antproject.antlookercore.model.shapes.Circle
import fr.antproject.antlookercore.model.shapes.Polygon
import fr.antproject.antlookercore.model.shapes.Shape
import fr.antproject.antlookercore.model.shapes.drawn.DrawnArrow
import fr.antproject.antlookercore.model.shapes.drawn.DrawnCircle
import fr.antproject.antlookercore.model.shapes.drawn.DrawnRectangle
import fr.antproject.antlookercore.utils.Color
import fr.antproject.antlookercore.utils.processContours
import fr.antproject.antlookercore.utils.wrappers.findContours
import fr.antproject.antlookercore.utils.wrappers.grayImage
import fr.antproject.antlookercore.utils.wrappers.loadImage
import fr.antproject.antlookercore.utils.wrappers.threshold
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
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
    val src = loadImage(fileName)
    val dest = loadImage(fileName)
    val processedContours = processContours(src.grayImage()
            .threshold(threshold = ImageProcessor.config.threshold, optional = ImageProcessor.config.optional)
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

    // FIXME switch to BitMap instead of BufferedImage
    val bufferedImage = BufferedImage(dest.width(), dest.height(), BufferedImage.TYPE_BYTE_GRAY)
    val data = (bufferedImage.getRaster().getDataBuffer() as DataBufferByte).data
    dest.get(0,0, data)
    TaskConfigReload.displayedImage = SwingFXUtils.toFXImage(bufferedImage,null)

    //cvWaitKey()
    Profiler.endSection()
}
