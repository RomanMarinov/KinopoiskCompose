package com.dev_marinov.kinopoisk.data.releaseYear.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear

@Entity(
    tableName = "release_years",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["release_years_ids"],
            childColumns = ["movie_id"],
            onDelete = CASCADE
        )
    ]
)
data class ReleaseYearEntity(
    @PrimaryKey
    val id: String,
    val end: Int,
    val start: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
) {

    companion object {

        fun mapFromDomain(releaseYear: ReleaseYear): ReleaseYearEntity = ReleaseYearEntity(
            id = releaseYear.id,
            end = releaseYear.end,
            start = releaseYear.start,
            movieId = releaseYear.movieId
        )
    }

    fun mapToDomain(): ReleaseYear = ReleaseYear(
        id = id,
        end = end,
        start = start,
        movieId = movieId
    )
}