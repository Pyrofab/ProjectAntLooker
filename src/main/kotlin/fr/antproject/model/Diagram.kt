package fr.antproject.model

import fr.antproject.model.shapes.Shape
import fr.antproject.model.shapes.drawnshapes.DrawnArrow
import fr.antproject.model.shapes.drawnshapes.DrawnShape
import fr.antproject.utils.wrappers.Point

class Diagram(private val components: List<Shape>) : Collection<Shape> by components {
    private val drawnShapes = components.filter { it is DrawnShape }.map { it as DrawnShape }

    /**
     * TODO make something a lot more accurate
     */
    fun getClosestShape(p: Point): DrawnShape? {
        var closestShape: DrawnShape? = null
        var distance = 0.0
        for (shape in drawnShapes.filter { it !is DrawnArrow }) {
            for (vertex in shape) {
                val dist = vertex distTo p
                if(closestShape == null || dist < distance) {
                    closestShape = shape
                    distance = dist
                }
            }
        }
        return closestShape
    }
}