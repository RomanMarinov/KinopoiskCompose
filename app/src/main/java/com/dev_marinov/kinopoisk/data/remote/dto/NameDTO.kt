package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Name
import com.google.gson.annotations.SerializedName

data class NameDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("name")
    val name: String
) {
    fun mapToDomain() : Name {
        return Name(
            _id = _id,
            name = name
        )
    }
}