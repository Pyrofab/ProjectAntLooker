package fr.antproject.model.diagram.transformer;

import fr.antproject.model.diagram.Diagram;
import fr.antproject.model.diagram.DiagramBase;
import fr.antproject.model.shapes.drawn.DrawnShape;

import java.util.List;

public interface DiagramTransformer<T extends Diagram> {

    List<Class<? extends DrawnShape>> getValidShapes();

    T transformDiagram(DiagramBase base);

}
