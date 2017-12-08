package fr.antproject.model.shapes.drawnshapes

import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.StraightArrow

/**
 * Class describing a drawn shape matching an arrow
 */
class DrawnArrow private constructor(drawnShape: Polygon, override val approx: StraightArrow): DrawnShape(drawnShape, approx) {

    companion object ArrowConverter : ShapeConverter {

        /**
         * @param shape a polygon to analyse
         * @return the approximated arrow or null if the shape isn't a valid arrow
         * TODO avoid making an arrow out of every possible shape
         */
        override fun getFromPoly(shape: Polygon): DrawnArrow? {
            if(shape.nbPoints() < 2) return null
            val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()

            val furthest = shape.maxBy { it distTo average } ?: return null
            val head = shape.maxBy { it distTo furthest } ?: return null

            return DrawnArrow(shape, StraightArrow(furthest, head))
        }
    }
}
