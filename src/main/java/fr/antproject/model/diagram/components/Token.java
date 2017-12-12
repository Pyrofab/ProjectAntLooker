package fr.antproject.model.diagram.components;

public class Token implements DiagramComponent{
    private Place container;

    public Token(Place container) {
        this.container = container;
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
}
