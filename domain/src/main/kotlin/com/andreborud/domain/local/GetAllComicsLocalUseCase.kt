package com.andreborud.domain.local

import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository

class GetAllComicsLocalUseCase(private val repository: ComicsLocalRepository) {
    suspend operator fun invoke(): List<XkcdComic> {
        return repository.getAllComics()
    }
}