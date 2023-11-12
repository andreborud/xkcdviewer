package com.andreborud.domain.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreborud.common.XkcdComic
import com.andreborud.data.remote.ComicApi
import com.andreborud.data.remote.ComicsRemoteRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetSpecificComicUseCaseTest {

    private lateinit var getSpecificComicUseCase: GetSpecificComicRemoteUseCase
    private lateinit var mockDataSource: ComicsRemoteRepositoryImpl
    private lateinit var mockComicApi: ComicApi

    @Before
    fun setup() {
        mockComicApi = mockk(relaxed = true)
        coEvery { mockComicApi.getSpecificComic(853) } returns XkcdComic(
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

        mockDataSource = ComicsRemoteRepositoryImpl(mockComicApi)
        getSpecificComicUseCase = GetSpecificComicRemoteUseCase(mockDataSource)
    }

    @Test
    fun testReturnLatestComic() = runBlocking {
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

        val result = getSpecificComicUseCase.invoke(853)
        assertEquals(expectedComic, result)
    }
}