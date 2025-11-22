package com.example.basicexoplayer.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.basicexoplayer.PlayerViewContainer
import com.example.basicexoplayer.player.ExoPlayerManager
import com.example.basicexoplayer.utils.PlayerType

private val urls = mapOf(
    PlayerType.PROGRESSIVE to "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
    PlayerType.HLS to "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8",
    PlayerType.DASH to "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(playerType: PlayerType, onBack: () -> Unit) {
    val context = LocalContext.current
    var player by remember { mutableStateOf(ExoPlayerManager.getPlayer()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(playerType) {
        val url = urls[playerType]!!
        ExoPlayerManager.initializePlayer(context, url) { loading ->
            isLoading = loading
        }
        player = ExoPlayerManager.getPlayer()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(playerType.displayName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            PlayerViewContainer(
                player = player,
                modifier = Modifier.fillMaxSize()
            )
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
