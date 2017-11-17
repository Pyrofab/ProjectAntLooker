package fr.antproject.shapes

import fr.antproject.utils.Point

abstract class Shape {
    abstract fun getBoundingBox(): Rectangle
    abstract infix fun isInside(other: Shape): Boolean
}

class Circle(val center: Point, val radius: Int): Shape() {
    override fun getBoundingBox() = Rectangle()
    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

open class Polygon: Shape() {
    var bounds: Rectangle? = null
    override fun getBoundingBox(): Rectangle {
        TODO("pas eu le temps lol")
    }

    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Rectangle: Polygon() {
    override fun getBoundingBox(): Rectangle = this
}
