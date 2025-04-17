package com.kaiku.composecomponent.component.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WithShadow(
    modifier: Modifier = Modifier,
    shadowHeight: Dp = 0.1.dp,
    color: Color = Color.Black,
    shape: Shape = RectangleShape,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    var height by remember { mutableStateOf(1.dp) }

    val ratio by remember {
        derivedStateOf {
            if (shadowHeight > height) {
                1f
            } else {
                (1 - (shadowHeight / height)) * 0.99f
            }
        }
    }

    val boxModifier = modifier
        .background(
            brush = Brush.verticalGradient(
                ratio to color.copy(
                    alpha = 0.5f
                ),
                1f to Color.Transparent.copy(
                    alpha = 0.25f
                )
            ),
            shape = shape
        )
        .padding(bottom = shadowHeight)
        .onGloballyPositioned {
            with(density) {
                height = it.size.height.toDp()
            }
        }

    Box(
        modifier = boxModifier
    ) {
        content.invoke()
    }
}