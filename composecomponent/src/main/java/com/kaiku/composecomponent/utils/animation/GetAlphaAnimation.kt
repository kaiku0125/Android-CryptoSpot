package com.kaiku.composecomponent.utils.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import com.kaiku.composecomponent.utils.isPreviewMode

@Composable
fun getAlphaAnimation(
    visible: Boolean,
    animationSpec: AnimationSpec<Float>? = null,
    range: (Boolean) -> Float = { alphaRange(it) }
): Float {
    return if (isPreviewMode()) {
        1f
    } else {
        animationSpec?.let {
            animateFloatAsState(
                targetValue = range.invoke(visible),
                animationSpec = it,
                label = ""
            ).value
        } ?: animateFloatAsState(
            targetValue = range.invoke(visible),
            label = ""
        ).value
    }
}

private fun alphaRange(isVisible: Boolean): Float = if (isVisible) 1f else 0f