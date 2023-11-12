package com.andreborud.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class ComicApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ComicApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Add this line
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ComicApi::class.java)
    }

    @Test
    fun testGetLatestComic() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"month\": \"11\", \"num\": 2853, \"link\": \"\", \"year\": \"2023\", \"news\": \"\", \"safe_title\": \"Redshift\", \"transcript\": \"\", \"alt\": \"So do you have any plans for z=-0.000000000000045?\", \"img\": \"https://imgs.xkcd.com/comics/redshift.png\", \"title\": \"Redshift\", \"day\": \"10\"}")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getLatestComic()
        assertNotNull(response)
        val request = mockWebServer.takeRequest()
        assertEquals("/info.0.json", request.path)
    }

    @Test
    fun testGetSpecificComic() = runBlocking {
        val comicNumber = 100
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"month\": \"11\", \"num\": 2853, \"link\": \"\", \"year\": \"2023\", \"news\": \"\", \"safe_title\": \"Redshift\", \"transcript\": \"\", \"alt\": \"So do you have any plans for z=-0.000000000000045?\", \"img\": \"https://imgs.xkcd.com/comics/redshift.png\", \"title\": \"Redshift\", \"day\": \"10\"}")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getSpecificComic(comicNumber)
        assertNotNull(response)
        val request = mockWebServer.takeRequest()
        assertEquals("/$comicNumber/info.0.json", request.path)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}