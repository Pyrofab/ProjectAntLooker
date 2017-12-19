package fr.antproject.model.diagram.components;

public class Token implements IDiagramComponent {
    private Place container;
    private int id;

    public Token(int id) {
        this.id = id;
    }

    public Place getContainer() {
        return container;
    }

    public void setContainer(Place container) {
        this.container = container;
    }

    @Override
    public String toString() {
        return "Token{" +
                "container=" + container +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

}
