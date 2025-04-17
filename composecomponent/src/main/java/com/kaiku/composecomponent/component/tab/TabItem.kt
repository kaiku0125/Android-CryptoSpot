package com.kaiku.composecomponent.component.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig

/**
 * 基本tab item 設定
 *
 * @property isSelected 是否被選擇
 * @property shape 元件
 * @property tabWidth tab寬度
 * @property selectedTextColor 被選擇時文字的顏色(未被選擇時走textConfig中的顏色)
 * @property textConfig SimpleText 設定
 */
data class TabItemConfig(
    val isEnabled: Boolean = true,
    val isSelected: Boolean = false,
    val shape: Shape = RoundedCornerShape(8.dp),
    val tabWidth: Dp = 50.dp,
    val selectedTextColor: Color = White,
    val disableTextColor: Color = color_9e9e9f,
    val textConfig: PocketTextConfig = PocketTextConfig()
)

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    config: TabItemConfig = TabItemConfig(),
    onClick: () -> Unit
) {
    val dynamicTextColor: Color by animateColorAsState(
        targetValue = if (!config.isEnabled) {
            config.disableTextColor
        } else if (config.isSelected) {
            config.selectedTextColor
        } else {
            config.textConfig.textColor
        },
        animationSpec = tween(easing = LinearEasing),
        label = ""
    )

    PocketTextWithClickEffect(
        modifier = modifier
            .width(config.tabWidth)
            .clip(config.shape),
        config = PocketTextConfig(
            value = config.textConfig.value,
            style = config.textConfig.style,
            textColor = dynamicTextColor,
            isEnable = config.isEnabled
        ),
        onClick = onClick
    )
}

@Composable
fun TitleTabItem(
    modifier: Modifier = Modifier,
    config: TabItemConfig = TabItemConfig(),
    onClick: () -> Unit
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (config.isSelected) {
            White
        } else {
            White
        },
        animationSpec = tween(easing = LinearEasing),
        label = ""
    )

    Box(
        modifier = modifier
            .clip(config.shape)
            .width(config.tabWidth)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickableEffectConfig(
                    config = ClickableConfig(
                        needRipple = false,
                        needSound = false,
                        needHaptic = false
                    ),
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            PocketText(
                modifier = Modifier.width(config.tabWidth),
                config = PocketTextConfig(
                    value = config.textConfig.value,
                    style = config.textConfig.style.copy(
                        fontWeight = if (config.isSelected) FontWeight.Bold else FontWeight.Normal
                    ),
                    textColor = tabTextColor
                )
            )
        }

    }
}