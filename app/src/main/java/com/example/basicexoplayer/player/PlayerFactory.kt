package com.example.basicexoplayer.player

import android.content.Context
import com.example.basicexoplayer.utils.PlayerType
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem

object PlayerFactory {
    fun createPlayer(context: Context, type: PlayerType, url: String): ExoPlayer {
        // With Media3, the player can infer the stream type from the URI,
        // so we don't need to manually create different media sources.
        val player = ExoPlayer.Builder(context).build()
        player.addListener(PlayerEventListener())
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        return player
    }
}
