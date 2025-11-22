package com.example.basicexoplayer.player

import android.util.Log
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player

private const val TAG = "PlayerEventListener"

class PlayerEventListener : Player.Listener {
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        Log.d(TAG, "onIsPlayingChanged: isPlaying = $isPlaying")
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString = when (playbackState) {
            Player.STATE_IDLE -> "IDLE"
            Player.STATE_BUFFERING -> "BUFFERING"
            Player.STATE_READY -> "READY"
            Player.STATE_ENDED -> "ENDED"
            else -> "UNKNOWN"
        }
        Log.d(TAG, "onPlaybackStateChanged: state = $stateString")
    }

    override fun onPlayerError(error: PlaybackException) {
        Log.e(TAG, "onPlayerError: ", error)
    }
}
