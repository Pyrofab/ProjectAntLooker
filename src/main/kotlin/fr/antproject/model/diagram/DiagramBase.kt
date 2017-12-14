package fr.antproject.model.diagram

import fr.antproject.model.shapes.drawn.DrawnArrow
import fr.antproject.model.shapes.drawn.DrawnShape
import fr.antproject.utils.wrappers.Point
import java.util.*

class DiagramBase(private val components: List<DrawnShape>) : Collection<DrawnShape> by components {

    companion object Predicates {
        @JvmStatic
        fun notArrow(shape: DrawnShape) = shape !is DrawnArrow
    }

    private val cache = WeakHashMap<DrawnShape, List<DrawnShape>>()

    /**
     * TODO make something more accurate (currently doesn't take edges into account)
     */
    @SafeVarargs
    fun getClosestShape(p: Point, vararg filters: (DrawnShape) -> Boolean): DrawnShape? {
        var closestShape: DrawnShape? = null
        var distance = 0.0
        for (shape in components.filter { shape -> filters.all { it(shape) } }) {
            for (vertex in shape) {
                val dist = vertex distTo p
                if (closestShape == null || dist < distance) {
                    closestShape = shape
                    distance = dist
                }
            }
        }
        return closestShape
    }

    fun getShapesContaining(shape: DrawnShape): List<DrawnShape> =
            cache.computeIfAbsent(shape, { this.filter { shape != it && shape isInside it } })

    fun getShapeContaining(shape: DrawnShape): DrawnShape? = getShapesContaining(shape).minBy { it.getArea() }

}