package com.kaiku.cryptospot.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
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

val Typography.text_11sp_400weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color.White
        )
    }

val Typography.text_12sp_400weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color.White
        )
    }

val Typography.text_12sp_500weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(500),
                color = Color.White
        )
    }

val Typography.text_13sp_400weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight(400),
                color = Color.White
        )
    }

val Typography.text_13sp_500weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight(500),
                color = Color.White
        )
    }

val Typography.text_14sp_400weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
        )
    }

val Typography.text_15sp_400weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(400),
                color = color_717071
        )
    }

val Typography.text_15sp_500weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500)
        )
    }

val Typography.text_15sp_normal: TextStyle
    get() {
        return TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
        )
    }

val Typography.text_15sp_bold: TextStyle
    get() {
        return TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
        )
    }

val Typography.text_17sp_400weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = Color.White
        )
    }

val Typography.text_17sp_500weight : TextStyle
    get() {
        return TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(500),
                color = Color.White
        )
    }

val Typography.text_19sp_500weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 19.sp,
                fontWeight = FontWeight(500),
                color = Color.White
        )
    }

val Typography.text_20sp_600weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = Color.White
        )
    }

val Typography.text_28sp_500weight: TextStyle
    get() {
        return TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight(500),
                color = Color.White
        )
    }




