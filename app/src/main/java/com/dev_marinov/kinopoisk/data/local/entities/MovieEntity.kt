package com.dev_marinov.kinopoisk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoisk.domain.model.Poster
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import com.dev_marinov.kinopoisk.domain.model.Votes

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val alternativeName: String,
    val description: String?,
    val length: Int?,
    val name: String?,
    val shortDescription: String?,
    val type: String?,
    val year: Int?,
    val releaseYearsIds: List<ReleaseYear>?,
    val votes: Votes?,
    val rating: Rating?,
    val poster: Poster?,
    val page: Int
)
