package fr.antproject.model.diagram.components;

import org.jetbrains.annotations.NotNull;

public class Arc<U extends DiagramNode, V extends DiagramNode> implements IDiagramComponent {
    private U source;
    private V target;



    public int id;

    public Arc(U source, V target,int id) {
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
        return "<arc id=\""+id+"\" source=\""+source.getId()+"\" target=\""+target.getId()+"\" />";
    }


    @Override
    public int getId() {
        return id;
    }
}
