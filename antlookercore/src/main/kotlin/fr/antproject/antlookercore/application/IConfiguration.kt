package fr.antproject.antlookercore.application

import fr.antproject.antlookercore.utils.wrappers.ContourApproxMethod
import fr.antproject.antlookercore.utils.wrappers.ContourRetrievalMode
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypes
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypesOptional

/**
 * An interface describing an object that allow access to every configurable property used in the application
 */
interface IConfiguration {

    // threshold
    val threshold: Double// = 140.0
    val maxValue: Double// = 255.0
    val algorithm: ThresholdTypes// = ThresholdTypes.BINARY_INVERTED
    val optional: ThresholdTypesOptional?// = null
    // find contours
    val mode: ContourRetrievalMode// = ContourRetrievalMode.LIST
    val method: ContourApproxMethod// = ContourApproxMethod.TC89_KCOS
    val maxFuseDistance: Double// = MAX_FUSE_DISTANCE

    /**The fraction of the average under which detected shapes are considered errors*/
    val minAcceptedArea: Double// = 0.2
}