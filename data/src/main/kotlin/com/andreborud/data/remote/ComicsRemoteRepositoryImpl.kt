package com.andreborud.data.remote

import com.andreborud.common.XkcdComic
import javax.inject.Inject

class ComicsRemoteRepositoryImpl @Inject constructor(private val comicApi: ComicApi) : ComicsRemoteRepository {
    override suspend fun getLatestComic(): XkcdComic = comicApi.getLatestComic()
    override suspend fun getSpecificComic(index: Int) = comicApi.getSpecificComic(index)
}