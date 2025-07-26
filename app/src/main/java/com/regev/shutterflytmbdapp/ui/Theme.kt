package com.regev.shutterflytmbdapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun ShutterflyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = ColorScheme.colors.primary,
            secondary = ColorScheme.colors.secondary,
            background = ColorScheme.colors.background,
            onBackground = ColorScheme.colors.onBackground
        ),
        typography = Typography.typography,
        content = content
    )
}