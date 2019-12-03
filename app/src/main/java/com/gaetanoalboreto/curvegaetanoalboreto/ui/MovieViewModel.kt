package com.gaetanoalboreto.curvegaetanoalboreto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaetanoalboreto.curvegaetanoalboreto.repository.MovieRepository
import kotlinx.coroutines.launch


class MovieViewModel(private val repository: MovieRepository) : ViewModel(){

    fun getPopularMovies() = repository.getPopularMovie()

    fun toggleFavorite(movieId : Int){
        viewModelScope.launch {
            repository.toggleFavorite(movieId)
        }
    }
}