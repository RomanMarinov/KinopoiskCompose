package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Logo
import com.google.gson.annotations.SerializedName

data class LogoDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("url")
    val url: String
) {
    fun mapToDomain() : Logo {
        return Logo(
            _id = _id,
            url = url
        )
    }
}