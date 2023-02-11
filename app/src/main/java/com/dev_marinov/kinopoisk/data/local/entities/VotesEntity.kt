package com.dev_marinov.kinopoisk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "votes")
data class VotesEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val await: Int,
    val filmCritics: Int,
    val imdb: Int,
    val kp: Int,
    val russianFilmCritics: Int
)