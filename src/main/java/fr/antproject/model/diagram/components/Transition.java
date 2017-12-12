package fr.antproject.model.diagram.components;

import java.util.ArrayList;
import java.util.List;

public class Transition extends DiagramNode {
    private final List<Place> arcs = new ArrayList<>();

    public void addTransition(Place place) {
        this.arcs.add(place);
    }

    public List<Place> getArcs() {
        return arcs;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "arcs=" + arcs +
                '}';
    }
}
