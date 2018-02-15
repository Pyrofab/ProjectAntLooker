package fr.antproject.antlookercore.model.shapes.drawn;

import fr.antproject.antlookercore.model.shapes.Polygon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DrawnDiscConverter implements DrawnShape.IShapeConverter {
    @Nullable
    @Override
    public DrawnShape getFromPoly(@NotNull Polygon shape) {
        return null;
    }
}
