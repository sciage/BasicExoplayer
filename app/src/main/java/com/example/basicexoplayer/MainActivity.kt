package com.example.basicexoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basicexoplayer.player.ExoPlayerManager
import com.example.basicexoplayer.screens.PlayerScreen
import com.example.basicexoplayer.utils.PlayerType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
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

@Composable
fun AppNavigation() {
    var selectedPlayerType by remember { mutableStateOf<PlayerType?>(null) }

    val playerType = selectedPlayerType
    if (playerType != null) {
        PlayerScreen(
            playerType = playerType,
            onBack = {
                ExoPlayerManager.releasePlayer()
                selectedPlayerType = null
            }
        )
    } else {
        MainScreen(onPlayerSelected = { selectedPlayerType = it })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onPlayerSelected: (PlayerType) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ExoPlayer Compose Switcher") })
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            items(PlayerType.entries.toTypedArray()) { playerType ->
                Text(
                    text = playerType.displayName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPlayerSelected(playerType) }
                        .padding(16.dp)
                )
            }
        }
    }
}
