package com.example.musicplayer.presentation.ui.bottomsheet.expanded.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bottomsheetaniamted.extension.PlayerLogo
import com.example.musicplayer.R

@Composable
fun TopSection(
    logoUrl: String,
    title: String,
    progress: Float,
    onProgressChange: (Float) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        PlayerLogo(logoUrl)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = title,
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))
        Slider(
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.onBackground,
                activeTrackColor = MaterialTheme.colors.onBackground,
                inactiveTrackColor = Color.LightGray
            ),
            value = progress,
            onValueChange = { onProgressChange.invoke(it) },
            valueRange = 0f..100f,
        )
    }
}