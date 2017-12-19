package fr.antproject.model.shapes.drawn;

import fr.antproject.model.shapes.Polygon;
import fr.antproject.model.shapes.Shape;
import org.jetbrains.annotations.NotNull;

public class DrawnDisc extends DrawnShape {
    protected DrawnDisc(@NotNull Polygon drawnShape, @NotNull Shape approx) {
        super(drawnShape, approx);
    }
}
