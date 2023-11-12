package com.andreborud.domain.local

import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository

class GetComicLocalUseCase(private val repository: ComicsLocalRepository) {
    suspend operator fun invoke(num: Int): XkcdComic? {
        return repository.getComic(num)
    }
}