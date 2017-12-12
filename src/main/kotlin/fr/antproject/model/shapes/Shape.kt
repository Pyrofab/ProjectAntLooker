package fr.antproject.model.shapes

import fr.antproject.utils.wrappers.Point

/**
 * Interface providing a definition for objects representing some form of geometric shape
 */
interface Shape {
    /**
     * @param point a point for which to check if it is contained in this shape
     * @return true if the point is inside this shape according to its definition
     */
    infix fun contains(point: Point): Boolean

    /**
     * @param other another shape for which to check if it contains this one
     * @return true if the entirety of this shape is inside the other according to their respective definition
     */
    infix fun isInside(other: Shape): Boolean

    fun getArea() = 0.0
}

