package com.gaetanoalboreto.curvegaetanoalboreto.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.gaetanoalboreto.curvegaetanoalboreto.api.MovieService
import com.gaetanoalboreto.curvegaetanoalboreto.db.MovieDao
import com.gaetanoalboreto.curvegaetanoalboreto.db.MovieDb
import com.gaetanoalboreto.curvegaetanoalboreto.model.Movie
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: MovieRepository
    private val mockService: MovieService = mock()
    private val mockDb: MovieDb = mock()
    private val mockDao: MovieDao = mock()
    private val mockDataSourceFactory: DataSource.Factory<Int, Movie> = mock()
    private val livePagedList = MutableLiveData<PagedList<Movie>>()
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setup() {
        mockkStatic("androidx.paging.LivePagedListKt")
        every {
            mockDataSourceFactory.toLiveData(any<PagedList.Config>(),any(),any(),any())
        } returns livePagedList
        whenever(mockDao.loadMovies()).thenReturn(mockDataSourceFactory)
        repository = MovieRepository(mockService, mockDb, mockDao)
    }

    @Test
    fun getPopularMoviesTest(){

        //Setup some fake data
        val movies = arrayListOf(
            fakeMovie(123),
            fakeMovie(456)
        )
        pagedList = mockPagedList(movies)
        livePagedList.value = pagedList


        //Start test
        val observer : Observer<PagedList<Movie>> = mock()
        repository.getPopularMovie().observeForever(observer)
        val captor = argumentCaptor<PagedList<Movie>>()

        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assert(firstValue == pagedList)
        }
    }


    private fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList: PagedList<T> = mock()
        whenever(pagedList[any()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        whenever(pagedList.size).thenReturn(list.size)
        return pagedList
    }

    private fun fakeMovie(id : Int) = Movie(
        id,
        true,
        "",
        emptyList(),
        "",
        "",
        "",
        100.0,
        "",
        "",
        "Fake Title",
        false,
        9.9f,
        123,
        1,
        false
    )

}