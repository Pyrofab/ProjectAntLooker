package fr.antproject.model.diagram

import fr.antproject.model.diagram.components.DiagramComponent
import fr.antproject.model.diagram.components.DiagramNode

class PetriNet(val nodes: Collection<DiagramNode>) : Diagram, Collection<DiagramComponent> by nodes {

    companion object PetriConverter {

        fun convertDiagram(diagramBase: DiagramBase): PetriNet {
            return PetriNet(listOf())
        }
    }

}