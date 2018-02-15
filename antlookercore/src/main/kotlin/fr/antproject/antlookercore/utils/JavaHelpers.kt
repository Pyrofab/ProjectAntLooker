/**
 * Created by Fabien on 15/02/2018.
 * Contains a few helpers for methods that don't exist in the used API
 */
@file:JvmName("JavaHelpers")
package fr.antproject.antlookercore.utils

import java.util.*

/**
 * Because Map#forEach doesn't exist for android below 24
 */
fun <K,V> Map<K,V>.forEachK(action: (K, V) -> Unit) {
    for ((key, value) in entries) {
        val k: K
        val v: V
        try {
            k = key
            v = value
        } catch (ise: IllegalStateException) {
            // this usually means the entry is no longer in the map.
            throw ConcurrentModificationException(ise)
        }

        action(k, v)
    }
}

fun <K,V> MutableMap<K,V>.computeIfAbsentK(key: K, mappingFunction: (K) -> V): V? {
    val v = get(key)
    if (v == null) {
        val newValue = mappingFunction(key)
        if (newValue != null) {
            this[key] = newValue
            return newValue
        }
    }

    return v
}
