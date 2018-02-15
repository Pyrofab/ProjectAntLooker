@file:JvmName("ConfigReload")

package fr.antproject.antlookercore

import android.media.Image
import fr.antproject.antlookercore.application.ImageProcessor
import fr.antproject.antlookercore.application.Logger
import fr.antproject.antlookercore.application.configScreen
import java.util.concurrent.Executors
import java.util.concurrent.Future

private var running: Future<*>? = null
private val RELOAD_THREAD = Executors.newSingleThreadExecutor()

fun scheduleReload(inputFile: String, saveFile: String?) {
    Logger.debug("${Thread.currentThread().stackTrace[2]}: ${running?.isDone}")
    if (running?.isDone != false) {
        running = RELOAD_THREAD.submit(TaskConfigReload(inputFile, saveFile))
    }
    else Logger.debug("attempted to refresh but a refresh is already in progress")
}

class TaskConfigReload(private val selectedFile: String, private val saveFile: String?) : Runnable/* : Task<Unit>()*/ {

    companion object {
        internal var displayedImage: Image? = null

        init {
            AntLookerApp.INSTANCE.addExitListener { RELOAD_THREAD.shutdownNow() }
        }
    }

    override fun run() {
        try {
            ImageProcessor.processImage(selectedFile)
        } catch (e: InterruptedException) {
            cancelled()
            return
        } catch (e: Exception) {
            failed()
            return
        }
        succeeded()
    }

    fun succeeded() {
        if (saveFile != null) {
            ImageProcessor.generateDiagram().export(saveFile)
        }
        configScreen.imageView.image = displayedImage
        clean()
        Logger.debug("Refreshed the image view")
    }

    fun cancelled() = clean()

    fun failed() = clean()

    private fun clean() {
        configScreen.progressIndicator.isVisible = false
    }
}