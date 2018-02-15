package fr.antproject.antlookercore.model.diagram.components;

import java.util.ArrayList;
import java.util.List;

public class Transition extends DiagramNode {

    private final List<Arc<Transition, Place>> arcs = new ArrayList<>();

    private final int id;

    public Transition(int id) {
        this.id = id;
    }

    public void addTransition(Arc<Transition, Place> place) {
        this.arcs.add(place);
    }

    public List<Arc<Transition, Place>> getArcs() {
        return arcs;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "arcs=" + arcs.size() +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }
}
