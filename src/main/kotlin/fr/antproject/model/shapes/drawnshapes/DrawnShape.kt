package fr.antproject.model.shapes.drawnshapes

import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.Shape

/**
 * Class describing an approximate shape that has been recognized in an image
 *
 * @property approx the theoretical shape closest to this shape
 */
abstract class DrawnShape protected constructor(drawnShape: Polygon, open val approx: Shape) : Polygon(drawnShape) {

    /**
     * Interface used to obtain valid drawn shapes from an extracted polygon
     * @see [fr.antproject.utils.processContours]
     */
    interface ShapeConverter {
        /**
         * @param shape the basic shape
         * @return a shape representing the input shape or null if it does not correspond to this shape's definition
         */
        fun getFromPoly(shape: Polygon): DrawnShape?
    }

    override fun toString(): String = "${javaClass.simpleName}(vertices=$vertices, approx=$approx)"
}
