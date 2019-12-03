package com.gaetanoalboreto.curvegaetanoalboreto.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gaetanoalboreto.curvegaetanoalboreto.model.Movie

/**
 *
 * RoomDatabase for the application
 *
 */
@Database(
    entities = [
        Movie::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ComplexTypeConverter::class)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}