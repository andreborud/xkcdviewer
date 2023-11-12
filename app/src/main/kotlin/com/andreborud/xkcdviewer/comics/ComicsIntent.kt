package com.andreborud.xkcdviewer.comics

sealed class ComicsIntent {
    data object DownloadNext : ComicsIntent()
    data object DownloadPrevious : ComicsIntent()
    data object Save : ComicsIntent()
    data object Share : ComicsIntent()
}