package fr.antproject.model.diagram

import fr.antproject.model.diagram.components.DiagramComponent

/**
 * Class describing a petri net, a directed bipartite graph in which the nodes represent transitions and places.
 *
 * Petri nets are made of places, transitions and tokens
 */
class PetriNet(private val nodes: Collection<DiagramComponent>) : Diagram, Collection<DiagramComponent> by nodes {

    override fun toString(): String {
        return "PetriNet(nodes=$nodes)"
    }

}
