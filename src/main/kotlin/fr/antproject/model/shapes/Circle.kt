@file: JvmName("Circles")

package fr.antproject.model.shapes

import fr.antproject.utils.wrappers.Point

/**
 * Class describing a perfect circle
 *
 * @property center the center of this circle
 * @property radius the radius of this circle
 */
class Circle(val center: Point, val radius: Int) : Shape {

    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(point: Point): Boolean = center distTo point < radius

    override fun toString(): String = "Circle(center=$center, radius=$radius)"
}