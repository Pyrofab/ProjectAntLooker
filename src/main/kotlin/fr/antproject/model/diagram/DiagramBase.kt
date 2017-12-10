package fr.antproject.model.diagram

import fr.antproject.model.shapes.drawnshapes.DrawnArrow
import fr.antproject.model.shapes.drawnshapes.DrawnShape
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
                if(closestShape == null || dist < distance) {
                    closestShape = shape
                    distance = dist
                }
            }
        }
        return closestShape
    }

    fun toDiagram(): Diagram {

        for (transition in this.filter { it is DrawnArrow }.map { it as DrawnArrow })
            ;
        return PetriNet.convertDiagram(this)
    }
}