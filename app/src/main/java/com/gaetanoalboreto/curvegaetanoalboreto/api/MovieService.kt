package com.gaetanoalboreto.curvegaetanoalboreto.api

import com.gaetanoalboreto.curvegaetanoalboreto.BuildConfig.API_KEY
import com.gaetanoalboreto.curvegaetanoalboreto.model.PopularMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("region") region: String? = null
    ): Single<PopularMovieResponse>
}