package fr.antproject.model.diagram.components;

import org.jetbrains.annotations.NotNull;

/**
 * Class describing a petri net arc
 * @param <S> The diagram node from which this arc takes source
 * @param <T> The diagram node in which this arc ends
 */
public class Arc<S extends DiagramNode, T extends DiagramNode> implements IExportableComponent {
    private S source;
    private T target;

    private final int id;

    public Arc(S source, T target, int id) {
        this.source = source;
        this.target = target;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }



    @NotNull
    @Override
    public String export() {
        return "<arc id=\""+ getPrefixedId()+"\" source=\""+source.getPrefixedId()+"\" target=\""+target.getPrefixedId()+"\" />";
    }

    @Override
    public int getId() {
        return id;
    }
}
