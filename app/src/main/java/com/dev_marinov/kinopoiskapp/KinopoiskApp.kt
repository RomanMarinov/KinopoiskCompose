package com.dev_marinov.kinopoiskapp

import android.app.Application
import android.icu.text.CaseMap.Title
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KinopoiskApp : Application()


data class OfficeHelp(
    val title: String,
    val lat: Double,
    val long: Double
)