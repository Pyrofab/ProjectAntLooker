package fr.antproject.utils

import fr.antproject.shapes.*
import org.bytedeco.javacpp.FloatPointer
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui
import org.bytedeco.javacpp.opencv_imgproc

fun processContours(contours: MatVector) : List<Shape> =
        filterDuplicates(extractPolys(contours)).map { DrawnCircle.getCircleFromPoly(it) ?: DrawnRectangle.getRectangleFromPoly(it) ?: it }

fun extractPolys(contours: MatVector) : List<Polygon> {
    val ret = mutableListOf<Polygon>()
    for (shape in contours) {
        val approx = opencv_core.Mat()
        opencv_imgproc.approxPolyDP(shape, approx, 0.01 * opencv_imgproc.arcLength(shape, true), true)
        for (row in 0 until approx.size().width()) {
            var polygon = Polygon(listOf())
            for (col in 0 until approx.size().height())
                polygon += Point(approx.ptr(row, col))
            ret += polygon
        }
    }
    return ret
}

fun detectArrow(shapes: List<Shape>) {
    shapes.forEach {
        if(it is Polygon) {
            if(!it.isRectangle()) {

            }
        }
    }
}

const val MAX_FUSE_DISTANCE = 13.5

fun filterDuplicates(polys: List<Polygon>): List<Polygon> {
    val temp = polys.toMutableList()
    var i = -1
    while(++i < temp.size) {        // can't use an iterator as the underlying list changes over time
        val polygon = temp[i]
        // For each polygon in the list, we check if it is different from the current polygon and that for each of its points,
        // there exists a point in the current polygon which distance to the first in less than the maximum fuse distance
        if (polys.any { it !== polygon && it.all { point -> polygon.any { point1 -> point distTo point1 < MAX_FUSE_DISTANCE } } }) {
            Logger.debug("Duplicate detected for $polygon")
            temp -= polygon
        }
    }
    return temp
}

/**
 * Detects perfect circles from a source image
 * @param gray a matrix representing a gray image
 * @return a list of circles detected in the image
 */
fun detectCircles(gray: ImageMat): List<Circle> {
    if(!gray.hasTransform(EnumImgTransforms.GRAY))
        throw IllegalArgumentException("The source image must use 8 color channels. Use ImageMat#grayImage first.")
    opencv_imgproc.GaussianBlur(gray, gray, opencv_core.Size(9,9), 2.0, 2.0, opencv_imgproc.CV_GAUSSIAN)
    opencv_highgui.imshow("img", gray)
    opencv_highgui.cvWaitKey()
    val circlesVector = ImageMat()
    opencv_imgproc.HoughCircles(gray, circlesVector, opencv_imgproc.CV_HOUGH_GRADIENT, 1.0,
            gray.rows()/8.0, 200.0, 100.0, 0,0)
    val ret = mutableListOf<Circle>()
    for(i in 0 until circlesVector.size().height()) {
        val center = Point(
                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(0)),
                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(1)))
        val radius = opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(2))
        ret += Circle(center, radius)
    }
    return ret
}
