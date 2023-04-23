package com.dev_marinov.kinopoiskapp.presentation.play_video

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
import com.dev_marinov.kinopoiskapp.presentation.activity.model.TrailersUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlayVideoViewModel @Inject constructor(
    private val videosRepository: VideosRepository
) : ViewModel() {

    private var _youtubeUrlBody: MutableLiveData<String> = MutableLiveData()
    val youtubeUrlBody: LiveData<String> = _youtubeUrlBody

    private var playCount = 0

    fun getTrailers(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val trailers = videosRepository.getTrailersForDetail(movieId = movieId.toInt())
            trailers?.let {
                getCycle(it)
            }
        }
    }

    private fun getCycle(trailers: List<Trailer>) {
        val youtubeUrlsBodyTemp = mutableListOf<String>()
        var youtubeTrailerBody = ""
        for (i in trailers.indices) {
            if (trailers[i].site == "youtube") {
                val youtubeTrailerUrl: String = trailers[i].url
                val containsEquals = youtubeTrailerUrl.contains("=")
                youtubeTrailerBody = if (containsEquals) {
                    youtubeTrailerUrl.split("=")[1]
                } else {
                    youtubeTrailerUrl.substringAfterLast('/')
                }
                youtubeUrlsBodyTemp.add(youtubeTrailerBody)
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            _youtubeUrlBody.value = youtubeUrlsBodyTemp[playCount]
        }
    }
}