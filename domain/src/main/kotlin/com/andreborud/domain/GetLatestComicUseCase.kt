package com.andreborud.domain

import com.andreborud.common.XkcdComic
import com.andreborud.data.ComicRemoteDataSource
import javax.inject.Inject

class GetLatestComicUseCase @Inject constructor(private val comicRemoteDataSource: ComicRemoteDataSource) {

    suspend operator fun invoke(): XkcdComic {
        return comicRemoteDataSource.getLatestComic()
    }

}