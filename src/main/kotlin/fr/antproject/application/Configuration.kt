package fr.antproject.application

import fr.antproject.utils.MAX_FUSE_DISTANCE
import fr.antproject.utils.wrappers.ContourApproxMethod
import fr.antproject.utils.wrappers.ContourRetrievalMode
import fr.antproject.utils.wrappers.ThresholdTypes
import fr.antproject.utils.wrappers.ThresholdTypesOptional

class Configuration {
    // threshold
    var threshold: Double = 140.0
    var maxValue: Double = 255.0
    var algorithm: ThresholdTypes = ThresholdTypes.BINARY_INVERTED
    var optional: ThresholdTypesOptional? = null

    // find contours
    var mode: ContourRetrievalMode = ContourRetrievalMode.LIST
    var method: ContourApproxMethod = ContourApproxMethod.TC89_KCOS

    var maxFuseDistance: Double = MAX_FUSE_DISTANCE

    /**The fraction of the average under which detected shapes are considered errors*/
    var minAcceptedArea = 0.2
}