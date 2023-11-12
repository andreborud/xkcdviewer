package com.andreborud.data.local

import com.andreborud.common.XkcdComic

interface ComicsLocalRepository {
    suspend fun getAllComics(): List<XkcdComic>
    suspend fun getComic(num: Int): XkcdComic?
    suspend fun saveComic(comic: XkcdComic)
    suspend fun deleteComic(comic: XkcdComic)
}