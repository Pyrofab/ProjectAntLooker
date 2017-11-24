package fr.antproject

import fr.antproject.shapes.Circle
import fr.antproject.shapes.Polygon
import fr.antproject.shapes.Rectangle
import fr.antproject.utils.*
import org.bytedeco.javacpp.opencv_core
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
            if(shape is Rectangle) {
                opencv_imgproc.rectangle(dest, shape.toOpencvRect(), Color.BLUE.toScalar(), 2, 8, 0)
            } else shape.forEach {
                    opencv_imgproc.drawMarker(dest, it,
                            Color.GREEN.toScalar(),0,20,1,8)
                }
        } else if(shape is Circle) {
            opencv_imgproc.circle(dest, shape.center, 3, Color.GREEN.toScalar(), -1, 8, 0)
            opencv_imgproc.circle(dest, shape.center, shape.radius, Color.RED.toScalar(), 3, 8, 0)
        }
    }
    imshow("img",dest)
    cvWaitKey()
}