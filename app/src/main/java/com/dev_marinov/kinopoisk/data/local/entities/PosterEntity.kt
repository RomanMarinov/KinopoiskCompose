package com.dev_marinov.kinopoisk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poster")
data class PosterEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val previewUrl: String,
    val url: String
)