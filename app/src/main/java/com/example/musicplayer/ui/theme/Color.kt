package com.example.musicplayer.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val Blue200 = Color(0xFF30189F)
val Blue500 = Color(0xFF483974)
val Blue700 = Color(0xFF1c1348)
val Orange200 = Color(0xFFeb7603)
val TextColour = Color(0xFFffffff)


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