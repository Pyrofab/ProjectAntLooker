package fr.antproject.utils

import org.bytedeco.javacpp.FloatPointer
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgproc


fun loadAndAnalyseImage(fileLocation: String) : List<List<Point>> =
        processContours((ImageMat(loadImage(fileLocation)).grayImage().threshold().findContours()))

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

fun detectCircles(gray: ImageMat): List<Pair<Point, Int>> {
    if(!gray.hasTransform(EnumImgTransforms.GRAY))
        throw IllegalArgumentException("The source image must use 8 color channels. Use ImageMat#grayImage first.")
    opencv_imgproc.GaussianBlur(gray, gray, opencv_core.Size(9,9), 2.0, 2.0, opencv_imgproc.CV_GAUSSIAN)
    val circlesVector = ImageMat()
    opencv_imgproc.HoughCircles(gray, circlesVector, opencv_imgproc.CV_HOUGH_GRADIENT, 1.0,
            gray.rows()/8.0, 200.0, 100.0, 0,0)
    val ret = mutableListOf<Pair<Point, Int>>()
    println(circlesVector)
    for(i in 0 until circlesVector.size().height()) {
        val center = Point(
                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(0)),
                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(1)))
        val radius = opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(2))
        ret += center to radius
    }
    return ret
}