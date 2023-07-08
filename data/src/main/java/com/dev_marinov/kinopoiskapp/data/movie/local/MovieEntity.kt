package com.dev_marinov.kinopoiskapp.data.movie.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.domain.model.movie.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val type: String?,
    val name: String?,
    val description: String?,
    val lengthLength: Int?,
    val year: Int?,
    val page: Int
) {

    companion object {
        fun mapFromDomain(movie: Movie): MovieEntity = MovieEntity(
            id = movie.id,
            type = movie.type,
            name = movie.name,
            description = movie.description,
            lengthLength = movie.movieLength,
            year = movie.year,
            page = movie.page
        )
    }

    fun mapToDomain(): Movie = Movie(
        id = id,
        type = type,
        name = name,
        description = description,
        movieLength = lengthLength,
        year = year,
        page = page
    )
}
