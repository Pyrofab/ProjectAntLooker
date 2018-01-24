package fr.antproject.model.shapes.drawn;

import fr.antproject.model.shapes.Polygon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class DrawnDiscConverter implements DrawnShape.IShapeConverter {
    @Nullable
    @Override
    public DrawnShape getFromPoly(@NotNull Polygon shape){

        if(shape instanceof DrawnDisc){
            return null;
        }else {
            return null;
        }
    }
}
