package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Watchability
import com.google.gson.annotations.SerializedName

data class WatchabilityDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("items")
    val items: List<ItemDTO>?
) {
    fun mapToDomain() : Watchability {
        return Watchability(
            _id = _id,
            items = items?.map {
                it.mapToDomain()
            }
        )
    }
}