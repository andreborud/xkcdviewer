package com.andreborud.domain.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreborud.common.XkcdComic
import com.andreborud.data.local.ComicsLocalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicLocalUseCasesTest {

    private lateinit var comicLocalUseCases: ComicLocalUseCases
    private lateinit var mockRepository: ComicsLocalRepository

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        comicLocalUseCases = ComicLocalUseCases(mockRepository)
    }

    @Test
    fun testSaveComic_NotExisting() = runBlocking {
        val comicToSave = createTestComic()
        coEvery { mockRepository.getComic(comicToSave.num) } returns null

        val result = comicLocalUseCases.saveOrDelete(comicToSave)

        assertEquals(ComicLocalUseCases.OperationResult.SAVED, result)
        coVerify { mockRepository.saveComic(comicToSave) }
    }

    @Test
    fun testDeleteComic_Existing() = runBlocking {
        val comicToDelete = createTestComic()
        coEvery { mockRepository.getComic(comicToDelete.num) } returns comicToDelete

        val result = comicLocalUseCases.saveOrDelete(comicToDelete)

        assertEquals(ComicLocalUseCases.OperationResult.DELETED, result)
        coVerify { mockRepository.deleteComic(comicToDelete) }
    }

    private fun createTestComic() = XkcdComic(
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
}