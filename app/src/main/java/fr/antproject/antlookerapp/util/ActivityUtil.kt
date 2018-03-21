@file:JvmName("ActivityUtil")
package fr.antproject.antlookerapp.util

import android.util.Log

/**
 * Example usage:
 *
 * `getResourceIdFromName("drawableName", R.drawable.class)`
 * @param id the name of the location that is being accessed
 * @param location the resource class in which to find the resource
 */
fun getResourceIdFromName(id: String, location: Class<*>): Int {
    try {
        val field = location.getField(id)
        return field.getInt(null)
    } catch (e: Exception) {
        Log.e("ActivityUtil", "Failure to get drawable id.", e)
    }
    return 0
}