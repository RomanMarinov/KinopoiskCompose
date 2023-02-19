package com.dev_marinov.kinopoiskapp.data.movie.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.domain.model.Movie


@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "alternative_name")
    val alternativeName: String,
    val description: String?,
    val length: Int?,
    val name: String?,
    @ColumnInfo(name = "short_description")
    val shortDescription: String?,
    val type: String?,
    val year: Int?,
    val page: Int
) {

    companion object {
        fun mapFromDomain(movie: Movie): MovieEntity = MovieEntity(
            alternativeName = movie.alternativeName,
            description = movie.description,
            id = movie.id,
            length = movie.length,
            name = movie.name,
            shortDescription = movie.shortDescription,
            type = movie.type,
            year = movie.year,
            page = movie.page
        )
    }

    fun mapToDomain(): Movie = Movie(
        alternativeName = alternativeName,
        description = description,
        id = id,
        length = length,
        name = name,
        shortDescription = shortDescription,
        type = type,
        year = year,
        page = page
    )
}
