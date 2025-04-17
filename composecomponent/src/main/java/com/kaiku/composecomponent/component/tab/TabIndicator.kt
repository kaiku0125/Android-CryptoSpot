package com.kaiku.composecomponent.component.tab


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.utils.sdp

@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
    indicatorBorder: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    shape : Shape
) {
    Box(
        modifier = modifier
            .width(width = indicatorWidth)
            .offset(x = indicatorOffset)
            .clipByShape(
                shape = shape,
                backgroundColor = indicatorColor,
                border = indicatorBorder
            )
    )
}