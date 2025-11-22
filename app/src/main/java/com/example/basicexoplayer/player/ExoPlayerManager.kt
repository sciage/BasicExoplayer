package com.example.basicexoplayer.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object ExoPlayerManager {
    private var exoPlayer: ExoPlayer? = null
    private var currentUrl: String? = null

    fun getPlayer(): ExoPlayer? {
        return exoPlayer
    }

    fun initializePlayer(context: Context, url: String, onLoadingChanged: (Boolean) -> Unit) {
        if (exoPlayer == null || url != currentUrl) {
            releasePlayer()
            currentUrl = url
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(url))
                addListener(PlayerEventListener(onLoadingChanged))
                prepare()
                playWhenReady = true
            }
        } else {
            exoPlayer?.play()
        }
    }

    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
        currentUrl = null
    }
}
