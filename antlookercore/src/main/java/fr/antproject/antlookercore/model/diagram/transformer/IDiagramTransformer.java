package fr.antproject.antlookercore.model.diagram.transformer;

import fr.antproject.antlookercore.model.diagram.DiagramBase;
import fr.antproject.antlookercore.model.diagram.IDiagram;
import fr.antproject.antlookercore.model.shapes.drawn.DrawnShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IDiagramTransformer<T extends IDiagram> {

    List<Class<? extends DrawnShape>> getValidShapes();

    @NotNull
    T transformDiagram(DiagramBase base);

}
