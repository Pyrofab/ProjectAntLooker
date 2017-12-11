package fr.antproject.model.diagram.transformer;

import fr.antproject.model.diagram.Diagram;
import fr.antproject.model.diagram.DiagramBase;
import fr.antproject.model.shapes.drawn.DrawnShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DiagramTransformer<T extends Diagram> {

    List<Class<? extends DrawnShape>> getValidShapes();

    @NotNull
    T transformDiagram(DiagramBase base);

}
