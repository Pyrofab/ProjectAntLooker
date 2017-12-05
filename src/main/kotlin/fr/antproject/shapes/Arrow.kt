package fr.antproject.shapes

import fr.antproject.utils.Point

class Arrow(val startPoint: Point, val lastPoint: Point): Shape() {
    override fun isInside(other: Shape): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}