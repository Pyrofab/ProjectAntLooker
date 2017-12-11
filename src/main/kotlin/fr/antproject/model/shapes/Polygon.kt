package fr.antproject.model.shapes

import fr.antproject.utils.doIntersect
import fr.antproject.utils.onSegment
import fr.antproject.utils.orientation
import fr.antproject.utils.wrappers.Point

/**
 * Class describing a closed shape made up of an arbitrary number of line segments
 *
 * @property vertices a list of vertices composing this shape's border,
 * with two consecutive points being the endpoints of a line that is a side of this polygon
 */
open class Polygon(protected val vertices: List<Point>) : Shape, Iterable<Point> by vertices {
    constructor (from: Polygon) : this(from.vertices)
    constructor (vararg vertices: Point) : this(vertices.asList())

    companion object {
        /**Define Infinite (Using INT_MAX caused overflow problems)*/
        const val INF = 10000
    }

    override fun isInside(other: Shape): Boolean = vertices.all { other contains it }

    operator fun get(i: Int): Point = vertices[i]

    operator fun plus(vertex: Point): Polygon = Polygon(vertices + vertex)

    fun nbPoints() = vertices.size

    override fun contains(point: Point): Boolean {
        if (vertices.contains(point)) return true

        val extreme = Point(INF, point.y())
        var count = 0
        var i = 0
        do {
            val next = (i + 1 % nbPoints())
            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(this[i] to this[next], point to extreme)) {
                // If the point 'p' is colinear with line segment 'i-next',
                // then check if it lies on segment. If it lies, return true,
                // otherwise false
                if (orientation(this[i], point, this[next]) == 0)
                    return point.onSegment(this[i], this[next])

                count++
            }
            i = next
        } while (i != 0)
        return count % 2 == 1
    }

    override fun toString(): String = "${javaClass.simpleName}(nbPoints=${nbPoints()}, vertices=$vertices)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Polygon

        if (vertices != other.vertices) return false

        return true
    }

    override fun hashCode(): Int = vertices.hashCode()
}