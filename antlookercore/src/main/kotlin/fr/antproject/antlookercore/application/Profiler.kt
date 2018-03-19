package fr.antproject.antlookercore.application

import java.text.DecimalFormat
import java.util.*

/**
 * This class implements a simple profiler, used to collect data about sections of the application
 */
object Profiler {

    private const val SEPARATOR = "/"

    /**The current stack of application sections*/
    private val profilingStack = LinkedList<Entry>()
    /**Stores information about the total time used by each section of the application*/
    private val profilingData = Collections.synchronizedMap(mutableMapOf<String, Long>())
    /**If set to false, this profiler won't collect any data.*/
    private var enabled = true

    /**A basic object used as a lock by the synchronized statements in this object*/
    private val LOCK = Any()

    /**
     * Starts a recording of a section with the name passed as argument
     */
    fun startSection(name: String) {
        if (enabled)
            synchronized(LOCK) {
                profilingStack.push(Entry(name))
            }
    }

    /**
     * Stops the recording of the most recent section on the stack and stores the computed data in the map
     */
    fun endSection() {
        if (enabled) {
            synchronized(LOCK) {
                try {
                    val sectionData = profilingStack.pop()
                    val timeSpent = System.currentTimeMillis() - sectionData.start
                    val prefix = if (profilingStack.isEmpty()) "" else profilingStack.map(Entry::name).reduce { s1, s2 -> "$s2$SEPARATOR$s1" } + SEPARATOR
                    profilingData.merge("$prefix${sectionData.name}", timeSpent, Long::plus)
                } catch (e: NoSuchElementException) {
                    Logger.warn("Attempted to end a section but none was started", e)
                }
            }
        }
    }

    /**
     * Convenience method to end a section and start another with a single call
     */
    fun endStartSection(name: String) {
        endSection()
        startSection(name)
    }

    /**
     * Gets the generated profiling data in a comprehensible form
     */
    fun getProfilingData(sectionName: String): List<Result> {
        if (!enabled) return emptyList()

        synchronized(LOCK) {
            val total = profilingData[sectionName] ?: return emptyList()
            return profilingData.filter { it.key.startsWith(sectionName) }
                    .map { Result(it.key, it.value, it.value.toDouble() / total) }
                    .sortedByDescending { it.duration }
        }
    }

    /**
     * A data class used to store information about current recorded sections
     */
    class Entry(val name: String, val start: Long = System.currentTimeMillis())

    /**
     * A data class used to store the computed information from our records
     */
    class Result(private val name: String, val duration: Long, private val percentage: Double) {
        companion object {
            val FORMAT = DecimalFormat("%#0.000")
        }

        override fun toString(): String {
            return "$name: duration=$duration, percentage=${FORMAT.format(percentage)}"
        }
    }

}