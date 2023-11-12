package com.andreborud.xkcdviewer.saved

import com.andreborud.common.XkcdComic

sealed class SavedState {
    data class OnComicsLoaded(val comics: List<XkcdComic>) : SavedState()
}