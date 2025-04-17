package com.kaiku.composecomponent.component.loading

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_1a1a1a
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.utils.text13Sp

/**
 * 進度條元件
 *
 * @property percentage 進度條百分比
 * @property total 最大進度
 * @property animationSpec 動畫設定
 * @property progressColor 進度條顏色
 * @property hoverContent 懸浮於進度條上的元件(不會超過進度條的區域！)
 */
@Composable
fun HorizontalProgressComponent(
    modifier: Modifier = Modifier,
    percentage: Float = 0.87f,
    total: Int = 10000,
    animationSpec: AnimationSpec<Float> = tween(1000),
    progressColor: Color = MaterialTheme.colorScheme.primary,
    hoverContent: @Composable () -> Unit = {
        val now = (percentage * total).toInt().toNumberFormat(
            isShowThousandsSign = true
        )
        val all = total.toNumberFormat(
            isShowThousandsSign = true
        )

        PocketText(
            modifier = Modifier.fillMaxSize(),
            config = PocketTextConfig(
                value = "$now/$all",
                style = text13Sp()
            )
        )
    }
) {
    Box(modifier = modifier) {
        val mPercentage = remember { mutableFloatStateOf(0f) }

        val percentageAnimation = animateFloatAsState(
            targetValue = mPercentage.floatValue,
            label = "percentageAnimation",
            animationSpec = animationSpec
        ).value

        LaunchedEffect(percentage) {
            if (percentage >= 1f) {
                mPercentage.floatValue = 1f
            } else {
                mPercentage.floatValue = percentage
            }
        }


        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val width = size.width
                val height = size.height

                val bodyPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = 0f,
                            top = 0f,
                            right = width,
                            bottom = height,
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                        )
                    )
                    close()
                }

                val currentXPosition = percentageAnimation * width

                clipPath(path = bodyPath) {
                    drawRect(
                        color = color_1a1a1a,
                        size = size
                    )

                    val progressPath = Path().apply {
                        moveTo(
                            x = 0f,
                            y = 0f
                        )
                        lineTo(
                            x = 0f,
                            y = height
                        )
                        lineTo(
                            x = currentXPosition,
                            y = size.height
                        )
                        lineTo(
                            x = currentXPosition,
                            y = 0f
                        )
                        close()
                    }
                    drawPath(
                        path = progressPath,
                        color = progressColor
                    )
                }
            }

        )
        hoverContent.invoke()
    }
}
