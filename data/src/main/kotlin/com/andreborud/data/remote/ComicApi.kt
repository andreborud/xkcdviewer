package com.andreborud.data.remote

import com.andreborud.common.XkcdComic
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicApi {

    @GET("/info.0.json")
    suspend fun getLatestComic(): XkcdComic

    @GET("/{index}/info.0.json")
    suspend fun getSpecificComic(@Path("index") index: Int): XkcdComic

}