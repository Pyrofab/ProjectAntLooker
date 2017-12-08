package fr.antproject.shapes

import fr.antproject.utils.Point

class Arrow(val startPoint: Point, val lastPoint: Point): Shape() {

    companion object ArrowConverter {

        /**
         * @param shape a polygon to analyse
         * @return the approximated arrow or null if the shape isn't a valid arrow
         */
        fun getArrowFromPoly(shape: Polygon): Arrow? {
            if(shape.nbPoints()<2) return null

            return null
        }
    }

    override fun contains(point: Point): Boolean {
        return point == startPoint || point == lastPoint
    }

    override fun isInside(other: Shape): Boolean = other contains startPoint && other contains lastPoint
}