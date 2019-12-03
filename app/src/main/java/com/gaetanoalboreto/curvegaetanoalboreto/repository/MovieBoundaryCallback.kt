package com.gaetanoalboreto.curvegaetanoalboreto.repository

import android.util.Log
import androidx.paging.PagedList
import com.gaetanoalboreto.curvegaetanoalboreto.api.MovieService
import com.gaetanoalboreto.curvegaetanoalboreto.model.Movie
import com.gaetanoalboreto.curvegaetanoalboreto.model.PopularMovieResponse
import io.reactivex.schedulers.Schedulers


class MovieBoundaryCallback(
    private val service: MovieService,
    private val handleResponse: (PopularMovieResponse) -> Unit,
    private val handleError: (Throwable) -> Unit = {}
) : PagedList.BoundaryCallback<Movie>() {

    companion object {
        private const val TAG = "MovieBoundaryCallback"
    }

    override fun onZeroItemsLoaded() {
        Log.v(TAG, "onZeroItemsLoaded")
        fetchData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        Log.v(TAG, "onItemAtEndLoaded")
        if (itemAtEnd.page > 0) fetchData(itemAtEnd.page + 1)
    }

    private fun fetchData(page: Int = 1) {
        Log.v(TAG, "Fetching data, page=$page")
        //todo dismiss this properly
        service.getPopularMovies(page)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe(
                {
                    Log.v(TAG, "Fetched from network: $it")
                    handleResponse.invoke(it)
                },
                {
                    Log.e(TAG, "Error fetching from network: ${it.message}")
                    handleError.invoke(it)
                }
            )
    }
}