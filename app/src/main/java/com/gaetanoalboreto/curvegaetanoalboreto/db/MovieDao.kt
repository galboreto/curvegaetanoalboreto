package com.gaetanoalboreto.curvegaetanoalboreto.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gaetanoalboreto.curvegaetanoalboreto.model.Movie

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovie(movie: Movie)

    @Query(
        "SELECT * FROM Movie ORDER BY page"
    )
    abstract fun loadMovies(): DataSource.Factory<Int, Movie>

    @Query(
        "SELECT * FROM Movie WHERE :movieId=id"
    )
    abstract fun getMovieById(movieId: Int): Movie
}