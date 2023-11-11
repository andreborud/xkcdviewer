package com.andreborud.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreborud.common.XkcdComic
import com.andreborud.data.ComicApi
import com.andreborud.data.ComicRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetLatestComicUseCaseTest {

    private lateinit var getLatestComicUseCase: GetLatestComicUseCase
    private lateinit var mockDataSource: ComicRemoteDataSource
    private lateinit var mockComicApi: ComicApi

    @Before
    fun setup() {
        // Mock ComicApi
        mockComicApi = mockk(relaxed = true)
        coEvery { mockComicApi.getLatestComic() } returns XkcdComic(
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

        // Create ComicRemoteDataSource with the mocked ComicApi
        mockDataSource = ComicRemoteDataSource(mockComicApi)

        // Instantiate GetLatestComicUseCase with the mock DataSource
        getLatestComicUseCase = GetLatestComicUseCase(mockDataSource)
    }

    @Test
    fun testReturnLatestComic() = runBlocking {
        // Given
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

        // When
        val result = getLatestComicUseCase.invoke()

        // Then
        assertEquals(expectedComic, result)
    }
}