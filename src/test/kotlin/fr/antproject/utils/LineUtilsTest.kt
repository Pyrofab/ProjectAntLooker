package fr.antproject.utils

import fr.antproject.utils.wrappers.Point
import org.junit.Test

import org.junit.Assert.*

class LineUtilsTest {
    @Test
    fun testAreAligned() {
        assertTrue(areAligned(Point(0,0), Point(0,1), Point(0,2)))
        assertFalse(areAligned(Point(0,0), Point(0,1), Point(-1,200)))
    }

    @Test
    fun onSegment() {
    }

    @Test
    fun orientation() {
    }

    @Test
    fun doIntersect() {
    }

}