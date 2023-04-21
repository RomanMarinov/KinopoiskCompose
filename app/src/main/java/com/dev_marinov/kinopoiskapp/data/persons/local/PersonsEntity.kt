package com.dev_marinov.kinopoiskapp.data.persons.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.Person

@Entity(
    tableName = "persons",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = CASCADE
        )
    ]
)

data class PersonsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val photo : String,
    val person: String?,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
) {
    companion object {
        fun mapFromDomain(persons: Person): PersonsEntity {
            return PersonsEntity(
                id = persons.id,
                photo = persons.photo,
                person = persons.name,
                movieId = persons.movieId
            )
        }
    }

    fun mapToDomain(): Person {
        return Person(
            id = id,
            photo = photo,
            name = person,
            movieId = movieId
        )
    }
}
