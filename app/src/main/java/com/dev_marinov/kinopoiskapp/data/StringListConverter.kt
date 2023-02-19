package com.dev_marinov.kinopoiskapp.data

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun convertFromStringList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun convertToStringList(data: String): List<String> = data.split(",").toList()
}