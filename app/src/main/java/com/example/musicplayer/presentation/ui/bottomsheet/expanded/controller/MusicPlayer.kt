package com.example.musicplayer.presentation.ui.bottomsheet.expanded.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.unit.dp
import com.example.musicplayer.R

@Composable
fun MusicPlayer(
    topSection: @Composable () -> Unit,
    playerControls: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .background(color = colorResource(id = R.color.background))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                topSection()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) {
//            Spacer(
//                modifier = Modifier
//                    .clip(
//                        RoundedCornerShape(
//                            bottomStartPercent = 100,
//                            bottomEndPercent = 100
//                        )
//                    )
//                    .background(Color.White)
//                    .height(36.dp)
//                    .fillMaxWidth()
//            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                playerControls()
            }
        }
    }
}