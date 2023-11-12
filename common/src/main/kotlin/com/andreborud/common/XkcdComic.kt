package com.andreborud.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class XkcdComic(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    @PrimaryKey val num: Int,
    @Json(name = "safe_title")
    val safeTitle: String,
    val title: String,
    val transcript: String,
    val year: String
): Serializable {

    fun getNextComicNumber(latestNumber: Int): Int {
        val nextNumber = num + 1
        if (nextNumber > latestNumber) {
            throw IsLatestComicExceptions()
        }
        return nextNumber
    }

    fun getPreviousComicNumber(): Int {
        val previousNumber = num - 1
        if (previousNumber < 1) {
            throw IsFirstComicExceptions()
        }
        return previousNumber
    }
}