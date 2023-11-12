package com.andreborud.xkcdviewer.saved

import com.andreborud.common.XkcdComic

sealed class SavedIntent {
    data object Refresh : SavedIntent()
    data class Remove(val comic: XkcdComic) : SavedIntent()
}