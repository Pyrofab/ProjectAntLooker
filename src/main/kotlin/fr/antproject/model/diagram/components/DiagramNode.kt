package fr.antproject.model.diagram.components

abstract class DiagramNode(vararg arcs: DiagramNode) : DiagramComponent {
    open val arcs: MutableList<DiagramNode> = mutableListOf(*arcs)
}