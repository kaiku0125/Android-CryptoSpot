package com.kaiku.cryptospot.customView.textfield.data
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

data class TextFieldConfig(
    val value: String = "",
    val alignment: TextAlign = TextAlign.Left,
    val style: TextStyle = TextStyle.Default
)
