@file:JvmName("ConfigReload")

package fr.antproject

import fr.antproject.application.ImageProcessor
import fr.antproject.application.Logger
import fr.antproject.application.configScreen
import fr.antproject.model.diagram.IDiagram
import javafx.concurrent.Task
import javafx.scene.image.Image
import java.util.concurrent.Executors
import java.util.concurrent.Future

private var running: Future<*>? = null
private val RELOAD_THREAD = Executors.newSingleThreadExecutor()

fun scheduleReload(inputFile: String, saveFile: String?) {
    Logger.debug("${Thread.currentThread().stackTrace[2]}: ${running?.isDone}")
    if (running?.isDone != false)
        running = RELOAD_THREAD.submit(TaskConfigReload(inputFile, saveFile))
    else Logger.debug("attempted to refresh but a refresh is already in progress")
}

class TaskConfigReload(private val selectedFile: String, private val saveFile: String?) : Task<IDiagram>() {

    companion object {
        internal var displayedImage: Image? = null
    }

    override fun call(): IDiagram = ImageProcessor.process(selectedFile)

    override fun succeeded() {
        if (saveFile != null) {
            this.get().export(saveFile)
        }
        configScreen.imageView.image = displayedImage
        Logger.debug("Refreshed the view")
    }
}