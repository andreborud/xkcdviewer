package com.andreborud.domain.local

import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository

class SaveComicLocalUseCase(private val repository: ComicsLocalRepository) {
    suspend operator fun invoke(xkcdComic: XkcdComic) {
        return repository.saveComic(xkcdComic)
    }
}