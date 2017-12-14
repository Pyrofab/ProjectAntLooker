package fr.antproject.model.diagram.transformer;

import fr.antproject.application.Logger;
import fr.antproject.application.Profiler;
import fr.antproject.model.diagram.DiagramBase;
import fr.antproject.model.diagram.PetriNet;
import fr.antproject.model.diagram.components.*;
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

/**
 * Class used to transform a base diagram into a {@link PetriNet}
 */
public class PetriTransformer implements IDiagramTransformer<PetriNet> {

    @Override
    public List<Class<? extends DrawnShape>> getValidShapes() {
        return Arrays.asList(DrawnArrow.class, DrawnCircle.class, DrawnRectangle.class);
    }

    @NotNull
    @Override
    public PetriNet transformDiagram(DiagramBase base) {

        Profiler.INSTANCE.startSection("generate_nodes");
        Map<DrawnShape, IDiagramComponent> nodes = base.stream()
                .filter(shape -> !(shape instanceof DrawnArrow))
                .map(shape -> new Pair<>(shape,
                        (shape instanceof DrawnCircle)
                                ? componentFromCircle(base, (DrawnCircle) shape)
                                : new Transition()))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        Profiler.INSTANCE.endStartSection("link_tokens");
        nodes.forEach((shape, component) -> {
            if (component instanceof Token) {
                Token token = (Token) component;
                token.setContainer((Place) nodes.get(base.getShapeContaining(shape)));
                token.getContainer().addToken(token);
            }
        });

        Profiler.INSTANCE.endStartSection("generate_arcs");
        base.stream()
                .filter(shape -> shape instanceof DrawnArrow)
                .forEach(arrow -> updateArcs((DrawnArrow) arrow, base, nodes));
        Profiler.INSTANCE.endSection();

        return new PetriNet(nodes.values());
    }

    private IDiagramComponent componentFromCircle(DiagramBase base, DrawnCircle circle) {
        return (base.getShapesContaining(circle).isEmpty()) ? new Place() : new Token(null);
    }

    private void updateArcs(DrawnArrow arrow, DiagramBase base, Map<DrawnShape, IDiagramComponent> nodes) {
        DrawnShape startPoint = base.getClosestShape(arrow.getApprox().getStartPoint(),
                DiagramBase::notArrow, shape -> base.getShapesContaining(shape).isEmpty());
        DrawnShape endPoint = base.getClosestShape(arrow.getApprox().getLastPoint(),
                DiagramBase::notArrow, shape -> base.getShapesContaining(shape).isEmpty());
        if (startPoint instanceof DrawnRectangle && endPoint instanceof DrawnCircle) {
            Transition source = (Transition) nodes.get(startPoint);
            Arc<Transition, Place> arc = new Arc<>(source, (Place) nodes.get(endPoint));
            source.addTransition(arc);
            nodes.put(arrow, arc);
        } else if (startPoint instanceof DrawnCircle && endPoint instanceof DrawnRectangle) {
            Place source = (Place) nodes.get(startPoint);
            Arc<Place, Transition> arc = new Arc<>(source, (Transition)nodes.get(endPoint));
            source.addTransition(arc);
            nodes.put(arrow, arc);
        } else Logger.info("Not a valid transition: " + startPoint + " to " + endPoint, null);
    }
}
