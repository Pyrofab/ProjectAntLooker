package fr.antproject.utils

/**
 * A utility class to control logging in the application
 */
object Logger {
    /**If set to false, debug messages won't be printed*/
    var debugMode: Boolean = true

    @JvmStatic
    fun info(message: Any, exception: Throwable? = null) {
        println("[Info] $message")
        exception?.printStackTrace()
    }

    @JvmStatic
    fun debug(message: Any) {
        if(debugMode)
            println("[Debug] $message")
    }

    @JvmStatic
    fun warn(message: Any, exception: Throwable? = null) {
        println("[Warning] $message")
        exception?.printStackTrace()
    }

    @JvmStatic
    fun error(message: Any, exception: Throwable? = null) {
        println("[Error] $message")
        exception?.printStackTrace()
    }
}