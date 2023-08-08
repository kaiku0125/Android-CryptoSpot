package com.kaiku.cryptospot.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)


val Typography.text_15sp: TextStyle
    get() {
        return TextStyle(
            fontSize = 15.sp
        )
    }
val Typography.text_16ssp_bold: TextStyle
    get() {
        return TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

