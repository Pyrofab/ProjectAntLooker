package fr.antproject.antlookercore.utils.wrappers

class Point(x: Number, y: Number) : org.opencv.core.Point(x.toDouble(), y.toDouble()) {
    constructor(other: org.opencv.core.Point) : this(other.x, other.y)

    override fun toString() = "${javaClass.simpleName}[x=${this.x},y=${this.y}]"

    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun times(mul: Int) = Point(x * mul, y * mul)
    operator fun div(denominator: Int) = Point(x / denominator, y / denominator)
    infix fun distTo(other: Point) = Math.sqrt(Math.pow((x - other.x), 2.0) + Math.pow((y - other.y), 2.0))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = (31 * result + x).toInt()
        result = (31 * result + y).toInt()
        return result
    }
}

/**
 * Represents the various transformations that can be applied to an displayedImage matrix
 */
enum class EnumImgTransforms(val flag: Int) {
    NO_TRANSFORMATION(0),
    GRAY(0b1),
    THRESHOLD(0b10)
}