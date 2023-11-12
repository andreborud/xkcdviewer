package com.andreborud.data.remote

import com.andreborud.common.XkcdComic

interface ComicsRemoteRepository {
    suspend fun getLatestComic(): XkcdComic
    suspend fun getSpecificComic(index: Int): XkcdComic
}