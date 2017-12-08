package fr.antproject.shapes

import fr.antproject.utils.Point

/**
 * Class describing an arrow connecting two points
 * @property startPoint the starting coordinates of this arrow
 * @property lastPoint the coordinate of this arrow's head
 */
class Arrow(val startPoint: Point, val lastPoint: Point): Shape() {

    companion object ArrowConverter {

        /**
         * @param shape a polygon to analyse
         * @return the approximated arrow or null if the shape isn't a valid arrow
         */
        fun getArrowFromPoly(shape: Polygon): Arrow? {
            if(shape.nbPoints() < 2) return null
            val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()
            val distances = shape.map { it distTo average }

            val furthest = shape.maxBy { it distTo average } ?: return null
            val head = shape.maxBy { it distTo furthest } ?: return null

            return Arrow(furthest, head)
        }
    }

    override fun contains(point: Point): Boolean {
        return point == startPoint || point == lastPoint
    }

    override fun isInside(other: Shape): Boolean = other contains startPoint && other contains lastPoint
}