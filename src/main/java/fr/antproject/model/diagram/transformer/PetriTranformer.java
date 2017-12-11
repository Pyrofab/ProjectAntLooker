package fr.antproject.model.diagram.transformer;

import fr.antproject.model.diagram.DiagramBase;
import fr.antproject.model.diagram.PetriNet;
import fr.antproject.model.diagram.components.DiagramNode;
import fr.antproject.model.diagram.components.Place;
import fr.antproject.model.diagram.components.Transition;
import fr.antproject.model.shapes.drawn.DrawnArrow;
import fr.antproject.model.shapes.drawn.DrawnCircle;
import fr.antproject.model.shapes.drawn.DrawnRectangle;
import fr.antproject.model.shapes.drawn.DrawnShape;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PetriTranformer implements DiagramTransformer<PetriNet> {

    @Override
    public List<Class<? extends DrawnShape>> getValidShapes() {
        return Arrays.asList(DrawnArrow.class, DrawnCircle.class, DrawnRectangle.class);
    }

    @NotNull
    @Override
    public PetriNet transformDiagram(DiagramBase base) {
        List<DiagramNode> nodes = base.stream()
                .filter(shape -> !(shape instanceof DrawnArrow))
                .map(shape -> (shape instanceof DrawnCircle) ? new Place() : new Transition())
                .collect(Collectors.toList());
        return new PetriNet(Collections.emptyList());
    }
}
