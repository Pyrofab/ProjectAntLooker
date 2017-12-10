package fr.antproject.model.shapes

import fr.antproject.utils.wrappers.Point
import org.bytedeco.javacpp.opencv_core

class Rectangle(private val x: Int, private val y: Int, private val width: Int, private val height: Int) :
        Polygon(Point(x + width, y), Point(x + width, y + height), Point(x, y + height)) {

    fun toOpencvRect(): opencv_core.Rect = opencv_core.Rect(x, y, width, height)

    override fun toString(): String = "Rectangle(x=$x, y=$y, width=$width, height=$height)"
}
