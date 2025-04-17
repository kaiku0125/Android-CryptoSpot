package com.kaiku.composecomponent.component.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig

@Composable
fun PocketTextWithBottomLine(
    modifier: Modifier = Modifier,
    config: PocketTextConfig = PocketTextConfig(),
    onClick: (() -> Unit)? = null,
    clickableConfig: ClickableConfig = ClickableConfig(
        needRipple = onClick != null,
        needSound = onClick != null,
        needHaptic = false
    )
) {
    PocketText(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawLine(
                    color = config.textColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2f
                )
            }
            .clickableEffectConfig(
                config = clickableConfig,
                onClick = {
                    onClick?.invoke()
                }
            ),
        config = config
    )
}