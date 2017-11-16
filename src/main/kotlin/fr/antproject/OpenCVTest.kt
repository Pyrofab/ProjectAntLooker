package fr.antproject

import fr.antproject.utils.*
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui.cvShowImage
import org.bytedeco.javacpp.opencv_highgui.cvWaitKey
import org.bytedeco.javacpp.opencv_imgproc

fun main(args: Array<String>) {
    val fileName = if(args.size > 1) args[0] else "poly.jpg"
    val img = loadImage(fileName)
    val imgMat = ImageMat(img)
    val processedContours = processContours(imgMat.grayImage().threshold().findContours())
    for(i in 0 until processedContours.size) {
        println("Shape #$i")
        processedContours[i].forEach {
            println(it)
            opencv_imgproc.drawMarker(imgMat,opencv_core.Point(it.x(),it.y()), opencv_core.Scalar( 0.0,255.0,0.0,1.0),0,20,1,8)
        }
    }

    cvShowImage("img",img)
    cvWaitKey()
}

