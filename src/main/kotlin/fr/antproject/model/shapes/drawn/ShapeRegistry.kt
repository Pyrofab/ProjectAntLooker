package fr.antproject.model.shapes.drawn

object ShapeRegistry : MutableMap<Class<out DrawnShape>, DrawnShape.ShapeConverter> by mutableMapOf() {
    private fun register(shape: Class<out DrawnShape>, converter: DrawnShape.ShapeConverter) = put(shape, converter)

    private fun registerAll(vararg pairs: Pair<Class<out DrawnShape>, DrawnShape.ShapeConverter>) = pairs.forEach { register(it.first, it.second) }

    init {
        registerAll(
                DrawnArrow::class.java to DrawnArrow,
                DrawnCircle::class.java to DrawnCircle,
                DrawnRectangle::class.java to DrawnRectangle)
    }
}