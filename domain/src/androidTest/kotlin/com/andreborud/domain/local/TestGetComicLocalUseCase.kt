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
class GetComicLocalUseCaseTest {

    private lateinit var getComicLocalUseCase: GetComicLocalUseCase
    private lateinit var mockRepository: ComicsLocalRepository

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        coEvery { mockRepository.getComic(any()) } returns XkcdComic(
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

        getComicLocalUseCase = GetComicLocalUseCase(mockRepository)
    }

    @Test
    fun testGetComic() = runBlocking {
        val comicNumber = 853
        val expectedComic = XkcdComic(
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

        val result = getComicLocalUseCase.invoke(comicNumber)
        assertEquals(expectedComic, result)
    }
}