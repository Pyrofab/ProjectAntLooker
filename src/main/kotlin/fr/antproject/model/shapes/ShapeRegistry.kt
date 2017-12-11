package fr.antproject.model.shapes

import fr.antproject.model.shapes.drawn.DrawnShape

object ShapeRegistry : MutableMap<Class<out DrawnShape>, DrawnShape.ShapeConverter> by mutableMapOf() {
    fun register(shape: Class<out DrawnShape>, converter: DrawnShape.ShapeConverter) = put(shape, converter)
}