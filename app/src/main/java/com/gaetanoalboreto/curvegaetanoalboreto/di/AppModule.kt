package com.gaetanoalboreto.curvegaetanoalboreto.di

import androidx.room.Room
import com.gaetanoalboreto.curvegaetanoalboreto.api.MovieService
import com.gaetanoalboreto.curvegaetanoalboreto.db.MovieDb
import com.gaetanoalboreto.curvegaetanoalboreto.repository.MovieRepository
import com.gaetanoalboreto.curvegaetanoalboreto.ui.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

private const val BASE_URL = "https://api.themoviedb.org/3/"

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    single {
        Room
            .databaseBuilder(get(), MovieDb::class.java, "movie.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<MovieDb>().movieDao() }

    single { MovieRepository(get(), get(), get()) }

    viewModel { MovieViewModel(get()) }
}
