package fr.antproject

import fr.antproject.utils.*
import org.bytedeco.javacpp.*
import org.bytedeco.javacpp.opencv_highgui.*

fun main(args: Array<String>) {
    val fileName = if(args.size > 1) args[0] else "poly.jpg"
    val src = ImageMat(loadImage(fileName))
    val dest = ImageMat(loadImage(fileName))
    val processedContours = processContours(src.grayImage().threshold().findContours())
    for(i in 0 until processedContours.size) {
        println("Shape #$i")
        processedContours[i].forEach {
            println(it)
            opencv_imgproc.drawMarker(dest,opencv_core.Point(it.x(),it.y()),
                    opencv_core.Scalar( 0.0,255.0,0.0,1.0),0,20,1,8)
        }
    }
    for(circle in detectCircles(src.grayImage())) {
        val center = circle.first
        val radius = circle.second
        println("circle: $center $radius")
        opencv_imgproc.circle(dest, center, 3, opencv_core.Scalar(0.0, 255.0, 0.0, 0.0), -1, 8, 0)
        opencv_imgproc.circle(dest, center, radius, opencv_core.Scalar(0.0, 0.0, 255.0, 0.0), 3, 8, 0)
    }
    imshow("img",dest)
    cvWaitKey()
}

