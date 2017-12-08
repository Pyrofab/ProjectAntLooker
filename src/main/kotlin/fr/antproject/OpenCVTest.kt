package fr.antproject

import fr.antproject.model.shapes.*
import fr.antproject.model.shapes.drawnshapes.DrawnArrow
import fr.antproject.model.shapes.drawnshapes.DrawnCircle
import fr.antproject.model.shapes.drawnshapes.DrawnRectangle
import fr.antproject.utils.*
import fr.antproject.utils.wrappers.*
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
    val processedContours = processContours(src.grayImage().threshold(optional= ThresholdTypesOptional.OTSU).findContours())
    processedContours.forEachIndexed { i, shape ->
        Logger.debug("Shape #$i: $shape")
        when (shape) {
            is Polygon -> when (shape) {
                is DrawnRectangle -> shape.forEach {
                    opencv_imgproc.drawMarker(dest, it,
                            Color.BLUE,0,20,1,8)
                }
                is DrawnCircle -> {
                    opencv_imgproc.circle(dest, shape.approx.center, 3, Color.GREEN, -1, 8, 0)
                    opencv_imgproc.circle(dest, shape.approx.center, shape.approx.radius, Color.RED, 3, 8, 0)
                }
                is DrawnArrow -> {
                    opencv_imgproc.arrowedLine(dest, shape.approx.startPoint, shape.approx.lastPoint, Color.ORANGE)
                    Logger.debug("Closest shape: ${processedContours.getClosestShape(shape.approx.lastPoint)}")
                }
                else -> shape.forEach { opencv_imgproc.drawMarker(dest, it,
                        Color.GREEN,0,20,1,8) }
            }
            is Circle -> {
                opencv_imgproc.circle(dest, shape.center, 3, Color.GREEN, -1, 8, 0)
                opencv_imgproc.circle(dest, shape.center, shape.radius, Color.RED, 3, 8, 0)
            }
        }
    }
    imshow("img",dest)
    cvWaitKey()
}