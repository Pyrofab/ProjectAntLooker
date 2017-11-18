package fr.antproject.shapes

import fr.antproject.utils.Point

abstract class Shape {
    abstract infix fun isInside(other: Shape): Boolean
}

class Circle(val center: Point, val radius: Int): Shape() {
    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String = "Circle(center=$center, radius=$radius)"

}

open class Polygon(private val vertices: List<Point>): Shape(), Iterable<Point> {
    override fun iterator(): Iterator<Point> = vertices.iterator()

    operator fun plus(vertex: Point): Polygon = Polygon(vertices + vertex)

    private fun nbPoints() = vertices.size

    override fun isInside(other: Shape): Boolean {
        TODO("not implemented")
    }

    override fun toString(): String = "Polygon(nbPoints=${nbPoints()}, vertices=$vertices)"
}

class Rectangle(val x: Int, val y: Int, val width: Int, val height: Int):
        Polygon(listOf(Point(x, y), Point(x+width, y), Point(x+width, y+height), Point(x, y+height))) {
    override fun toString(): String = "Rectangle(x=$x, y=$y, width=$width, height=$height)"
}
