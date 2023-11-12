package com.andreborud.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class XkcdComic(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    @PrimaryKey val num: Int,
    @Json(name="safe_title")
    val safeTitle: String,
    val title: String,
    val transcript: String,
    val year: String
)