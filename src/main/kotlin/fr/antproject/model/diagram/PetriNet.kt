package fr.antproject.model.diagram

import fr.antproject.model.diagram.components.IDiagramComponent

/**
 * Class describing a petri net, a directed bipartite graph in which the nodes represent transitions and places.
 *
 * Petri nets are made of places, transitions and tokens
 */
class PetriNet(private val nodes: Collection<IDiagramComponent>) : IDiagram, Collection<IDiagramComponent> by nodes {

    override fun toString(): String {
        return "PetriNet(nodes=$nodes)"
    }

}
