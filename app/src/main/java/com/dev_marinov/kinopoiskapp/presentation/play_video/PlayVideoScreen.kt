package com.dev_marinov.kinopoiskapp.presentation.play_video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@SuppressLint("SuspiciousIndentation")
@Composable
fun PlayVideoScreen(
    urlTrailer: String?,
    viewModel: PlayVideoViewModel = hiltViewModel(),
) {
    val playerView = YouTubePlayerView(LocalContext.current)
    val player: YouTubePlayer? = null
    val context = LocalContext.current.applicationContext

    YouTubePlayer(
        playerView = playerView,
        player = player,
        videoId = urlTrailer, // Замените VIDEO_ID на реальный идентификатор видео YouTube
        modifier = Modifier.fillMaxSize(),
        onReady = {
            player?.pause()
        },
        onFullScreen = {
            player?.pause()
            player?.toggleFullscreen()

        },
        onError = { exception ->
            Toast.makeText(context, "Video trailer not available", Toast.LENGTH_LONG).show()
        }
    )
    //  }
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
            }
            //youTubePlayer.pause()
            onReady?.invoke()
        }

        override fun onError(
            youTubePlayer: YouTubePlayer,
            error: PlayerConstants.PlayerError
        ) {
            onError?.invoke(Exception(error.name))
        }

        override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            onFullScreen?.invoke() // или тут
        }

        override fun onExitFullscreen() {
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
