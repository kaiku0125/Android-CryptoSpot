package com.kaiku.composecomponent.component.loading

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_333333
import kotlin.math.abs

/**
 * @property percentage 百分比 (%數)
 * @property number 總數
 * @property radius 半徑
 * @property strokeColor 圓圈顏色
 * @property strokeWidth 圓圈顏色
 * @property bg 背景顏色
 * @property animationSpec 動畫設定
 */
data class CircularPercentageConfig(
    val percentage: Float = 0f,
    val number: Int = 100,
    val radius: Dp = 50.dp,
    val strokeColor: Color = Color.Green,
    val strokeWidth: Dp = 8.dp,
    val bg: Color = color_333333,
    val animationSpec: AnimationSpec<Float> = tween(
        durationMillis = 1000,
        easing = FastOutSlowInEasing
    )
)

/**
 * 基於Canvas繪製的圓形進度條的事件
 *
 * @property nowPercent 當前百分比
 * @property number 總數
 */
data class CircularEvent(
    val nowPercent: Float = 0f,
    val number: Int = 100
)

/**
 * 基於Canvas繪製的圓形進度條
 *
 * @param config 圓形進度條的設定
 * @param centerContent 圓形進度條中間的內容
 */
@Composable
fun CircularPercentageComponent(
    modifier: Modifier = Modifier,
    config: CircularPercentageConfig = CircularPercentageConfig(),
    centerContent: @Composable (CircularEvent) -> Unit = {}
) {
    var animationPlayed by remember { mutableStateOf(false) }

    val circularPercentage by remember(config.percentage) {
        derivedStateOf {
            val absPercentage = abs(config.percentage)

            if (absPercentage > 100) {
                100f
            } else {
                absPercentage
            }
        }

    }

    val circularPercentageProgress = animateFloatAsState(
        targetValue = if (animationPlayed) circularPercentage / 100 else 0f,
        animationSpec = config.animationSpec,
        label = ""
    )

    val textPercentageProgress = animateFloatAsState(
        targetValue = if (animationPlayed) config.percentage else 0f,
        animationSpec = config.animationSpec,
        label = ""
    )

    LaunchedEffect(config.percentage) {
        animationPlayed = true
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(config.radius * 2f),
            onDraw = {
                drawCircle(
                    color = config.bg,
                    radius = config.radius.toPx(),
                    style = Stroke(
                        width = config.strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                drawArc(
                    color = config.strokeColor,
                    startAngle = -90f,
                    sweepAngle = 360 * circularPercentageProgress.value,
                    useCenter = false,
                    style = Stroke(
                        width = config.strokeWidth.toPx(),
                    )
                )
            }
        )

        centerContent.invoke(
            CircularEvent(
                nowPercent = textPercentageProgress.value,
                number = config.number
            )
        )
    }
}

