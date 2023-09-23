package com.dev_marinov.kinopoiskapp.data.pagination

data class PagingParamsDTO(
    val yearPickerFrom: String,
    val yearPickerTo: String,
    val ratingPickerFrom: String,
    val ratingPickerTo: String,
    val genre: String,
    val page: Int,
    val indexLoad: Int
)
