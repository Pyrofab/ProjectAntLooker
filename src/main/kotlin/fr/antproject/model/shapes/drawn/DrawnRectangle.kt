package fr.antproject.model.shapes.drawn

import fr.antproject.model.shapes.Polygon
import fr.antproject.model.shapes.Rectangle

class DrawnRectangle private constructor(drawnShape: Polygon, override val approx: Rectangle) : DrawnShape(drawnShape, approx) {

    companion object RectangleConverter : IShapeConverter {

        /**
         * TODO make this detect and take rotation into account
         * @param shape a polygon to analyse
         * @return the approximated approx or null if the shape isn't a valid approx
         */
        override fun getFromPoly(shape: Polygon): DrawnRectangle? {
            if (shape is DrawnRectangle) return shape
            if (!shape.isRectangle()) return null
            val x = (shape[0].x + shape[1].x) / 2
            val y = (shape[0].y + shape[3].y) / 2
            val width = ((shape[0] distTo shape[3]) + (shape[1] distTo shape[2])).toInt() / 2
            val height = ((shape[0] distTo shape[1]) + (shape[3] distTo shape[2])).toInt() / 2
            return DrawnRectangle(shape, Rectangle(x.toInt(), y.toInt(), width, height))
        }

        private fun Polygon.isRectangle(): Boolean {
            if (this is DrawnRectangle) return true
            if (this.nbPoints() != 4)
                return false
            val lengthAB = this[0] distTo this[1]
            var lengthAC = this[0] distTo this[3]
            var lengthBC = this[1] distTo this[3]
            var angleBAC = Math.acos((lengthAB * lengthAB + lengthAC * lengthAC - lengthBC * lengthBC) / (2 * lengthAB * lengthAC))
            if (angleBAC < Math.PI * 0.45 || angleBAC > Math.PI * 0.55)
                return false
            lengthAC = this[1] distTo this[2]
            lengthBC = this[2] distTo this[0]
            angleBAC = Math.acos((lengthAB * lengthAB + lengthAC * lengthAC - lengthBC * lengthBC) / (2 * lengthAB * lengthAC))
            if (angleBAC < Math.PI * 0.45 || angleBAC > Math.PI * 0.55)
                return false
            return true
        }

    }

}