package fr.antproject.utils

object Logger {
    var debugMode: Boolean = true

    fun info(message: Any, exception: Throwable? = null) {
        println("[Info] $message")
        exception?.printStackTrace()
    }

    fun debug(message: Any) {
        if(debugMode)
            println("[Debug] $message")
    }

    fun warn(message: Any, exception: Throwable? = null) {
        println("[Warning] $message")
        exception?.printStackTrace()
    }

    fun error(message: Any, exception: Throwable? = null) {
        println("[Error] $message")
        exception?.printStackTrace()
    }
}