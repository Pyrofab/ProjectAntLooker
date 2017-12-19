package fr.antproject.model.diagram.components;

import org.jetbrains.annotations.NotNull;

public class Token implements IDiagramComponent {
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


    @NotNull
    @Override
    public String export() {
        return "";
    }
}
