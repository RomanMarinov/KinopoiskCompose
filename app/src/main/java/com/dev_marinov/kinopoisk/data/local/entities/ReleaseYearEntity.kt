package com.dev_marinov.kinopoisk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "releaseYears")
data class ReleaseYearEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val end: Int,
    val start: Int
)