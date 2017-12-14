@file:JvmName("LineUtils")

package fr.antproject.utils

import fr.antproject.utils.wrappers.Point
import java.lang.Math.acos
import kotlin.math.acos

fun areAligned(p: Point, q: Point, r: Point, delta: Double = 0.0): Boolean {
    val pq = p distTo q
    val pr = p distTo r
    val qr = q distTo r
    val angle = acos((pq * pq + pr * pr - qr * qr) / (2 * pq * pr))
    return angle <= delta
}

/**
 * Checks if a point is on a segment described by its extremities
 * @param p the first endpoint of the segment
 * @param q the opposite endpoint of the segment
 * @return true if the given point belongs to the segment
 */
fun Point.onSegment(p: Point, q: Point): Boolean = (
        this.x() <= Math.max(p.x(), q.x()) &&
                this.x() >= Math.min(p.x(), q.x()) &&
                this.y() <= Math.max(p.y(), q.y()) &&
                this.y() >= Math.min(p.y(), q.y()))

fun orientation(p: Point, q: Point, r: Point): Int {
    val v = (q.y() - p.y()) * (r.x() - q.x()) - (q.x() - p.x()) * (r.y() - q.y())
    if (v == 0) return 0
    return if (v > 0) 1 else 2
}

/**
 * Checks if two segments described by their extremities intersect
 * @param line1 a pair of two endpoints representing the first segment
 * @param line2 a pair of two endpoints representing the second segment
 * @return true if the two segment intersect
 */
fun doIntersect(line1: Pair<Point, Point>, line2: Pair<Point, Point>): Boolean {
    val o1 = orientation(line1.first, line1.second, line2.first)
    val o2 = orientation(line1.first, line1.second, line2.second)
    val o3 = orientation(line2.first, line2.second, line1.first)
    val o4 = orientation(line2.first, line2.second, line1.second)
    if (o1 != o2 && o3 != o4)
        return true
    // Special Cases
    // line1.first, line1.second and line2.first are collinear and line2.first lies on segment line1.first-line1.second
    if (o1 == 0 && line2.first.onSegment(line1.first, line1.second)) return true

    // line1.first, line1.second and line2.first are collinear and line2.second lies on segment line1.first-line1.second
    if (o2 == 0 && line2.second.onSegment(line1.first, line1.second)) return true

    // line2.first, line2.second and line1.first are collinear and line1.first lies on segment line2.first-line2.second
    if (o3 == 0 && line1.first.onSegment(line2.first, line2.second)) return true

    // line2.first, line2.second and line1.second are collinear and line1.second lies on segment line2.first-line2.second
    if (o4 == 0 && line1.second.onSegment(line2.first, line2.second)) return true

    return false // Doesn't fall in any of the above cases
}