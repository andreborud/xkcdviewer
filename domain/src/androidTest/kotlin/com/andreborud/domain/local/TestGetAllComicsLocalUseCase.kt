package com.andreborud.domain.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetAllComicsLocalUseCaseTest {

    private lateinit var getAllComicsLocalUseCase: GetAllComicsLocalUseCase
    private lateinit var mockRepository: ComicsLocalRepository

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        coEvery { mockRepository.getAllComics() } returns listOf(
            XkcdComic(
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
            ),
            XkcdComic(
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
        )
        getAllComicsLocalUseCase = GetAllComicsLocalUseCase(mockRepository)
    }

    @Test
    fun testGetAllComics() = runBlocking {
        val expectedComics = listOf(
            XkcdComic(
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
            ),
            XkcdComic(
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
        )

        val result = getAllComicsLocalUseCase.invoke()
        assertEquals(expectedComics, result)
    }
}