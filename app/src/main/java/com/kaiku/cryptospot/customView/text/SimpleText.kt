package com.kaiku.cryptospot.customView.text

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.presentation.theme.text_11sp
import com.kaiku.cryptospot.presentation.theme.text_15sp

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    text: String,
    style: TextStyle = MaterialTheme.typography.text_15sp,
    @ColorRes textColor: Color = Color.White,
) {
    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        Text(
            text = text,
            style = style,
            color = textColor,
            maxLines = 1
        )
    }
}

