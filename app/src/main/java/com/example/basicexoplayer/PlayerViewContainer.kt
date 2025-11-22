package com.example.basicexoplayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.basicexoplayer.player.ExoPlayerManager

@Composable
fun PlayerViewContainer(
    player: Player?,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { PlayerView(it) },
        update = { view ->
            view.player = player
            view.useController = true
            view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        },
        onRelease = {
            ExoPlayerManager.releasePlayer()
        },
        modifier = modifier
    )
}
