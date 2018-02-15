@file:JvmName("ProcessingHelpers")

package fr.antproject.antlookercore.utils

import fr.antproject.antlookercore.application.Logger
import fr.antproject.antlookercore.model.shapes.Polygon
import fr.antproject.antlookercore.model.shapes.Shape
import fr.antproject.antlookercore.model.shapes.drawn.DrawnArrow
import fr.antproject.antlookercore.model.shapes.drawn.DrawnCircle
import fr.antproject.antlookercore.model.shapes.drawn.DrawnRectangle
import fr.antproject.antlookercore.utils.wrappers.Point
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.imgproc.Imgproc

/**
 * Attempts to convert a vector of extracted contours into a list of appropriate shapes
 *
 * @param contours a vector of contours extracted from an displayedImage matrix
 */
fun processContours(contours: List<org.opencv.core.MatOfPoint>): Collection<Shape> =
        (filterDuplicates(extractPolys(contours)).map {
            DrawnCircle.getFromPoly(it)
                    ?: DrawnRectangle.getFromPoly(it)
                    ?: DrawnArrow.getFromPoly(it)
                    ?: it
        })

/**
 * Attempts to convert contours extracted from an displayedImage into proper polygons
 *
 * @param contours a vector of contours extracted from an displayedImage matrix
 */
fun extractPolys(contours: List<MatOfPoint>): Collection<Polygon> {
    val ret = mutableListOf<Polygon>()
    for (shape in contours) {
        val approx = MatOfPoint2f()
        val shape2f = MatOfPoint2f(shape)
        Imgproc.approxPolyDP(shape2f, approx, 0.01 * Imgproc.arcLength(shape2f, true), true)
        val points = approx.toList()
        for (point in points) {
            var polygon = Polygon()
                polygon += Point(point)
            ret += polygon
        }
    }
    return ret
}

const val MAX_FUSE_DISTANCE = 13.5

/**
 * Attempts to remove any shape that has been detected twice
 *
 * For each polygon extracted from the source displayedImage, checks if there's another polygon that has vertices
 * closer than the [MAX_FUSE_DISTANCE].
 *
 * @param polys a list of unrefined polygons extracted from an displayedImage
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

/*
 * Detects perfect circles from a source displayedImage
 * @param gray a matrix representing a gray displayedImage
 * @return a list of circles detected in the displayedImage
 */
//fun detectCircles(gray: ImageMat): List<Circle> {
//    if (!gray.hasTransform(EnumImgTransforms.GRAY))
//        throw IllegalArgumentException("The source displayedImage must use 8 color channels. Use ImageMat#grayImage first.")
//    Imgproc.GaussianBlur(gray, gray, opencv_core.Size(9, 9), 2.0, 2.0, Imgproc.CV_GAUSSIAN)
//    opencv_highgui.imshow("img", gray)
//    opencv_highgui.cvWaitKey()
//    val circlesVector = ImageMat()
//    Imgproc.HoughCircles(gray, circlesVector, Imgproc.CV_HOUGH_GRADIENT, 1.0,
//            gray.rows() / 8.0, 200.0, 100.0, 0, 0)
//    val ret = mutableListOf<Circle>()
//    for (i in 0 until circlesVector.size().height()) {
//        val center = Point(
//                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(0)),
//                opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(1)))
//        val radius = opencv_core.cvRound(FloatPointer(circlesVector.ptr(i, 0)).get(2))
//        ret += Circle(center, radius)
//    }
//    return ret
//}
