package com.kaiku.composecomponent.component.click

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberClickableConfig

/**
 * SurfaceWithClickableEffect 擁有點擊效果的擴展元件
 * ！由於Scaling會造成background重組，因此使用Surface！
 *
 * 使用場景：想要讓元件擁有點擊效果
 *
 * 其餘 param 與 Surface 相同
 * @param isScaleEnabled 是否需要點擊效果
 * @param clickableConfig 點擊設定(clickEffect必須為false)
 * @param content 內容UI
 * @param onClick export點擊事件
 */
@Composable
fun SurfaceWithClickableEffect(
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    isScaleEnabled: Boolean = true,
    clickableConfig: ClickableConfig = rememberClickableConfig(
        needSound = isScaleEnabled,
        needRipple = isScaleEnabled,
        clickEffect = false
    ),
    content: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    Surface(
        modifier = modifier.scale(if (isScaleEnabled) scale else 1f),
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
        content = {
            Box(
                modifier = boxModifier.clickableEffectConfig(
                    config = clickableConfig,
                    onScaling = { scale = it },
                    onClick = onClick
                )
            ) {
                content.invoke(this)
            }
        }
    )
}