package fr.antproject.model.diagram.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Place extends DiagramNode {
    private final List<Arc<Place, Transition>> arcs = new ArrayList<>();
    private final List<Token> tokens = new ArrayList<>();

    public Place(int id) {
        this.id = id;
    }

    public int id;
    public void addTransition(Arc<Place, Transition> transition) {
        this.arcs.add(transition);
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }

    public List<Arc<Place, Transition>> getArcs() {
        return arcs;
    }

    @Override
    public String toString() {
        return "Place{" +
                "arcs=" + arcs.size() +
                ", tokens:" + tokens.size() +
                '}';
    }

    public int getId() {
        return id;
    }

    @NotNull
    @Override
    public String export() {
        return "<place id=\""+id+"\"/>";
    }

}
