package fr.antproject.model.diagram.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describing a petri net place
 */
public class Place extends DiagramNode {
    private final List<Arc<Place, Transition>> arcs = new ArrayList<>();
    private final List<Token> tokens = new ArrayList<>();

    private final int id;

    public Place(int id) {
        this.id = id;
    }

    public void addTransition(Arc<Place, Transition> transition) {
        this.arcs.add(transition);
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }

    @Override
    public String toString() {
        return "Place{" +
                "arcs=" + arcs.size() +
                ", tokens:" + tokens.size() +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

}
