import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgcodecs
import org.bytedeco.javacpp.opencv_imgproc

fun main(args: Array<String>) {
    val processedContours = loadAndAnalyseImage("square.png")
    for(i in 0 until processedContours.size) {
        println("Shape #$i")
        processedContours[i].forEach { println(pointToString(it)) }
    }
}

fun loadAndAnalyseImage(fileLocation: String) : List<List<opencv_core.Point>> {
    val img = loadImage(fileLocation)
    val imgMat = imgToMat(img)
    val gray = grayImage(imgMat)
    val thresh = threshold(gray, 127.0, 255.0, ThresholdTypes.BINARY_INVERTED)
    val contours = findContours(thresh, ContourRetrievalMode.LIST, ContourApproxMethod.SIMPLE)
    return processContours(contours)
}

fun loadImage(location: String) = opencv_imgcodecs.cvLoadImage(location)!!

fun imgToMat(img: opencv_core.IplImage) = ImageMat(img)

fun grayImage(img : ImageMat) : GrayImageMat {
    val gray = GrayImageMat()
    opencv_imgproc.cvtColor(img, gray, opencv_imgproc.COLOR_BGR2GRAY)
    return gray
}

fun threshold(grayImage: GrayImageMat, threshold: Double, maxValue: Double, type: ThresholdTypes): ThresholdImageMat {
    val ret = ThresholdImageMat()
    opencv_imgproc.threshold(grayImage, ret, threshold, maxValue, type.value)
    return ret
}

fun findContours(thresholdImageMat: ThresholdImageMat, mode: ContourRetrievalMode, method: ContourApproxMethod): MatVector {
    val ret = MatVector()
    opencv_imgproc.findContours(thresholdImageMat, ret, mode.value, method.value)
    return ret
}

fun processContours(contours: MatVector) : List<List<opencv_core.Point>> {
    val ret = mutableListOf<List<opencv_core.Point>>()
    for (i in 0 until contours.size()) {
        val shape = contours[i]
        val approx = opencv_core.Mat()
        opencv_imgproc.approxPolyDP(shape, approx, 0.01 * opencv_imgproc.arcLength(shape, true), true)
        for (row in 0 until approx.size().width()) {
            var contoursList : List<opencv_core.Point> = listOf()
            for (col in 0 until approx.size().height())
                contoursList += opencv_core.Point(approx.ptr(row, col))
            ret += contoursList
        }
    }
    return ret
}

fun pointToString(point: opencv_core.Point) = "opencv_core.Point[x=${point.x()},y=${point.y()}]"

operator fun opencv_core.Mat.get(row: Int, col: Int) = opencv_core.Point(this.ptr(row, col))

class ImageMat(img : opencv_core.IplImage) : opencv_core.Mat(img)

class GrayImageMat : opencv_core.Mat()

class ThresholdImageMat : opencv_core.Mat()

class MatVector : opencv_core.MatVector()