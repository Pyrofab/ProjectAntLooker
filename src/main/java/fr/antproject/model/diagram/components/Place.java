package fr.antproject.model.diagram.components;

import java.util.List;


public class Place extends DiagramNode {
    private List<Transition> arcs;

    public void addTransition(Transition transition) {
        this.arcs.add(transition);
    }

    public List<Transition> getArcs() {
        return arcs;
    }
}
