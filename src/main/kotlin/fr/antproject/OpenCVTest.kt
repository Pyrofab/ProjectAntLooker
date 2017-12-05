package fr.antproject

import fr.antproject.shapes.DrawnCircle
import fr.antproject.shapes.Polygon
import fr.antproject.shapes.DrawnRectangle
import fr.antproject.utils.*
import org.bytedeco.javacpp.opencv_highgui.cvWaitKey
import org.bytedeco.javacpp.opencv_highgui.imshow
import org.bytedeco.javacpp.opencv_imgproc

fun main(args: Array<String>) = AntLookerApp.main(args)

fun test(fileName: String) {
    Logger.info("Loading image at location $fileName")
    val src = ImageMat(loadImage(fileName))
    val dest = ImageMat(loadImage(fileName))
    val processedContours = processContours(src.grayImage().threshold().findContours())
    for(i in 0 until processedContours.size) {
        val shape = processedContours[i]
        Logger.debug("Shape #$i: $shape")
        if(shape is Polygon) {
            when (shape) {
                is DrawnRectangle -> shape.forEach {
                    opencv_imgproc.drawMarker(dest, it,
                            Color.BLUE.toScalar(),0,20,1,8)
                }
                is DrawnCircle -> {
                    opencv_imgproc.circle(dest, shape.approx.center, 3, Color.GREEN.toScalar(), -1, 8, 0)
                    opencv_imgproc.circle(dest, shape.approx.center, shape.approx.radius, Color.RED.toScalar(), 3, 8, 0)
                }
                else -> shape.forEach {
                    opencv_imgproc.drawMarker(dest, it,
                            Color.GREEN.toScalar(),0,20,1,8)
                }
            }
        }
    }
    imshow("img",dest)
    cvWaitKey()
}