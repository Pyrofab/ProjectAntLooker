package fr.antproject.antlookercore.application

import fr.antproject.antlookercore.utils.MAX_FUSE_DISTANCE
import fr.antproject.antlookercore.utils.wrappers.ContourApproxMethod
import fr.antproject.antlookercore.utils.wrappers.ContourRetrievalMode
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypes
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypesOptional

/**
 * Default implementation of a configuration, giving default values for all types
 */
class DefaultConfiguration : IConfiguration{

    // threshold
    override val threshold: Double = 140.0
    override val maxValue: Double = 255.0
    override val algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED
    override val optional: ThresholdTypesOptional? = null
    // find contours
    override val mode: ContourRetrievalMode = ContourRetrievalMode.LIST
    override val method: ContourApproxMethod = ContourApproxMethod.TC89_KCOS
    override val maxFuseDistance: Double = MAX_FUSE_DISTANCE

    /**The fraction of the average under which detected shapes are considered errors*/
    override val minAcceptedArea: Double = 0.2
}