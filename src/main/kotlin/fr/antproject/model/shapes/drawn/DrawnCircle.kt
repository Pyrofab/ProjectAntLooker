package fr.antproject.model.shapes.drawn

import fr.antproject.model.shapes.Circle
import fr.antproject.model.shapes.Polygon

/**
 * Class describing a drawn shape matching a circle
 *
 * @property approx the perfect circle closest to this shape
 */
class DrawnCircle private constructor(drawnShape: Polygon, override val approx: Circle) : DrawnShape(drawnShape, approx) {

    companion object CircleConverter : IShapeConverter {
        /**The minimum amount of points a polygon must have to be considered a circle*/
        private const val MIN_POINTS = 6
        /**The relative minimum distance a point may be to the center to be considered on the circle*/
        private const val MIN_DISTANCE = 0.8
        /**The relative maximum distance a point may be to the center to be considered on the circle*/
        private const val MAX_DISTANCE = 1.2
        /**The minimum fraction of points in range to be considered a circle*/
        private const val MIN_IN_RANGE = 0.8

        /**
         * @param shape a polygon to analyse
         * @return the approximated circle or null if the shape isn't a valid circle
         */
        override fun getFromPoly(shape: Polygon): DrawnCircle? {
            if (shape is DrawnCircle) return shape

            if (shape.nbPoints() < MIN_POINTS) return null
            val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()
            val distances = shape.map { it distTo average }
            val averageDistance = distances.reduce { p0, p1 -> p0 + p1 } / distances.size

            val min = averageDistance * MIN_DISTANCE
            val max = averageDistance * MAX_DISTANCE

            val inRange = distances.filter { it > min && it < max }.size

            return if ((inRange / shape.nbPoints()) > MIN_IN_RANGE) DrawnCircle(shape, Circle(average, averageDistance.toInt())) else null
        }
    }
}
