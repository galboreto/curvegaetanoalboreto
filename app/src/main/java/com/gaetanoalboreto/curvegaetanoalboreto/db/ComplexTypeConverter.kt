package com.gaetanoalboreto.curvegaetanoalboreto.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ComplexTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToIntList(value: String): List<Int> {
        val mapType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, mapType)
    }

    @TypeConverter
    fun fromIntListToString(list: List<Int>): String {
        return gson.toJson(list)
    }
}