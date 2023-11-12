package com.andreborud.domain.remote

import com.andreborud.common.XkcdComic
import com.andreborud.data.remote.ComicsRemoteRepositoryImpl
import javax.inject.Inject

class GetSpecificComicRemoteUseCase @Inject constructor(private val comicRemoteDataSource: ComicsRemoteRepositoryImpl) {

    suspend operator fun invoke(index: Int): XkcdComic? {
        return comicRemoteDataSource.getSpecificComic(index)
    }
}