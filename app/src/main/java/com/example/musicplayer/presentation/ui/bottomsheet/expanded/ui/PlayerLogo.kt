package com.example.bottomsheetaniamted.extension

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R

@Composable
fun PlayerLogo(logoUrl: String) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(180.dp)
            .shadow(10.dp, CircleShape)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
//            .border(10.dp, MaterialTheme.colors.primary, CircleShape)

    ) {
        Image(
            painter = painterResource(id = R.drawable.music),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
//                .shadow(10.dp, CircleShape),
        )
    }
}