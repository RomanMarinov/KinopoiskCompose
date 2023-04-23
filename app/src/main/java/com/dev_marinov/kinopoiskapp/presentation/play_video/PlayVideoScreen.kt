package com.dev_marinov.kinopoiskapp.presentation.play_video

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PlayVideoScreen(
    movieId: String?,
    viewModel: PlayVideoViewModel = hiltViewModel(),
) {

    movieId?.let { viewModel.getTrailers(it) }
    val youtubeUrlBody by viewModel.youtubeUrlBody.observeAsState()

    val playerView = YouTubePlayerView(LocalContext.current)
    val player: YouTubePlayer? = null
    val context = LocalContext.current

    youtubeUrlBody?.let {
        YouTubePlayer(
            playerView = playerView,
            player = player,
            videoId = it, // Замените VIDEO_ID на реальный идентификатор видео YouTube
            modifier = Modifier.fillMaxSize(),
            onReady = {
                player?.pause()
            },
            onFullScreen = {
                player?.pause()
                player?.toggleFullscreen()

            },
            onError = { exception ->
                Log.d("4444", " onError exception=$exception")
                Toast.makeText(context, "Video trailer not available", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Composable
fun YouTubePlayer(
    playerView: YouTubePlayerView,
    player: YouTubePlayer?,
    videoId: String?,
    modifier: Modifier = Modifier,
    onReady: (() -> Unit)? = null,
    onFullScreen: (() -> Unit)? = null,
    onError: ((Exception) -> Unit)? = null
) {
    val activity = LocalContext.current as? Activity ?: return
    player?.play()
    player?.addListener(object : FullscreenListener, AbstractYouTubePlayerListener() {
        override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {}
        override fun onExitFullscreen() {}
    })

    playerView.addYouTubePlayerListener(object : FullscreenListener,
        AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            videoId?.let {
                youTubePlayer.loadVideo(it, 0f)
                Log.d("4444", " вызвался AbstractYouTubePlayerListener onReady video=" + it)
            }
            //youTubePlayer.pause()
            onReady?.invoke()
            Log.d("4444", " вызвался AbstractYouTubePlayerListener onReady ")
        }

        override fun onError(
            youTubePlayer: YouTubePlayer,
            error: PlayerConstants.PlayerError
        ) { onError?.invoke(Exception(error.name)) }

        override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
            Log.d("4444", " вызвался onEnterFullscreen")
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            onFullScreen?.invoke() // или тут
        }

        override fun onExitFullscreen() {
            Log.d("4444", " вызвался onExitFullscreen")
//                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            onFullScreen?.invoke() // или тут
        }
    })


    DisposableEffect(playerView) {
        onDispose {
            playerView.release()
        }
    }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { playerView }
    )
}
