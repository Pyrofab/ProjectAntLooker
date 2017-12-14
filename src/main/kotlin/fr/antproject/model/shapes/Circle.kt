@file: JvmName("Circles")

package fr.antproject.model.shapes

import fr.antproject.utils.wrappers.Point
import java.lang.Math.PI
import kotlin.math.PI

/**
 * Class describing a perfect circle
 *
 * @property center the center of this circle
 * @property radius the radius of this circle
 */
class Circle(val center: Point, val radius: Int) : Shape {

    override fun isInside(other: Shape): Boolean {
        if (other is Circle) return (other.center distTo this.center) < other.radius - this.radius
        return !(other isInside this) && other contains center
    }

    override fun contains(point: Point): Boolean = center distTo point < radius

    override fun toString(): String = "Circle(center=$center, radius=$radius)"

    override fun getArea() = PI*radius*radius

}