package fr.antproject.application

import fr.antproject.AntLookerApp
import fr.antproject.ConfigScreen
import fr.antproject.utils.MAX_FUSE_DISTANCE
import fr.antproject.utils.wrappers.ContourApproxMethod
import fr.antproject.utils.wrappers.ContourRetrievalMode
import fr.antproject.utils.wrappers.ThresholdTypes
import fr.antproject.utils.wrappers.ThresholdTypesOptional

class Configuration {
    private val configScreen: ConfigScreen
        get() = AntLookerApp.INSTANCE.loader.getController()

    // threshold
    val threshold: Double// = 140.0
        get() = configScreen.threshold.value
    val maxValue: Double// = 255.0
        get() = configScreen.maxValue.value
    val algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED
    val optional: ThresholdTypesOptional? = null

    // find contours
    val mode: ContourRetrievalMode = ContourRetrievalMode.LIST
    val method: ContourApproxMethod = ContourApproxMethod.TC89_KCOS

    val maxFuseDistance: Double// = MAX_FUSE_DISTANCE
        get() = configScreen.maxFuseDistance.value

    /**The fraction of the average under which detected shapes are considered errors*/
    val minAcceptedArea = 0.2
}