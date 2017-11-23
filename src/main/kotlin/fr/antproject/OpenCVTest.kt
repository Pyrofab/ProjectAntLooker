package fr.antproject

import fr.antproject.shapes.Circle
import fr.antproject.shapes.Polygon
import fr.antproject.utils.*
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui.cvWaitKey
import org.bytedeco.javacpp.opencv_highgui.imshow
import org.bytedeco.javacpp.opencv_imgproc

fun main(args: Array<String>) {
    AntLookerApp.main(args)
}

fun test(fileName: String) {
    Logger.info("Loading image at location $fileName")
    val src = ImageMat(loadImage(fileName))
    val dest = ImageMat(loadImage(fileName))
    val processedContours = processContours(src.grayImage().threshold().findContours())
    for(i in 0 until processedContours.size) {
        val shape = processedContours[i]
        Logger.debug("Shape #$i: $shape")
        if(shape is Polygon) {
            shape.forEach {
                opencv_imgproc.drawMarker(dest, it,
                        opencv_core.Scalar( 0.0,255.0,0.0,1.0),0,20,1,8)
            }
        } else if(shape is Circle) {
            opencv_imgproc.circle(dest, shape.center, 3, opencv_core.Scalar(0.0, 255.0, 0.0, 0.0), -1, 8, 0)
            opencv_imgproc.circle(dest, shape.center, shape.radius, opencv_core.Scalar(0.0, 0.0, 255.0, 0.0), 3, 8, 0)
        }
    }
    imshow("img",dest)
    cvWaitKey()
}