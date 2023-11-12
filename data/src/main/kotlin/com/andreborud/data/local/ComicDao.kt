package com.andreborud.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreborud.common.XkcdComic

@Dao
interface ComicDao {

    @Query("SELECT * FROM xkcdcomic ORDER BY num ASC")
    suspend fun getAll(): List<XkcdComic>

    @Query("SELECT * FROM xkcdcomic WHERE num IN (:num)")
    suspend fun get(num: Int): XkcdComic?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(xkcdComic: XkcdComic)

    @Delete
    suspend fun delete(xkcdComic: XkcdComic)

}