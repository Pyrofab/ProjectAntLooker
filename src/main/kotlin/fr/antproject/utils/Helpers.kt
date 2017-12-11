@file:JvmName("ProcessingHelpers")

package fr.antproject.utils

import fr.antproject.application.Logger
import fr.antproject.model.shapes.Circle
import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.Shape
import fr.antproject.model.shapes.drawn.DrawnArrow
import fr.antproject.model.shapes.drawn.DrawnCircle
import fr.antproject.model.shapes.drawn.DrawnRectangle
import fr.antproject.utils.wrappers.EnumImgTransforms
import fr.antproject.utils.wrappers.ImageMat
import fr.antproject.utils.wrappers.MatVector
import fr.antproject.utils.wrappers.Point
import org.bytedeco.javacpp.FloatPointer
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_highgui
import org.bytedeco.javacpp.opencv_imgproc

/**
 * Attempts to convert a vector of extracted contours into a list of appropriate shapes
 *
 * @param contours a vector of contours extracted from an image matrix
 */
fun processContours(contours: MatVector): Collection<Shape> =
        (filterDuplicates(extractPolys(contours)).map {
            DrawnCircle.getFromPoly(it)
                    ?: DrawnRectangle.getFromPoly(it)
                    ?: DrawnArrow.getFromPoly(it)
                    ?: it
        })

/**
 * Attempts to convert contours extracted from an image into proper polygons
 *
 * @param contours a vector of contours extracted from an image matrix
 */
fun extractPolys(contours: MatVector): Collection<Polygon> {
    val ret = mutableListOf<Polygon>()
    for (shape in contours) {
        val approx = opencv_core.Mat()
        opencv_imgproc.approxPolyDP(shape, approx, 0.01 * opencv_imgproc.arcLength(shape, true), true)
        for (row in 0 until approx.size().width()) {
            var polygon = Polygon()
            for (col in 0 until approx.size().height())
                polygon += Point(approx.ptr(row, col))
            ret += polygon
        }
    }
    return ret
}

const val MAX_FUSE_DISTANCE = 13.5

/**
 * Attempts to remove any shape that has been detected twice
 *
 * For each polygon extracted from the source image, checks if there's another polygon that has vertices
 * closer than the [MAX_FUSE_DISTANCE].
 *
 * @param polys a list of unrefined polygons extracted from an image
 */
fun filterDuplicates(polys: Collection<Polygon>): Collection<Polygon> {
    val temp = polys.toMutableList()
    var i = -1
    while (++i < temp.size) {        // can't use an iterator as the underlying list changes over time
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
    if (!gray.hasTransform(EnumImgTransforms.GRAY))
        throw IllegalArgumentException("The source image must use 8 color channels. Use ImageMat#grayImage first.")
    opencv_imgproc.GaussianBlur(gray, gray, opencv_core.Size(9, 9), 2.0, 2.0, opencv_imgproc.CV_GAUSSIAN)
    opencv_highgui.imshow("img", gray)
    opencv_highgui.cvWaitKey()
    val circlesVector = ImageMat()
    opencv_imgproc.HoughCircles(gray, circlesVector, opencv_imgproc.CV_HOUGH_GRADIENT, 1.0,
            gray.rows() / 8.0, 200.0, 100.0, 0, 0)
    val ret = mutableListOf<Circle>()
    for (i in 0 until circlesVector.size().height()) {
        val center = Point(
                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(0)),
                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(1)))
        val radius = opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(2))
        ret += Circle(center, radius)
    }
    return ret
}
