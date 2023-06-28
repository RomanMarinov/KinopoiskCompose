package com.dev_marinov.kinopoiskapp.presentation.play_video

import androidx.lifecycle.ViewModel
import com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayVideoViewModel @Inject constructor(
    private val videosRepository: VideosRepository
) : ViewModel() {

}