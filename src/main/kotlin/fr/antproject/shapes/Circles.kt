@file: JvmName("Circles")

package fr.antproject.shapes

import fr.antproject.utils.*

class DrawnCircle private constructor(drawnShape: Polygon, val approx: Circle): Polygon(drawnShape) {

    companion object CircleConverter {
        /**The minimum amount of points a polygon must have to be considered a circle*/
        const val MIN_POINTS = 6
        /**The relative minimum distance a point may be to the center to be considered on the circle*/
        const val MIN_DISTANCE = 0.8
        /**The relative maximum distance a point may be to the center to be considered on the circle*/
        const val MAX_DISTANCE = 1.2
        /**The minimum fraction of points in range to be considered a circle*/
        const val MIN_IN_RANGE = 0.8

        /**
         * @param shape a polygon to analyse
         * @return the approximated circle or null if the shape isn't a valid circle
         */
        fun getCircleFromPoly(shape: Polygon): DrawnCircle? {
            if(shape.nbPoints() < MIN_POINTS) return null
            val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()
            val distances = shape.map { it distTo average }
            val averageDistance = distances.reduce { p0, p1 -> p0 + p1 } / distances.size

            val min = averageDistance * MIN_DISTANCE
            val max = averageDistance * MAX_DISTANCE

            val inRange = distances.filter { it > min && it < max }.size

            return if((inRange / shape.nbPoints()) > MIN_IN_RANGE) DrawnCircle(shape, Circle(average, averageDistance.toInt())) else null
        }
    }

    override fun toString(): String = "DrawnCircle(vertices=$vertices, approx=$approx)"
}

class Circle(val center: Point, val radius: Int): Shape() {

    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(point: Point): Boolean = center distTo point < radius

    override fun toString(): String = "Circle(center=$center, radius=$radius)"
}