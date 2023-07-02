package com.dev_marinov.kinopoiskapp.data.persons.remote

import com.dev_marinov.kinopoiskapp.domain.model.Person

data class PersonsDTO(
    val id: Int,
    val photo: String,
    val name: String?,
) {
    fun mapToDomain(movieId: Int): Person {
        return Person(
            id = id,
            photo = photo,
            name = name,
            movieId = movieId
        )
    }
}
