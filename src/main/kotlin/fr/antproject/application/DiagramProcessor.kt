package fr.antproject.application

import fr.antproject.model.diagram.DiagramBase

object DiagramProcessor {

    fun process(diagramBase: DiagramBase) = ImageProcessor.diagramTransformer.transformDiagram(diagramBase)
}