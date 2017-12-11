package fr.antproject.model.shapes

import fr.antproject.model.shapes.drawn.DrawnArrow
import fr.antproject.model.shapes.drawn.DrawnCircle
import fr.antproject.model.shapes.drawn.DrawnRectangle
import fr.antproject.model.shapes.drawn.DrawnShape

object ShapeRegistry : MutableMap<Class<out DrawnShape>, DrawnShape.ShapeConverter> by mutableMapOf() {
    fun register(shape: Class<out DrawnShape>, converter: DrawnShape.ShapeConverter) = put(shape, converter)

    fun registerAll(vararg pairs: Pair<Class<out DrawnShape>, DrawnShape.ShapeConverter>) = putAll(pairs)

    init {
        registerAll(
                DrawnArrow::class.java to DrawnArrow.ArrowConverter,
                DrawnCircle::class.java to DrawnCircle.CircleConverter,
                DrawnRectangle::class.java to DrawnRectangle.RectangleConverter)
    }
}