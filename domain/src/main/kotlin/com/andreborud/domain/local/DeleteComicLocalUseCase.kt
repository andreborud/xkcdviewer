package com.andreborud.domain.local

import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository

class DeleteComicLocalUseCase(private val repository: ComicsLocalRepository) {
    suspend operator fun invoke(xkcdComic: XkcdComic) {
        return repository.deleteComic(xkcdComic)
    }
}