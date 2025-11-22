package com.example.basicexoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.basicexoplayer.player.ExoPlayerManager
import com.example.basicexoplayer.utils.PlayerType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayerSwitcherScreen()
        }
    }

    override fun onPause() {
        super.onPause()
        ExoPlayerManager.getPlayer()?.pause()
    }

    override fun onResume() {
        super.onResume()
        ExoPlayerManager.getPlayer()?.play()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSwitcherScreen() {
    val context = LocalContext.current
    val urls = remember {
        mapOf(
            PlayerType.PROGRESSIVE to "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            PlayerType.HLS to "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8",
            PlayerType.DASH to "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd"
        )
    }

    var selected by remember { mutableStateOf(PlayerType.PROGRESSIVE) }
    var player by remember { mutableStateOf(ExoPlayerManager.getPlayer()) }

    LaunchedEffect(selected) {
        val url = urls[selected]!!
        ExoPlayerManager.initializePlayer(context, url)
        player = ExoPlayerManager.getPlayer()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ExoPlayer Compose Switcher") })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            TabRowForPlayers(selected) { selected = it }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                PlayerViewContainer(
                    player = player,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabRowForPlayers(selected: PlayerType, onSelected: (PlayerType) -> Unit) {
    val types = PlayerType.values().toList()
    val selectedIndex = types.indexOf(selected)
    SecondaryTabRow(selectedTabIndex = selectedIndex) {
        types.forEachIndexed { index, type ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onSelected(type) },
                text = { Text(type.displayName) }
            )
        }
    }
}
