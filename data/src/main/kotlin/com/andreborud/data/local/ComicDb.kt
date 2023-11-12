package com.andreborud.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreborud.common.XkcdComic

@Database(entities = [XkcdComic::class], version = 1)
abstract class ComicDb : RoomDatabase() {
    abstract fun comicDao(): ComicDao
}