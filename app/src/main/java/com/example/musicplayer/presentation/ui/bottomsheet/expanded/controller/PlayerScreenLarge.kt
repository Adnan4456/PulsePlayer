package com.example.musicplayer.presentation.ui.bottomsheet.expanded.controller

import PlayerControls
import androidx.compose.foundation.background
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.musicplayer.R
import com.example.musicplayer.data.model.Audio

@Composable
fun PlayerScreenLarge(
    icon: String,
    name: String,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    Surface(
        modifier = Modifier.background(color = colorResource(id = R.color.background))
    ) {
        MusicPlayer(
            topSection = {
                TopSection(
                    logoUrl = icon,
                    title = name,
                    progress = progress,
                    onProgressChange = { onProgressChange.invoke(it) }
                )
            },
            playerControls = {
                PlayerControls(
                    isAudioPlying = isAudioPlying,
                    onStart = { onStart.invoke() },
                    onNext = { onNext.invoke() },
                    onPrevious = { onPrevious.invoke() },
                )
            }
        )
    }
}