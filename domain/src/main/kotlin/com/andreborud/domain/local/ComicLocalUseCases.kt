package com.andreborud.domain.local

import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository

class ComicLocalUseCases(private val repository: ComicsLocalRepository) {

    enum class OperationResult {
        SAVED, DELETED
    }

    suspend fun saveOrDelete(comic: XkcdComic): OperationResult {
        return if (existsInDatabase(comic.num)) {
            repository.deleteComic(comic)
            OperationResult.DELETED
        } else {
            repository.saveComic(comic)
            OperationResult.SAVED
        }
    }

    private suspend fun existsInDatabase(num: Int): Boolean {
        return repository.getComic(num) != null
    }

    suspend fun getAll(): List<XkcdComic> {
        return repository.getAllComics()
    }

    suspend fun get(num: Int): XkcdComic? {
        return repository.getComic(num)
    }
}