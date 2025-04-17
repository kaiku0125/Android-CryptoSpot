package com.kaiku.composecomponent.extension

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun Color.hoverColor(): Color {
    return if (this.luminance() < 0.5f) {
        Color.White
    } else {
        Color.Black
    }
}

@Composable
fun getColorAnimation(
    toColor: Color,
    animationSpec: AnimationSpec<Color>? = null
) : Color {
    val aniColor by animateColorAsState(
        targetValue = toColor,
        animationSpec = animationSpec ?: spring(),
        label = ""
    )
    return aniColor
}