package com.regev.shutterflytmbdapp.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object Typography {
    val typography = Typography(
        bodySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    )
}