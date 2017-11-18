package fr.antproject.shapes

import fr.antproject.utils.Point

open class Polygon(private val vertices: List<Point>): Shape(), Iterable<Point> {
    override fun iterator(): Iterator<Point> = vertices.iterator()

    operator fun plus(vertex: Point): Polygon = Polygon(vertices + vertex)

    private fun nbPoints() = vertices.size

    override fun isInside(other: Shape): Boolean {
        TODO("not implemented")
    }

    override fun toString(): String = "Polygon(nbPoints=${nbPoints()}, vertices=$vertices)"
}