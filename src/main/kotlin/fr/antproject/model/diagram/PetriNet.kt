package fr.antproject.model.diagram

import fr.antproject.model.diagram.components.IDiagramComponent

/**
 * Class describing a petri net, a directed bipartite graph in which the nodes represent transitions and places.
 *
 * Petri nets are made of places, transitions and tokens
 */
class PetriNet(private val nodes: Collection<IDiagramComponent>) : IDiagram, Collection<IDiagramComponent> by nodes {

    companion object {
        const val HEADER = "<pnml xmlns=\"http://www.pnml.org/\">\n"
    }

    override fun export(path: String) {
        val builder = StringBuilder(HEADER)
        builder.append("<net id=\"n1\"type=\"...\">\n")
//        this.map(IDiagramComponent::export).map { it + "\n" }.reduce(String::plus)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "PetriNet(nodes=$nodes)"
    }

}
