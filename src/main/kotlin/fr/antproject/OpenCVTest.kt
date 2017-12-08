package fr.antproject

import fr.antproject.shapes.*
import fr.antproject.utils.*
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
    val processedContours = processContours(src.grayImage().threshold(optional=ThresholdTypesOptional.OTSU).findContours())
    for(i in 0 until processedContours.size) {
        val shape = processedContours[i]
        Logger.debug("Shape #$i: $shape")
        when (shape) {
            is Polygon -> when (shape) {
                is DrawnRectangle -> shape.forEach {
                    opencv_imgproc.drawMarker(dest, it,
                            Color.BLUE.toScalar(),0,20,1,8)
                }
                is DrawnCircle -> {
                    opencv_imgproc.circle(dest, shape.approx.center, 3, Color.GREEN.toScalar(), -1, 8, 0)
                    opencv_imgproc.circle(dest, shape.approx.center, shape.approx.radius, Color.RED.toScalar(), 3, 8, 0)
                }
                else -> for(j in 0 until shape.nbPoints()) {
                    opencv_imgproc.drawMarker(dest, shape[j],
                            Color.GREEN.toScalar(),0,20,1,8)
                    opencv_imgproc.putText(dest, "$j", shape[j], 0, 1.0, Color.ORANGE.toScalar())
                }
            }
            is Circle -> {
                opencv_imgproc.circle(dest, shape.center, 3, Color.GREEN.toScalar(), -1, 8, 0)
                opencv_imgproc.circle(dest, shape.center, shape.radius, Color.RED.toScalar(), 3, 8, 0)
            }
            is Arrow -> {
                opencv_imgproc.arrowedLine(dest, shape.startPoint, shape.lastPoint, Color.ORANGE.toScalar())
            }
        }
    }
    imshow("img",dest)
    cvWaitKey()
}