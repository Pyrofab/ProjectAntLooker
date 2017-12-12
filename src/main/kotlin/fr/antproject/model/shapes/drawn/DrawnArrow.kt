package fr.antproject.model.shapes.drawn

import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.StraightArrow
import fr.antproject.utils.areAligned
import fr.antproject.utils.wrappers.Point

/**
 * Class describing a drawn shape matching an arrow
 */
class DrawnArrow private constructor(drawnShape: Polygon, override val approx: StraightArrow) : DrawnShape(drawnShape, approx) {

    companion object ArrowConverter : ShapeConverter {

        /**
         * @param shape a polygon to analyse
         * @return the approximated arrow or null if the shape isn't a valid arrow
         */
        override fun getFromPoly(shape: Polygon): DrawnArrow? {
            if (shape.nbPoints() < 2) return null
            val average = shape.reduce { p, p1 -> p + p1 } / shape.nbPoints()

            val furthest = shape.maxBy { it distTo average } ?: return null
            if (!isEndOfLine(furthest, shape)) return null
            val head = shape.maxBy { it distTo furthest } ?: return null

            return DrawnArrow(shape, StraightArrow(furthest, head))
        }

        private fun isEndOfLine(vertex: Point, shape: Polygon): Boolean {
            val index = shape.indexOf(vertex)
            val prev = shape[(if (index == 0) shape.nbPoints() else index) - 1]
            val next = shape[(if (index == shape.nbPoints() - 1) 0 else index + 1)]

            return areAligned(vertex, prev, next, 0.1) && (prev distTo vertex) > (prev distTo next)
        }
    }
}
