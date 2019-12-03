package com.gaetanoalboreto.curvegaetanoalboreto.repository

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.room.withTransaction
import com.gaetanoalboreto.curvegaetanoalboreto.api.MovieService
import com.gaetanoalboreto.curvegaetanoalboreto.db.MovieDao
import com.gaetanoalboreto.curvegaetanoalboreto.db.MovieDb
import com.gaetanoalboreto.curvegaetanoalboreto.model.Movie
import com.gaetanoalboreto.curvegaetanoalboreto.model.PopularMovieResponse
import io.reactivex.disposables.Disposable


class MovieRepository(
    private val movieService: MovieService,
    private val movieDb: MovieDb,
    private val movieDao: MovieDao
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    fun getPopularMovie(): LiveData<PagedList<Movie>> {

        val boundaryCallback = MovieBoundaryCallback(
            service = movieService,
            handleResponse = this::insertResultIntoDb,
            handleError = {} //Todo error management
        )
        return movieDao.loadMovies().toLiveData(
            config = Config(NETWORK_PAGE_SIZE, 3 * NETWORK_PAGE_SIZE),
            boundaryCallback = boundaryCallback
        )
    }

    suspend fun toggleFavorite(movieId : Int){
        movieDb.withTransaction {
            movieDao.getMovieById(movieId).let {
                it.favorite = !it.favorite //toggle favorite boolean
                movieDao.insertMovie(it)
            }
        }
    }

    private fun insertResultIntoDb(response: PopularMovieResponse) {
        response.results.map {
            it.apply {
                //Add page to each item first
                this.page = response.page
            }
        }.let {
            movieDb.runInTransaction {
                movieDao.insertMovies(it)
            }
        }

    }
}