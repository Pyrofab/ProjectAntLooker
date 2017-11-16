package fr.antproject.utils

import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgproc


fun loadAndAnalyseImage(fileLocation: String) : List<List<Point>> =
        processContours((fr.antproject.utils.ImageMat(fr.antproject.utils.loadImage(fileLocation)).grayImage().threshold().findContours()))

fun processContours(contours: MatVector) : List<List<Point>> {
    val ret = mutableListOf<List<Point>>()
    for (shape in contours) {
        val approx = opencv_core.Mat()
        opencv_imgproc.approxPolyDP(shape, approx, 0.01 * opencv_imgproc.arcLength(shape, true), true)
        for (row in 0 until approx.size().width()) {
            var contoursList : List<Point> = listOf()
            for (col in 0 until approx.size().height())
                contoursList += Point(approx.ptr(row, col))
            ret += contoursList
        }
    }
    return ret
}
