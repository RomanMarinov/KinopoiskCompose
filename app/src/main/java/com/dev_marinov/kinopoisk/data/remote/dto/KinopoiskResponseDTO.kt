package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.KinopoiskResponse
import com.google.gson.annotations.SerializedName

data class KinopoiskResponseDTO(
    @SerializedName("docs")
    val docs: List<DocDTO>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int
) {
    fun mapToDomain(): KinopoiskResponse {
        return KinopoiskResponse(
            docs = docs.map {
                it.mapToDomain()
            },
            total = total,
            limit = limit,
            page = page,
            pages = pages
        )
    }
}