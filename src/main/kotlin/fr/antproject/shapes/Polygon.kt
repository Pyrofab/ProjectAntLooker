package fr.antproject.shapes

import fr.antproject.utils.Point

open class Polygon(private val vertices: List<Point>): Shape(), Iterable<Point> {
    override fun iterator(): Iterator<Point> = vertices.iterator()

    operator fun plus(vertex: Point): Polygon = Polygon(vertices + vertex)

    fun nbPoints() = vertices.size

    override fun isInside(other: Shape): Boolean {
        TODO("not implemented")
    }

    override fun toString(): String = "Polygon(nbPoints=${nbPoints()}, vertices=$vertices)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Polygon

        if (vertices != other.vertices) return false

        return true
    }

    override fun hashCode(): Int = vertices.hashCode()
}