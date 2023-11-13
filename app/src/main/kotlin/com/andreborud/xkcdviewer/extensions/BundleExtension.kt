package com.andreborud.xkcdviewer.extensions

import android.os.Build
import android.os.Bundle
import java.io.Serializable

/**
 * Handle deprecated get of serializable items from a bundle
 */
@Suppress("DEPRECATION")
fun <T : Serializable> Bundle.getSerializableSafe(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        getSerializable(name, clazz)
    } else {
        getSerializable(name) as T?
    }
}