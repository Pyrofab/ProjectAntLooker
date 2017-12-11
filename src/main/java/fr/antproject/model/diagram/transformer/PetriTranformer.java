package fr.antproject.model.diagram.transformer;

import fr.antproject.model.diagram.DiagramBase;
import fr.antproject.model.diagram.PetriNet;
import fr.antproject.model.shapes.drawn.DrawnArrow;
import fr.antproject.model.shapes.drawn.DrawnCircle;
import fr.antproject.model.shapes.drawn.DrawnRectangle;
import fr.antproject.model.shapes.drawn.DrawnShape;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PetriTranformer implements DiagramTransformer<PetriNet> {

    @Override
    public List<Class<? extends DrawnShape>> getValidShapes() {
        return Arrays.asList(DrawnArrow.class, DrawnCircle.class, DrawnRectangle.class);
    }

    @Override
    public PetriNet transformDiagram(DiagramBase base) {
        return new PetriNet(Collections.emptyList());
    }
}
