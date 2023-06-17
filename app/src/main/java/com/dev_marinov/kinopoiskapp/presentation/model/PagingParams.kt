package com.dev_marinov.kinopoiskapp.presentation.model

data class PagingParams(
    val yearPickerFrom: String,
    val yearPickerTo: String,
    val ratingPickerFrom: String,
    val ratingPickerTo: String,
    val genre: String,
    val page: Int,
    val indexLoad: Int
)
