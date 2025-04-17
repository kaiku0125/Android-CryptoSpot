package com.kaiku.composecomponent.component.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.utils.localDrawableProvider
import com.kaiku.composecomponent.utils.sdp

/**
 * 帶有前導radio的選擇元件
 *
 * @param drawableProvider 依據各平台實作的 DrawableProvider
 * @param isChecked 是否勾選
 * @param isEnabled 是否可被選擇
 * @param background 背景顏色
 * @param horizontalPadding 水平padding
 * @param paddingToText radio元件與顯示內容間距
 * @param clickableConfig 點擊效果設定
 * @param onFieldClick 區域被點擊
 * @param content 顯示內容
 */
@Composable
fun LeadingRadioSelectedComponent(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    isChecked: Boolean = false,
    isEnabled: Boolean,
    tint: Color = Color.Unspecified,
    background: Color = MaterialTheme.colorScheme.background,
    horizontalPadding: Dp = 16.sdp(),
    paddingToText: Dp = 5.sdp(),
    clickableConfig: ClickableConfig = ClickableConfig(),
    onFieldClick : () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .background(background)
            .clickableEffectConfig(
                config = clickableConfig,
                onClick = {
                    if (isEnabled) {
                        onFieldClick.invoke()
                    }
                }
            )
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PocketRadioIconComponent(
            modifier = Modifier.padding(top = 1.sdp()), // 不確定為何會有1.dp的跑版
            drawableProvider = drawableProvider,
            isChecked = isChecked,
            isEnabled = isEnabled,
            tint = tint,
            iconSize = 24.sdp()
        )

        Spacer(modifier = Modifier.width(paddingToText))

        content.invoke(this)
    }
}

