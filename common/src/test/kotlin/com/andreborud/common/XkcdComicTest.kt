package com.andreborud.common

import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class XkcdComicTest {

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun getNextComicNumber_NormalCase() {
        val comic = XkcdComic(
            num = 5,
            month = "11",
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10")
        assertEquals(6, comic.getNextComicNumber(10))
    }

    @Test
    fun getNextComicNumber_IsLatestComic() {
        val comic = XkcdComic(
            num = 10,
            month = "11",
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10")

        expectedException.expect(IsLatestComicExceptions::class.java)
        comic.getNextComicNumber(10)
    }

    @Test
    fun getPreviousComicNumber_NormalCase() {
        val comic = XkcdComic(
            num = 5,
            month = "11",
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10")
        assertEquals(4, comic.getPreviousComicNumber())
    }

    @Test
    fun getPreviousComicNumber_IsFirstComic() {
        val comic = XkcdComic(
            num = 1,
            month = "11",
            link = "",
            year = "2023",
            news = "",
            safeTitle = "Redshift",
            transcript = "",
            alt = "So do you have any plans for z=-0.000000000000045?",
            img = "https://imgs.xkcd.com/comics/redshift.png",
            title = "Redshift",
            day = "10")

        expectedException.expect(IsFirstComicExceptions::class.java)
        comic.getPreviousComicNumber()
    }
}