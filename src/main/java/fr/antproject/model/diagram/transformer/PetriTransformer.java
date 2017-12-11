package fr.antproject.model.diagram.transformer;

import fr.antproject.application.Logger;
import fr.antproject.model.diagram.DiagramBase;
import fr.antproject.model.diagram.PetriNet;
import fr.antproject.model.diagram.components.DiagramNode;
import fr.antproject.model.diagram.components.Place;
import fr.antproject.model.diagram.components.Transition;
import fr.antproject.model.shapes.drawn.DrawnArrow;
import fr.antproject.model.shapes.drawn.DrawnCircle;
import fr.antproject.model.shapes.drawn.DrawnRectangle;
import fr.antproject.model.shapes.drawn.DrawnShape;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetriTransformer implements DiagramTransformer<PetriNet> {

    @Override
    public List<Class<? extends DrawnShape>> getValidShapes() {
        return Arrays.asList(DrawnArrow.class, DrawnCircle.class, DrawnRectangle.class);
    }

    @NotNull
    @Override
    public PetriNet transformDiagram(DiagramBase base) {
        Map<DrawnShape, DiagramNode> nodes = base.stream()
                .filter(shape -> !(shape instanceof DrawnArrow))
                .map(shape -> new Pair<>(shape,
                        (shape instanceof DrawnCircle)
                        ? new Place()
                        : new Transition()))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        base.stream()
                .filter(shape -> shape instanceof DrawnArrow)
                .forEach(arrow -> updateArcs((DrawnArrow)arrow, base, nodes));

        return new PetriNet(nodes.values());
    }

    private void updateArcs(DrawnArrow arrow, DiagramBase base, Map<DrawnShape, DiagramNode> nodes) {
        DrawnShape startPoint = base.getClosestShape(arrow.getApprox().getStartPoint());
        DrawnShape endPoint = base.getClosestShape(arrow.getApprox().getLastPoint());
        if(startPoint instanceof DrawnRectangle && endPoint instanceof DrawnCircle) {
            ((Transition)nodes.get(startPoint)).addTransition((Place)nodes.get(endPoint));
        } else if (startPoint instanceof DrawnCircle && endPoint instanceof DrawnRectangle) {
            ((Place)nodes.get(startPoint)).addTransition((Transition)nodes.get(endPoint));
        } else Logger.info("Not a valid transition :" + startPoint + " to " + endPoint);
    }
}
