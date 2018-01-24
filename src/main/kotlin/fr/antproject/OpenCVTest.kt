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
import fr.antproject.utils.Color.BLUE
import fr.antproject.utils.processContours
import fr.antproject.utils.wrappers.findContours
import javafx.embed.swing.SwingFXUtils
import marvin.image.MarvinImage
import org.bytedeco.javacpp.opencv_imgproc
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.BasicStroke
import java.awt.geom.Ellipse2D
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
    val src = marvin.io.MarvinImageIO.loadImage(fileName)
    findContours(src);
}

fun display(processedContours: Collection<Shape>, dest: MarvinImage) {
    processedContours.forEachIndexed { i, shape ->
        Logger.log(Level.FINER, "Shape #$i: $shape")
        when (shape) {
            is Polygon -> when (shape) {
                is DrawnRectangle -> shape.forEach {
                    dest.drawLine(it.x() - 1, it.y() - 1, it.x() + 1, it.y() + 1, java.awt.Color.BLUE)
                }
                is DrawnCircle -> {
                    val out = dest.getBufferedImage()
                    val g = out.createGraphics()
                    g.setColor(java.awt.Color.RED)
                    val basic = BasicStroke(3f)
                    g.setStroke(basic)
                    val ellipse = Ellipse2D.Double(shape.approx.center.x().toDouble(), shape.approx.center.y().toDouble(), shape.approx.radius.toDouble(), shape.approx.radius.toDouble())
                    g.draw(ellipse)
                }
                is DrawnArrow -> {
                    dest.drawLine(shape.approx.startPoint.x() - 1, shape.approx.startPoint.y() - 1, shape.approx.lastPoint.x() + 1, shape.approx.lastPoint.y() + 1, java.awt.Color.ORANGE)
//                    Logger.debug("Closest shape: ${processedContours.getClosestShape(shape.approx.lastPoint)}")
                }
                else -> {
                    shape
                            .forEach {
                                dest.drawLine(it.x() - 1, it.y() - 1, it.x() + 1, it.y() + 1, java.awt.Color.GREEN)
                            }
                }
            }

        }


        Profiler.startSection("user_input")
        //imshow("img", dest)

       //TODO

        //TaskConfigReload.displayedImage = SwingFXUtils.toFXImage(paintConverter.getBufferedImage(ptdr, 1.0), null)

        //cvWaitKey()
        Profiler.endSection()
    }
}

