package fr.antproject.utils

import fr.antproject.shapes.Circle
import fr.antproject.shapes.Polygon
import fr.antproject.shapes.Shape
import org.bytedeco.javacpp.FloatPointer
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui
import org.bytedeco.javacpp.opencv_imgproc

fun processContours(contours: MatVector) : List<Shape> = filterDuplicates(extractPolys(contours)).map { getCircleFromPoly(it) ?: it }

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

/**The minimum amount of points a polygon must have to be considered a circle*/
const val MIN_POINTS = 6
/**The relative minimum distance a point may be to the center to be considered on the circle*/
const val MIN_DISTANCE = 0.8
/**The relative maximum distance a point may be to the center to be considered on the circle*/
const val MAX_DISTANCE = 1.2
/**The minimum fraction of points in range to be considered a circle*/
const val MIN_IN_RANGE = 0.8

/**
 * @param shape a polygon to analyse
 * @return the approximated circle or null if the shape isn't a valid circle
 */
fun getCircleFromPoly(shape: Polygon): Circle? {
    if(shape.nbPoints() < MIN_POINTS) return null
    val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()
    val distances = shape.map { it distTo average }
    val averageDistance = distances.reduce { p0, p1 -> p0 + p1 } / distances.size

    val min = averageDistance * MIN_DISTANCE
    val max = averageDistance * MAX_DISTANCE

    val inRange = distances.filter { it > min && it < max }.size

    return if((inRange / shape.nbPoints()) > MIN_IN_RANGE) Circle(average, averageDistance.toInt()) else null
}

const val MAX_FUSE_DISTANCE = 3.0

fun filterDuplicates(polys: List<Polygon>): List<Polygon> {
    val temp = polys.toMutableList()
    var i = -1
    while(++i < temp.size) {        // can't use an iterator as the underlying list changes over time
        val polygon = temp[i]
        if (polys.any { it !== polygon && it.all { point -> polygon.none { point1 -> point distTo point1 < MAX_FUSE_DISTANCE } } })
        temp -= polygon
    }
    return temp
}

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
