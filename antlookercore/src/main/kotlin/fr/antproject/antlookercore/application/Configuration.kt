package fr.antproject.antlookercore.application

import fr.antproject.antlookercore.AntLookerApp
import fr.antproject.antlookercore.ConfigScreen
import fr.antproject.antlookercore.utils.wrappers.ContourApproxMethod
import fr.antproject.antlookercore.utils.wrappers.ContourRetrievalMode
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypes
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypesOptional

val configScreen: ConfigScreen
    get() = AntLookerApp.INSTANCE.loader.getController()

/**
 * An implementation of IConfiguration bridging with the graphical interface to provide access to properties
 * FIXME use android stuff instead
 */
class Configuration : IConfiguration{

    // threshold
    override val threshold: Double// = 140.0
        get() = configScreen.threshold.value
    override val maxValue: Double = 255.0
    override val algorithm: ThresholdTypes// = ThresholdTypes.BINARY_INVERTED
        get() = configScreen.thresholdTypes.value
    override val optional: ThresholdTypesOptional?// = null
        get() = configScreen.thresholdTypesOptional.value
    // find contours
    override val mode: ContourRetrievalMode// = ContourRetrievalMode.LIST
        get() = configScreen.contourRetrievalMode.value
    override val method: ContourApproxMethod// = ContourApproxMethod.TC89_KCOS
        get() = configScreen.contourApproxMethod.value
    override val maxFuseDistance: Double// = MAX_FUSE_DISTANCE
        get() = configScreen.maxFuseDistance.value

    /**The fraction of the average under which detected shapes are considered errors*/
    override val minAcceptedArea// = 0.2
        get() = configScreen.minAcceptedArea.value
}