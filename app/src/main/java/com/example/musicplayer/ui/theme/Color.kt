package com.example.musicplayer.ui.theme

import androidx.compose.ui.graphics.Color


sealed class ThemeColor(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val text: Color,
) {

    object Night : ThemeColor(
        background = Color(0xC4000000),
        surface = Color(0xFF000000),
        primary = Color(0xFF4FB64C),
        text = Color(0xffffffff)
    )

    object Day : ThemeColor(
        background = Color(0xffffffff),
        surface = Color(0xffffffff),
        primary = Color(0xE8FFC107),
        text = Color(0xFF000000)
    )
}