package fr.antproject.antlookercore.model.diagram

import fr.antproject.antlookercore.model.diagram.components.IDiagramComponent
import fr.antproject.antlookercore.model.diagram.components.IExportableComponent
import java.io.PrintStream

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
        writer.println("\t<net id=\"$name\" type=\"P/T\">")
        this.filterIsInstance(IExportableComponent::class.java)
                .sortedBy(IDiagramComponent::getId)
                .map(IExportableComponent::export)
                .forEach { writer.println("\t\t$it") }
        writer.println("\t</net>")
        writer.println("</pnml>")
    }

    override fun toString(): String {
        return "PetriNet(nodes=$nodes)"
    }

}
