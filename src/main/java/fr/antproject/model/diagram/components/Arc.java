package fr.antproject.model.diagram.components;

public class Arc<U extends DiagramNode, V extends DiagramNode> implements DiagramComponent {
    private U source;
    private V target;

    public Arc(U source, V target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
