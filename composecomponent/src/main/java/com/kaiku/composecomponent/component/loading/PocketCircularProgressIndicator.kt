package com.kaiku.composecomponent.component.loading

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.kaiku.composecomponent.R

/**
 * 口袋旋轉Icon載入動畫元件
 *
 * @param resId 欲選轉的Icon
 * @param color 初始顏色
 * @param toColor 漸變顏色
 * @param durationTimes 動畫時間，數字越大旋轉越慢
 */
@Composable
fun PocketCircularProgressIndicator(
    modifier: Modifier = Modifier,
    indicatorModifier: Modifier = Modifier,
    @DrawableRes resId: Int = R.drawable.lib_pocket_pull_refresh_indicator,
    color: Color = MaterialTheme.colorScheme.primary,
    toColor: Color? = null,
    durationTimes : Int = 1
) {
    val transition = rememberInfiniteTransition(label = "")
    val progress by transition.animateValue(
        initialValue = 0f,
        targetValue = 1f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1332 * durationTimes, // 1 and 1/3 second
                easing = LinearEasing
            )
        ),
        label = ""
    )

    // 顏色變化動畫
    val mToColor = if (toColor != null) {
        val animatedColor by transition.animateColor(
            initialValue = color,
            targetValue = toColor,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1332 * durationTimes,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )
        animatedColor
    } else {
        color
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "refreshing",
            modifier = indicatorModifier.rotate(progress * 360),
            colorFilter = ColorFilter.tint(mToColor)
        )
    }
}