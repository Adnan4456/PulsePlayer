package com.example.musicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColors(
    primary = ThemeColor.Night.primary,
    onPrimary = ThemeColor.Night.text,
    surface = ThemeColor.Night.surface,
    background = ThemeColor.Night.background
)

private val LightColorScheme = darkColors(
    primary = ThemeColor.Day.primary,
    onPrimary = ThemeColor.Day.text,
    surface = ThemeColor.Day.surface,
    background = ThemeColor.Day.background
)

@Composable
fun MusicTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
//        typography = Typography,
        content = content,
        colors = if (isDarkTheme) DarkColorScheme else LightColorScheme
    )
}

@Composable
fun MusicPlayerTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
}