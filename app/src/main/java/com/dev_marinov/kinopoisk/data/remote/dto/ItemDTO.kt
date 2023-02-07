package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Item
import com.google.gson.annotations.SerializedName

data class ItemDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("logo")
    val logo: LogoDTO,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun mapToDomain() : Item {
        return Item(
            _id = _id,
            logo = logo.mapToDomain(),
            name = name,
            url = url
        )
    }
}