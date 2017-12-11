package fr.antproject.model.diagram.components;

import java.util.List;

public class Transition extends DiagramNode {
    private List<Place> arcs;

    public void addTransition(Place place) {
        this.arcs.add(place);
    }

    public List<Place> getArcs() {
        return arcs;
    }

}
