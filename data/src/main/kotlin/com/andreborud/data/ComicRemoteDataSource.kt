package com.andreborud.data

import com.andreborud.common.XkcdComic
import javax.inject.Inject

class ComicRemoteDataSource @Inject constructor(private val comicsAPi: ComicApi) : ComicsDataSource {

    override suspend fun getLatestComic(): XkcdComic {
        return comicsAPi.getLatestComic()
    }

    override suspend fun getSpecificComic(index: Int): XkcdComic {
        return comicsAPi.getSpecificComic(index)
    }
}