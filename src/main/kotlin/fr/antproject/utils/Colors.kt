package fr.antproject.utils

import org.bytedeco.javacpp.opencv_core

class Color private constructor(val rgba: Int) {
    companion object {
        val WHITE = Color(255, 255, 255)
        val RED = Color(255, 0, 0)
        val GREEN = Color(0, 255, 0)
        val BLUE = Color(0, 0, 255)
        val BLACK = Color(0, 0, 0)
    }

    constructor(r: Int, g: Int, b: Int, a:Int = 0xFF) : this
            (a and 0xFF shl 24 or
            (r and 0xFF shl 16) or
            (g and 0xFF shl 8) or
            (b and 0xFF shl 0))

    constructor(rgba: Int, hasAlpha: Boolean = false): this(if(hasAlpha) rgba else rgba or -0x1000000)

    fun getAlpha() = rgba shr 24 and 0xFF

    fun getRed() = rgba shr 16 and 0xFF

    fun getGreen() = rgba shr 8 and 0xFF

    fun getBlue() = rgba and 0xFF

    fun toScalar(): opencv_core.Scalar = opencv_core.Scalar(getBlue().toDouble(), getGreen().toDouble(),
            getRed().toDouble(), getAlpha().toDouble())
}