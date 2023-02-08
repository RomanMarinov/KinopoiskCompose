package com.dev_marinov.kinopoisk.domain.model

data class KinopoiskResponse(
    val docs: List<Doc>?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?,
    val total: Int?
)