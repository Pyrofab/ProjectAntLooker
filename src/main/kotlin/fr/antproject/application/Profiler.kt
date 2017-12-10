package fr.antproject.application

import java.text.DecimalFormat
import java.time.Duration
import java.time.Instant
import java.util.*

object Profiler {

    const val SEPARATOR = "/"

    val profilingStack = LinkedList<Entry>()
    private val profilingData = mutableMapOf<String, Duration>()
    private var enabled = true

    fun startSection(name: String) {
        if (enabled)
            profilingStack.push(Entry(name))
    }

    fun endSection() {
        if (enabled) {
            try {
                val sectionData = profilingStack.pop()
                val timeSpent = Duration.between(sectionData.start, Instant.now())
                val prefix = if(profilingStack.isEmpty()) "" else profilingStack.map(Entry::name).reduce { s1, s2 -> "$s2$SEPARATOR$s1" } + SEPARATOR
                profilingData.merge("$prefix${sectionData.name}", timeSpent, Duration::plus)
            } catch (e: NoSuchElementException) {
                Logger.warn("Attempted to end a section but none was started", e)
            }
        }
    }

    fun endStartSection(name: String) {
        endSection()
        startSection(name)
    }

    fun getProfilingData(sectionName: String): List<Profiler.Result> {
        if(!enabled) return emptyList()

        val total = profilingData[sectionName]?.toNanos() ?: return emptyList()
        return profilingData.filter { it.key.startsWith(sectionName) }
                .map { Result(it.key, it.value, it.value.toNanos().toDouble() / total)  }
                .sortedByDescending { it.duration }
    }

    class Entry(val name: String, val start: Instant = Instant.now())

    class Result(val name: String, val duration: Duration, val percentage: Double) {
        companion object {
            val FORMAT = DecimalFormat("%#0.000")
        }

        override fun toString(): String {
            return "$name: duration=$duration, percentage=${FORMAT.format(percentage)}"
        }
    }

}