package com.example.musicplayer.data.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R

@Composable
fun MusicIcon(){

    val gradient = listOf(
        Color(0xFFFFF176),
        Color(0xFFFFEE58),
        Color(0xFFFFEB3B),
        Color(0xFFFFD600),
        Color(0xFFFFC107),
//        Color(0xFFFFF176),
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(brush = GradientAndBrush(
                true ,
                    colors = gradient
            )),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter =  painterResource(R.drawable.music),
                contentDescription = "",
                tint = Color.White)
        }
}

@Preview
@Composable
fun PreView()
{
    MusicIcon()
}