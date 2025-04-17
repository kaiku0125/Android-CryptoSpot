package com.kaiku.composecomponent.component.pager

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.utils.sdp

@Composable
fun PagerDotComponent(
    modifier: Modifier = Modifier,
    currentPage: Int = 0,
    pageSize: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageSize) {
            DotItem(it == currentPage)
            if (it != pageSize - 1) {
                Spacer(modifier = Modifier.width(8.sdp()))
            }
        }
    }
}

@Composable
private fun DotItem(isSelected: Boolean) {
    Canvas(
        modifier = Modifier.size(6.sdp()),
        onDraw = {
            val size = 6.dp.toPx()
            drawCircle(
                color = if (isSelected) {
                    Color.White
                } else {
                    color_9e9e9f
                },

                radius = size / 2f
            )
        }
    )
}