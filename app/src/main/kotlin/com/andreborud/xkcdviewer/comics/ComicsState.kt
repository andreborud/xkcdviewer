package com.andreborud.xkcdviewer.comics

import com.andreborud.common.XkcdComic

sealed class ComicsState {
    data class OnComicDownloaded(val comic: XkcdComic, val isFirst: Boolean, val isLatest: Boolean, val isSaved: Boolean) : ComicsState()
    data object OnSaved : ComicsState()
    data object OnUnsaved : ComicsState()
    data class OnShare(val link: String) : ComicsState()
    data class OnShowExplanation(val link: String) : ComicsState()
    data class OnError(val message: String) : ComicsState()
}