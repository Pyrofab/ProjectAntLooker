package fr.antproject.model.diagram.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Transition extends DiagramNode {


    private final List<Arc<Transition, Place>> arcs = new ArrayList<>();

    public Transition(int id) {
        this.id = id;
    }

    public int id;
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

    public int getId() {
        return id;
    }

    @NotNull

    @Override
    public String export() {
        return "<transition id=\""+id+"\"/>";
    }


}
