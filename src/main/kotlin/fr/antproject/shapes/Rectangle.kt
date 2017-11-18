package fr.antproject.shapes

import fr.antproject.utils.Point

class Rectangle(val x: Int, val y: Int, val width: Int, val height: Int):
        Polygon(listOf(Point(x, y), Point(x + width, y), Point(x + width, y + height), Point(x, y + height))) {
    override fun toString(): String = "Rectangle(x=$x, y=$y, width=$width, height=$height)"
}