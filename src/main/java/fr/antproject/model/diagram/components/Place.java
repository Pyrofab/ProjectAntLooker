package fr.antproject.model.diagram.components;

import java.util.ArrayList;
import java.util.List;


public class Place extends DiagramNode {
    private List<Transition> arcs = new ArrayList<>();

    public void addTransition(Transition transition) {
        this.arcs.add(transition);
    }

    public List<Transition> getArcs() {
        return arcs;
    }

    @Override
    public String toString() {
        return "Place{" +
                "arcs=" + arcs +
                '}';
    }
}
