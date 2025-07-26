package com.regev.shutterflytmbdapp.ui

import androidx.compose.ui.graphics.Color

object ColorScheme {
    val colors = Colors(
        primary = Color(0xFFFFA500),
        secondary = Color.White,
        background = Color.DarkGray,
        onBackground = Color(0xFFFFA500)
    )

    data class Colors(
        val primary: Color,
        val secondary: Color,
        val background: Color,
        val onBackground: Color
    )
}