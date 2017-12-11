package fr.antproject.model.diagram.components

class Place(vararg arcs: Transition) : DiagramNode(*arcs) {
}