package com.kaiku.composecomponent.component.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.utils.sdp

/**
 * 點點載入動畫 (目前只支援3個點點的呈現)
 *
 * @param dotSize 點點大小
 * @param color 點點顏色
 * @param delayUnit 點點動畫延遲時間
 * @param minScale 點點縮放最小值
 * @param maxScale 點點縮放最大值
 */
@Composable
fun DotsPulsing(
    modifier : Modifier = Modifier,
    dotSize: Dp = 12.sdp(),
    color: Color = MaterialTheme.colorScheme.primary,
    delayUnit : Int = 300,
    minScale: Float = 0.2f,
    maxScale: Float = 0.8f
) {

    @Composable
    fun Dot(scale: Float) = Spacer(
        Modifier
            .size(dotSize)
            .scale(scale)
            .background(
                color = color,
                shape = CircleShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateScaleWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                minScale at delay using LinearEasing
                maxScale at delay + delayUnit using LinearEasing
                minScale at delay + delayUnit * 2
            }
        )
    )

    val scale1 by animateScaleWithDelay(0)
    val scale2 by animateScaleWithDelay(delayUnit)
    val scale3 by animateScaleWithDelay(delayUnit * 2)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Dot(scale1)
        PocketSpacer(width = 2)
        Dot(scale2)
        PocketSpacer(width = 2)
        Dot(scale3)
    }
}

