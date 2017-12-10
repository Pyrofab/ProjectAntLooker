package fr.antproject.application

/**
 * A utility class to control logging in the application
 */
object Logger {

    const val ERROR = 200
    const val WARN = 300
    const val INFO = 400
    const val DEBUG = 500

    /** Only messages with a log level lower than this will be printed */
    var debugLevel: Int = DEBUG

    @JvmStatic
    fun log(debugLevel: Int, message: Any, exception: Throwable? = null) {
        if (debugLevel <= this.debugLevel) {
            println(message)
            exception?.printStackTrace()
        }
    }

    @JvmStatic
    fun error(message: Any, exception: Throwable? = null) {
        log(ERROR, "[Error] $message", exception)
    }

    @JvmStatic
    fun warn(message: Any, exception: Throwable? = null) {
        log(WARN, "[Warning] $message", exception)
    }

    @JvmStatic
    fun info(message: Any) {
        info(message, null)
    }

    @JvmStatic
    fun info(message: Any, exception: Throwable?) {
        log(INFO, "[Info] $message", exception)
    }

    @JvmStatic
    fun debug(message: Any) {
        log(DEBUG, "[Debug] $message")
    }

}