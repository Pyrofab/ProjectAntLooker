package fr.antproject.shapes

abstract class Shape {
    abstract infix fun isInside(other: Shape): Boolean
}

