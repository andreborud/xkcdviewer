package com.andreborud.data

import com.andreborud.common.XkcdComic

interface ComicsDataSource {

    suspend fun getLatestComic(): XkcdComic

    suspend fun getSpecificComic(index: Int): XkcdComic

}