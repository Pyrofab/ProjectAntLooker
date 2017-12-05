package fr.antproject.shapes

import fr.antproject.utils.Point

abstract class Shape {
    abstract infix fun contains(point: Point): Boolean
    abstract infix fun isInside(other: Shape): Boolean
}

