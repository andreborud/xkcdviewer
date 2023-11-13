package com.andreborud.xkcdviewer.extensions

import android.view.View

// Simplify hiding and showing of views
fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}