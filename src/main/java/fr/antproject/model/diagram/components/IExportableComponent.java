package fr.antproject.model.diagram.components;

import java.util.Locale;

/**
 * Interface providing a definition for diagram components that should be exported as their own tag
 */
public interface IExportableComponent extends IDiagramComponent {

    /**
     * @return this component's id prefixed with a class identifier
     */
    default String getPrefixedId() {
        return getClass().getSimpleName().substring(0,1) + getId();
    }

    /**
     * Exports this component as a valid representation for the diagram it's contained in. <br/>
     * By default, generates a PNML tag from the class' name and the prefixed id
     *
     * @return a string representing this component fit for export
     */
    default String export() {
        return "<" + getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + " id=\"" + getPrefixedId() + "\"/>";
    }
}
