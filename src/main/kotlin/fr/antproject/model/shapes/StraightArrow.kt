package fr.antproject.model.shapes

import fr.antproject.utils.wrappers.Point

/**
 * Class describing an arrow connecting two points
 */
class StraightArrow(val startPoint: Point, val lastPoint: Point) : Shape {

    override fun contains(point: Point): Boolean {
        return point == startPoint || point == lastPoint
    }

    override fun isInside(other: Shape): Boolean = other contains startPoint && other contains lastPoint

    override fun toString(): String {
        return "StraightArrow(startPoint=$startPoint, lastPoint=$lastPoint)"
    }

}