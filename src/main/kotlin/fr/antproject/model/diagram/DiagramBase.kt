package fr.antproject.model.diagram

import fr.antproject.model.shapes.drawn.DrawnArrow
import fr.antproject.model.shapes.drawn.DrawnShape
import fr.antproject.utils.wrappers.Point

class DiagramBase(private val components: List<DrawnShape>) : Collection<DrawnShape> by components {

    /**
     * TODO make something a lot more accurate
     */
    fun getClosestShape(p: Point): DrawnShape? {
        var closestShape: DrawnShape? = null
        var distance = 0.0
        for (shape in components.filter { it !is DrawnArrow }) {
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

}