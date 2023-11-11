package com.andreborud.common

import com.squareup.moshi.Json

data class XkcdComic(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    val num: Int,
    @Json(name="safe_title")
    val safeTitle: String,
    val title: String,
    val transcript: String,
    val year: String
)