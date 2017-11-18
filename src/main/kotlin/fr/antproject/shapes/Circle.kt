package fr.antproject.shapes

import fr.antproject.utils.Point

class Circle(val center: Point, val radius: Int): Shape() {
    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String = "Circle(center=$center, radius=$radius)"

}