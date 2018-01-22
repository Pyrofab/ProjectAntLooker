package fr.antproject.utils.wrappers

import fr.antproject.application.Logger
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.opencv_core

class Point(ptr: Pointer) : opencv_core.Point(ptr) {
    constructor(x: Int, y: Int) : this(opencv_core.Point(x, y))

    override fun toString() = "${javaClass.simpleName}[x=${this.x()},y=${this.y()}]"

    operator fun plus(other: Point) = Point(x() + other.x(), y() + other.y())
    operator fun minus(other: Point) = Point(x() - other.x(), y() - other.y())
    operator fun times(mul: Int) = Point(x() * mul, y() * mul)
    operator fun div(denominator: Int) = Point(x() / denominator, y() / denominator)
    infix fun distTo(other: Point) = Math.sqrt(Math.pow((x() - other.x()).toDouble(), 2.0) + Math.pow((y() - other.y()).toDouble(), 2.0))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Point

        if (x() != other.x()) return false
        if (y() != other.y()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + x()
        result = 31 * result + y()
        return result
    }
}

class MatVector : opencv_core.MatVector(), Iterable<opencv_core.Mat> {
    override fun iterator(): kotlin.collections.Iterator<opencv_core.Mat> {
        return object : kotlin.collections.Iterator<opencv_core.Mat> {
            var index = 0L
            override fun hasNext() = index < size()
            override fun next() = get(index++)
        }
    }

    override fun toString(): String = "${javaClass.name}[size=${this.size()}]"
}

/**
 * A wrapper for [opencv_core.Mat], specifically for representing images
 *
 * Stores the various transformations that have been applied to the represented displayedImage
 * @constructor Creates an displayedImage matrix by casting the given pointer. Also sets transformations flags
 */
class ImageMat(ptr: Pointer?, imgTransformFlags: Int = 0) : opencv_core.Mat(ptr) {
    /**
     * Creates an displayedImage matrix from the given displayedImage object.
     * @param img the displayedImage that this matrix will represent
     */
    constructor(img: opencv_core.IplImage) : this(opencv_core.Mat(img))

    /**
     * Creates an empty displayedImage matrix with the given transformation flags
     */
    constructor(imgTransformFlags: Int = 0) : this(opencv_core.Mat(), imgTransformFlags)

    var imgTransformFlags = imgTransformFlags
        private set

    /**
     * @return true if this matrix has received the given transformation
     */
    fun hasTransform(transformOP: EnumImgTransforms) = (imgTransformFlags and transformOP.flag > 0)

    /**
     * Adds an displayedImage transformation to this matrix
     * @param transformOP A transformation that has been applied to this matrix
     */
    fun addTransform(transformOP: EnumImgTransforms) {
        imgTransformFlags = imgTransformFlags or transformOP.flag
    }

    /**
     * @return a list containing all displayedImage transformations that have been applied to this matrix
     */
    private fun getTransforms() = EnumImgTransforms.values().filter { hasTransform(it) }

    override fun toString(): String {
        return if (isNull) {
            super.toString()
        } else {
            try {
                "${javaClass.name}[transforms=${getTransforms()},width=${arrayWidth()},height=${arrayHeight()},depth=${arrayDepth()},channels=${arrayChannels()}]"
            } catch (e: Exception) {
                Logger.error("Exception while printing information for an displayedImage matrix: ${e.message}")
                super.toString()
            }
        }
    }
}

/**
 * Represents the various transformations that can be applied to an displayedImage matrix
 */
enum class EnumImgTransforms(val flag: Int) {
    NO_TRANSFORMATION(0),
    GRAY(0b1),
    THRESHOLD(0b10)
}