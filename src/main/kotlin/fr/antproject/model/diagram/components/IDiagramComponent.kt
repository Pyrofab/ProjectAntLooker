package fr.antproject.model.diagram.components

interface IDiagramComponent {
    val id : Int
    fun export(): String
}