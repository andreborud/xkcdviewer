package com.andreborud.domain

import com.andreborud.common.XkcdComic
import com.andreborud.data.ComicRemoteDataSource
import javax.inject.Inject

class GetSpecificComicUseCase @Inject constructor(private val comicRemoteDataSource: ComicRemoteDataSource) {

    suspend operator fun invoke(index: Int): XkcdComic {
        return comicRemoteDataSource.getSpecificComic(index)
    }
}