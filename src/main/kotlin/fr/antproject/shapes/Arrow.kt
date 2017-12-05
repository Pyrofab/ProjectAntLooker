package fr.antproject.shapes

import fr.antproject.utils.Point

class Arrow(val startPoint: Point, val lastPoint: Point): Shape() {

    override fun contains(point: Point): Boolean {
        return point == startPoint || point == lastPoint
    }

    override fun isInside(other: Shape): Boolean = other contains startPoint && other contains lastPoint
}