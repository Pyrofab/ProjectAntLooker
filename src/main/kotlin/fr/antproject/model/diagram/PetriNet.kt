package fr.antproject.model.diagram

import fr.antproject.model.diagram.components.IDiagramComponent
import java.io.PrintStream
import java.nio.file.Files

/**
 * Class describing a petri net, a directed bipartite graph in which the nodes represent transitions and places.
 *
 * Petri nets are made of places, transitions and tokens
 */
class PetriNet(private val nodes: Collection<IDiagramComponent>, private val name: String = "n1") : IDiagram, Collection<IDiagramComponent> by nodes {

    companion object {
        const val HEADER = "<pnml xmlns=\"http://www.pnml.org/\">"
    }

    override fun export(path: String) {
        val writer = PrintStream(path)
        writer.println(HEADER)
        writer.println("<net id=\"$name\"type=\"P/T\">")
        this.map(IDiagramComponent::export).forEach(writer::println)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "PetriNet(nodes=$nodes)"
    }

}
