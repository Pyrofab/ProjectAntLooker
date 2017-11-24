package fr.antproject.shapes

import fr.antproject.utils.Point
import org.bytedeco.javacpp.opencv_core

class Rectangle(private val x: Int, private val y: Int, private val width: Int, private val height: Int):
        Polygon(listOf(Point(x, y), Point(x + width, y), Point(x + width, y + height), Point(x, y + height))) {

    override fun toString(): String = "Rectangle(x=$x, y=$y, width=$width, height=$height)"

    fun toOpencvRect(): opencv_core.Rect = opencv_core.Rect(x, y, width, height)
}

fun Polygon.isRectangle(): Boolean {
    if (this is Rectangle) return true
    if(this.nbPoints() != 4)
        return false
    val lengthAB = this[0] distTo this[1]
    var lengthAC = this[0] distTo this[3]
    var lengthBC = this[1] distTo this[3]
    var angleBAC = Math.acos((lengthAB * lengthAB + lengthAC * lengthAC - lengthBC * lengthBC) / (2*lengthAB*lengthAC))
    if(angleBAC < Math.PI * 0.45 || angleBAC > Math.PI * 0.55)
        return false
    lengthAC = this[1] distTo this[2]
    lengthBC = this[2] distTo this[0]
    angleBAC = Math.acos((lengthAB * lengthAB + lengthAC * lengthAC - lengthBC * lengthBC) / (2*lengthAB*lengthAC))
    if(angleBAC < Math.PI * 0.45 || angleBAC > Math.PI * 0.55)
        return false
    return true
}