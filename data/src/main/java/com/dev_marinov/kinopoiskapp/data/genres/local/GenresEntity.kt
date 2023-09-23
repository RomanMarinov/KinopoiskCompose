package com.dev_marinov.kinopoiskapp.data.genres.local

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.movie.Genre
import com.dev_marinov.kinopoiskapp.domain.model.movie.Genres

// Указываем, что значения поля movieId (параметр childColumns) должно обязательно быть
// равно какому-либо значению поля id (параметр parentColumns) в таблице MovieEntityNew (параметр entity).
// CASCADE. Это означает, что при удалении родительского ключа, будут удалены, связанные с ним дочерние ключи.
// Т.е. при удалении genres, удалится и его movie.
@Entity(
    tableName = "genres",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = CASCADE
        )
    ]
)

data class GenresEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val genres: String?,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
) {
    companion object {
        fun mapFromDomain(genres: Genre): GenresEntity = GenresEntity(
            id = genres.id,
            genres = genres.name,
            movieId = genres.movieId,
        )
    }

    fun mapToDomain(): Genres = Genres(
        id = id,
        genres = genres,
        movieId = movieId
    )
}