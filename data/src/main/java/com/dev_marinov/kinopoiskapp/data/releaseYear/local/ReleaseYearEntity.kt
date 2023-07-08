package com.dev_marinov.kinopoiskapp.data.releaseYear.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.movie.ReleaseYear

@Entity(
    tableName = "release_years",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = CASCADE
        )
    ]
)
data class ReleaseYearEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val end: Int?,
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