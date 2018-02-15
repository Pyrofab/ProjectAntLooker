package fr.antproject.antlookercore.model.diagram

import fr.antproject.antlookercore.model.diagram.components.IDiagramComponent

interface IDiagram : Collection<IDiagramComponent> {
    fun export(path: String)
}