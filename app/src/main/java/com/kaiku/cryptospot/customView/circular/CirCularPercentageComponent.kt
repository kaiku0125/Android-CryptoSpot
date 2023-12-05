package com.kaiku.cryptospot.customView.circular

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.presentation.theme.text_15sp_400weight
import kotlinx.coroutines.delay

/**
 * @sample CircularPercentageComponent
 *
 * @param
 */


data class CircularPercentageConfig(
    val percentage: Float = 0f,
    val number: Int = 100,
    val radius: Dp = 50.dp,
    val color: Color = Color.Green,
    val strokeWidth: Dp = 8.dp

)

@Composable
fun CircularPercentageComponent(
    modifier: Modifier = Modifier,
    config: CircularPercentageConfig = CircularPercentageConfig()
) {
    var animationPlayed by remember { mutableStateOf(false) }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) config.percentage else 0f,
        animationSpec = tween(300),
        label = ""
    )

    LaunchedEffect(true){
        delay(1000)
        animationPlayed = true
    }

    Box(
        modifier = modifier
            .background(Color.Gray)
            .size(config.radius * 2f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(config.radius * 2f),
            onDraw = {
                drawArc(
                    color = config.color,
                    startAngle = -90f,
                    sweepAngle = 360 * currentPercentage.value,
                    useCenter = false,
                    style = Stroke(
                        width = config.strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        )

        SimpleText(
            config = SimpleTextConfig(
                value = (currentPercentage.value * config.number).toInt().toString(),
                textColor = Color.Black,
                style = MaterialTheme.typography.text_15sp_400weight
            )
        )
    }
}

@Preview
@Composable
private fun CircularPercentageComponentPreview() {
    CircularPercentageComponent(
        config = CircularPercentageConfig(
            percentage = 0.8f
        )
    )
}