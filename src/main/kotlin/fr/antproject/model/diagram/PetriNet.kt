package fr.antproject.model.diagram

import fr.antproject.model.diagram.components.DiagramComponent

class PetriNet(private val nodes: Collection<DiagramComponent>) : Diagram, Collection<DiagramComponent> by nodes {

    override fun toString(): String {
        return "PetriNet(nodes=$nodes)"
    }

}
