package fr.antproject.antlookercore.model.shapes

import fr.antproject.antlookercore.utils.wrappers.Point

class Rectangle(private val x: Int, private val y: Int, private val width: Int, private val height: Int) :
        Polygon(Point(x + width, y), Point(x + width, y + height), Point(x, y + height)) {

    override fun toString(): String = "Rectangle(x=$x, y=$y, width=$width, height=$height)"
}
