package fr.antproject.utils

import fr.antproject.shapes.*
import org.bytedeco.javacpp.FloatPointer
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui
import org.bytedeco.javacpp.opencv_imgproc

fun processContours(contours: MatVector) : List<Shape> =
        filterDuplicates(extractPolys(contours)).map { getCircleFromPoly(it) ?:getArrowFromPoly(it) ?: it }

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

/**
 * TODO make this detect and take rotation into account
 * @param shape a polygon to analyse
 * @return the approximated rectangle or null if the shape isn't a valid rectangle
 */
fun getRectangleFromPoly(shape: Polygon): Polygon? {
    if(!shape.isRectangle()) return null
    val x = (shape[0].x() + shape[1].x()) / 2
    val y = (shape[0].y() + shape[3].y()) / 2
    val width = ((shape[0] distTo shape[3]) + (shape[1] distTo shape[2])).toInt() / 2
    val height = ((shape[0] distTo shape[1]) + (shape[3] distTo shape[2])).toInt() / 2
    return Rectangle(x, y, width, height)
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

/**
 * TODO("not implemented")
 * @param shape a polygon to analyse
 * @return the approximated arrow or null if the shape isn't a valid arrow
 */
fun getArrowFromPoly(shape: Polygon): Arrow? {
    return null
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
