package com.dev_marinov.kinopoisk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rating")
data class RatingEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val await: Double,
    val filmCritics: Int,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double
)