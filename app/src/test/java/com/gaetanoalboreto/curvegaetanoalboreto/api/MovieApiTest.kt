package com.gaetanoalboreto.curvegaetanoalboreto.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MovieApiTest {
    private lateinit var service: MovieService

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Test
    fun getPopularMoviesTest() {
        val mockBody = loadJsonFromRes("get-popular-movies-response.json")
        mockWebServer.enqueue(MockResponse().setBody(mockBody))
        val response = service.getPopularMovies()

        val observable = response.test()
        observable.assertNoErrors()
        observable.assertValue { it.page == 1 }
        observable.assertValue { it.results.isNotEmpty() }
        //Todo asserting other values
    }

    private fun loadJsonFromRes(fileName: String): String {
        val inputStream = javaClass.classLoader
            ?.getResourceAsStream("api-responses/$fileName")
        val source = inputStream?.source()?.buffer()
        return source?.readString(Charsets.UTF_8) ?: "{}"
    }
}