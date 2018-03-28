package fr.antproject.antlookercore.model.diagram

import android.os.Parcelable
import fr.antproject.antlookercore.model.diagram.components.IDiagramComponent

interface IDiagram : Collection<IDiagramComponent> {
    fun export(path: String)
}