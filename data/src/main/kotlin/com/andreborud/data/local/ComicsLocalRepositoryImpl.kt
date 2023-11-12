package com.andreborud.data.local

import com.andreborud.common.XkcdComic
import javax.inject.Inject

class ComicsLocalRepositoryImpl @Inject constructor(private val comicDao: ComicDao) : ComicsLocalRepository {
    override suspend fun getAllComics(): List<XkcdComic> = comicDao.getAll()
    override suspend fun getComic(num: Int): XkcdComic? = comicDao.get(num)
    override suspend fun saveComic(comic: XkcdComic) = comicDao.save(comic)
    override suspend fun deleteComic(comic: XkcdComic) = comicDao.delete(comic)
}