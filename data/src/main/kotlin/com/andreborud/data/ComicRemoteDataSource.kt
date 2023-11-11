package com.andreborud.data

import android.util.Log
import com.andreborud.common.XkcdComic
import javax.inject.Inject

class ComicRemoteDataSource @Inject constructor(private val comicApi: ComicApi) : ComicsDataSource {

    init {
        Log.d("ComicRemoteDataSource", "Injected ComicApi: $comicApi")
    }

    override suspend fun getLatestComic(): XkcdComic {
        return comicApi.getLatestComic()
    }

    override suspend fun getSpecificComic(index: Int): XkcdComic {
        return comicApi.getSpecificComic(index)
    }
}