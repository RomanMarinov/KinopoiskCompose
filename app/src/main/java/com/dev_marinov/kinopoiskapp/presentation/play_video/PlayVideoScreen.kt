package com.dev_marinov.kinopoiskapp.presentation.play_video

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun PlayVideoScreen() {


    // тут плеер
    //movieItemDetail.videos?.let {
        //YoutubeScreen("poUq9ypynKs")
       // Log.d("4444", "site=" + it[3].site)


        val playerView = YouTubePlayerView(LocalContext.current)
        val player: YouTubePlayer? = null

        YouTubePlayer(
            playerView2 = playerView,
            player = player,
            videoId = "poUq9ypynKs", // Замените VIDEO_ID на реальный идентификатор видео YouTube
            modifier = Modifier.fillMaxSize(),
            onReady = {
                player?.pause()
            },
            onFullScreen = {
                Log.d("4444", " вызвался onFullScreen")
                player?.pause()
                player?.toggleFullscreen()

            },
            onError = { exception ->
                // Обработка ошибок воспроизведения видео

            },
            context = LocalContext.current
        )
  //  }


}


///////////////////////
@Composable
fun YouTubePlayer(
    playerView2: YouTubePlayerView,
    player: YouTubePlayer?,
    videoId: String,
    modifier: Modifier = Modifier,
    onReady: (() -> Unit)? = null,
    onFullScreen: (() -> Unit)? = null,
    context: Context,
    onError: ((Exception) -> Unit)? = null
) {
    val activity = LocalContext.current as? Activity ?: return

    player?.play()
    player?.addListener(object : FullscreenListener, AbstractYouTubePlayerListener() {
        override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
            TODO("Not yet implemented")
        }

        override fun onExitFullscreen() {
            TODO("Not yet implemented")
        }
    })


//    DisposableEffect(Unit) {

//
//        player?.addListener(object : FullscreenListener, YouTubePlayerListener {
//            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
//                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                onFullScreen?.invoke() // или тут
//            }
//            override fun onExitFullscreen() {
//                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//                onFullScreen?.invoke() // или тут
//            }
//
//            override fun onApiChange(youTubePlayer: YouTubePlayer) { }
//
//            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) { }
//
//            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
//                onError?.invoke(Exception(error.name))
////                        onError?.invoke(YouTubePlayerException(error.name))
//            }
//
//            override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer,
//                playbackQuality: PlayerConstants.PlaybackQuality) { }
//
//            override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer,
//                playbackRate: PlayerConstants.PlaybackRate) { }
//
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                youTubePlayer.loadVideo(videoId, 0f)
//                youTubePlayer.pause()
//                onReady?.invoke()
//            }
//
//            override fun onStateChange(youTubePlayer: YouTubePlayer,
//                state: PlayerConstants.PlayerState) { }
//
//            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) { }
//
//            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) { }
//
//            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) { }
//
//        })

/////////////////////////////////////

    playerView2?.addYouTubePlayerListener(object : FullscreenListener,
        AbstractYouTubePlayerListener() {

        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.loadVideo(videoId, 0f)
            youTubePlayer.pause()
            onReady?.invoke()

            Log.d("4444", " вызвался AbstractYouTubePlayerListener onReady")
        }

        override fun onError(
            youTubePlayer: YouTubePlayer,
            error: PlayerConstants.PlayerError
        ) {
            onError?.invoke(Exception(error.name))
//                        onError?.invoke(YouTubePlayerException(error.name))
        }

        override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
            Log.d("4444", " вызвался onEnterFullscreen")
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            // activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

            onFullScreen?.invoke() // или тут
        }

        override fun onExitFullscreen() {
            Log.d("4444", " вызвался onExitFullscreen")
//                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            onFullScreen?.invoke() // или тут
        }
    })


    DisposableEffect(playerView2) {
        onDispose {
            playerView2?.release()
        }
    }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { playerView2 }
    )

//    val playerView = remember {
//        YouTubePlayerView(context).apply {
//          //  getPlayerUiController().showFullscreenButton(true)
//            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    youTubePlayer.loadVideo(videoId, 0f)
//                    youTubePlayer.pause()
//                    onReady?.invoke()
//                }
//                override fun onError(
//                    youTubePlayer: YouTubePlayer,
//                    error: PlayerConstants.PlayerError
//                ) {
//                    onError?.invoke(Exception(error.name))
////                        onError?.invoke(YouTubePlayerException(error.name))
//                }
//            })
//
//            addFullscreenListener(object : FullscreenListener {
//                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
//                    Log.d("4444", " вызвался onEnterFullscreen")
//                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                    onFullScreen?.invoke() // или тут
//                }
//                override fun onExitFullscreen() {
//                    Log.d("4444", " вызвался onExitFullscreen")
////                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//                    onFullScreen?.invoke() // или тут
//                }
//
//            })
//        }
//    }

//    DisposableEffect(playerView) {
//        onDispose {
//            playerView.release()
//        }
//    }
//    AndroidView(
//        modifier = modifier.fillMaxSize(),
//        factory = { playerView }
//    )
    // }
}
