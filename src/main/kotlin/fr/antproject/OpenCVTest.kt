package fr.antproject

import fr.antproject.utils.*
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui.cvShowImage
import org.bytedeco.javacpp.opencv_highgui.cvWaitKey
import org.bytedeco.javacpp.opencv_imgproc
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val fileName = if(args.size > 1) args[0] else "petri_resize.png"
    val img = loadImage(fileName) ?: throw FileNotFoundException("$fileName does not exist")
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

//fun loadAndAnalyseImage(fileLocation: String) : List<List<opencv_core.fr.antproject.utils.Point>> {
//    return fr.antproject.utils.processContours((fr.antproject.utils.ImageMat(fr.antproject.utils.loadImage(fileLocation) ?: return listOf()).fr.antproject.utils.grayImage().fr.antproject.utils.threshold().fr.antproject.utils.findContours()))
//}

