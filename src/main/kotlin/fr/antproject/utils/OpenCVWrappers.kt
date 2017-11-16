package fr.antproject.utils

import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.opencv_core

class Point(ptr: Pointer) : opencv_core.Point(ptr) {
    override fun toString(): String = "${javaClass.name}[x=${this.x()},y=${this.y()}]"
}

class MatVector : opencv_core.MatVector(), Iterable<opencv_core.Mat> {
    override fun iterator(): Iterator<opencv_core.Mat> {
        return object: Iterator<opencv_core.Mat> {
            var index = 0L
            override fun hasNext() = index < size()
            override fun next() = get(index++)
        }
    }

    override fun toString(): String = "${javaClass.name}[size=${this.size()}]"
}

class ImageMat(ptr : Pointer?, imgTransformFlags: Int = 0) : opencv_core.Mat(ptr) {
    constructor(img : opencv_core.IplImage) : this(opencv_core.Mat(img))
    constructor(imgTransformFlags: Int = 0) : this(opencv_core.Mat(), imgTransformFlags)
    var imgTransformFlags = imgTransformFlags
        private set

    fun hasTransform(transformOP: EnumImgTransforms) = (imgTransformFlags and transformOP.flag > 0)

    /**
     * Adds an image transformation to this matrix
     * @param transformOP A transformation that has been applied to this matrix
     */
    fun addTransform(transformOP: EnumImgTransforms) {
        imgTransformFlags = imgTransformFlags or transformOP.flag
    }

    /**
     * @return a list containing all image transformations that have been applied to this matrix
     */
    private fun getTransforms() = EnumImgTransforms.values().filter { hasTransform(it) }

    override fun toString(): String {
        return if (isNull) {
            super.toString()
        } else {
            try {
                "${javaClass.name}[transforms=${getTransforms()},width=${arrayWidth()},height=${arrayHeight()},depth=${arrayDepth()},channels=${arrayChannels()}]"
            } catch (e: Exception) {
                println(e.message)
                super.toString()
            }
        }
    }
}

enum class EnumImgTransforms(val flag: Int) {
    NO_TRANSFORMATION(0),
    GRAY(0b1),
    THRESHOLD(0b10)
}