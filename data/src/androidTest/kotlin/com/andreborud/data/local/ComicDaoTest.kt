package com.andreborud.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.andreborud.common.XkcdComic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicDaoTest {

    private lateinit var comicDb: ComicDb
    private lateinit var comicDao: ComicDao

    @Before
    fun setup() {
        comicDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            ComicDb::class.java
        ).allowMainThreadQueries().build()

        comicDao = comicDb.comicDao()
    }

    @Test
    fun testGetAllComics() = runBlocking {
        val comic1 = XkcdComic(
            month = "11",
            num = 853,
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10"
        )
        val comic2 = XkcdComic(
            month = "12",
            num = 852,
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10"
        )
        comicDao.save(comic1)
        comicDao.save(comic2)

        val comics = comicDao.getAll()

        assertEquals(2, comics.size)
        assertTrue(comics.containsAll(listOf(comic1, comic2)))
    }

    @Test
    fun testGetComic() = runBlocking {
        val comic = XkcdComic(
            month = "11",
            num = 853,
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10"
        )
        comicDao.save(comic)

        val retrievedComic = comicDao.get(comic.num)

        assertEquals(comic, retrievedComic)
    }

    @Test
    fun testSaveComic() = runBlocking {
        val comic = XkcdComic(
            month = "11",
            num = 853,
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10"
        )

        comicDao.save(comic)
        val retrievedComic = comicDao.get(comic.num)

        assertEquals(comic, retrievedComic)
    }

    @Test
    fun testDeleteComic() = runBlocking {
        val comic = XkcdComic(
            month = "11",
            num = 853,
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10"
        )
        comicDao.save(comic)

        comicDao.delete(comic)
        val retrievedComic = comicDao.get(comic.num)
        assertNull(retrievedComic)
    }

    @After
    fun closeDb() {
        comicDb.close()
    }
}