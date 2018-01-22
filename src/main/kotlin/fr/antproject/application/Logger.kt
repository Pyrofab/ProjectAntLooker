package fr.antproject.application

import java.io.PrintStream
import java.util.logging.Level

/**
 * A utility class to control logging in the application
 */
object Logger {

    const val ERROR = 950
    const val WARN = 900
    const val INFO = 800
    const val DEBUG = 500
    const val PROFILING = 400

    private val printers = mutableMapOf<PrintStream, PrintStreamProperties>()

    init {
//        addOutputStream(System.out, INFO, WARN)               // uncomment that and comment the line below to remove debug logs
        addOutputStream(System.out, DEBUG, WARN)    // everything is printed to the standard output
        addOutputStream(System.err, WARN, Integer.MAX_VALUE)    // except important stuff that get printed to the standard error output
        addOutputStream(PrintStream("profiling.txt"), PROFILING, PROFILING+1)
    }

    /**
     * Adds an output stream in which messages will be logged if their logging level is in the range [minLevel, maxLevel)
     *
     * @param printStream the new printstream
     * @param minLevel messages with a log level strictly below this won't be printed to this stream
     * @param maxLevel messages with a log level equal or higher than this won't be printed to this stream
     */
    fun addOutputStream(printStream: PrintStream, minLevel: Int, maxLevel: Int) {
        printers += printStream to PrintStreamProperties(minLevel, maxLevel)
    }

    /**
     * Logs a message using the given [log level][java.util.logging.Level] as both a log priority and a prefix
     */
    @JvmStatic
    fun log(level: Level, message: Any?, exception: Throwable? = null) {
        log(level.intValue(), "[${level.localizedName}] $message", exception)
    }

    /**
     * Logs the given message to all registered output streams accepting the given log level
     *
     * @param debugLevel the level used to determine which output streams this message will be printed to
     * @param message an object that will be printed on its own line. This method calls
     * at first String.valueOf(x) to get the printed object's string value
     * @param exception if an exception is given, its stacktrace will be printed to the accepting streams as well
     */
    @JvmStatic
    fun log(debugLevel: Int, message: Any?, exception: Throwable? = null) {
        printers.forEach { stream, props ->
            if (debugLevel >= props.minLevel && debugLevel < props.maxLevel) {
                stream.println(message)
                exception?.printStackTrace(stream)
            }
        }
    }

    /**
     * Convenience method to print a message with the log level "Error"
     */
    @JvmStatic
    fun error(message: Any, exception: Throwable? = null) {
        log(ERROR, "[Error] $message", exception)
    }

    /**
     * Convenience method to print a message with the log level "Warn"
     */
    @JvmStatic
    fun warn(message: Any, exception: Throwable? = null) {
        log(WARN, "[Warning] $message", exception)
    }

    /**
     * Convenience method to print a message with the log level "Info"
     */
    @JvmStatic
    fun info(message: Any) {
        info(message, null)
    }

    /**
     * Convenience method to print a message and a associated exception with the log level "Info"
     */
    @JvmStatic
    fun info(message: Any, exception: Throwable?) {
        log(INFO, "[Info] $message", exception)
    }

    /**
     * Convenience method to print a message with the log level "Debug"
     */
    @JvmStatic
    fun debug(message: Any?) {
        log(DEBUG, "[Debug] $message")
    }

    /**
     * A class holding logging properties about a registered print stream
     */
    class PrintStreamProperties(val minLevel: Int, val maxLevel: Int)

}