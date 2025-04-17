package com.kaiku.composecomponent.component.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.withEffect
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.utils.sdp

/**
 * PocketPrimaryButton 主要顏色 pocket button
 *
 * @param config 文字設定
 * @param clickableConfig 點擊設定
 * @param isEnable 是否可以點擊
 * @param height 按鈕高度
 * @param shape 按鈕形狀
 * @param border 按鈕邊框
 * @param primaryColor 按鈕顏色
 * @param contentPadding 按鈕內容padding
 * @param interactionSource 元件互動資源
 * @param onClick export 點擊事件
 * @param content 按鈕內容UI(預設為文字)
 */
@Composable
fun PocketPrimaryButton(
    modifier: Modifier = Modifier,
    config: PocketTextConfig = PocketTextConfig(),
    clickableConfig: ClickableConfig = rememberClickableConfig(clickEffect = true),
    isEnable: Boolean = true,
    height: Dp? = null,
    shape: Shape = RoundedCornerShape(8.sdp()),
    border: BorderStroke = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && clickableConfig.clickEffect) 0.95f else 1f,
        label = "scale"
    )

    val mModifier = if (height != null) {
        modifier.height(height)
    } else {
        modifier
    }

    Button(
        modifier = mModifier.scale(scale),
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = Color.White,
            disabledContainerColor = color_9e9e9f,
            disabledContentColor = Color.White
        ),
        shape = shape,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = {
            if (isEnable) {
                onClick.withEffect(
                    context = context,
                    haptic = haptic,
                    needSound = clickableConfig.needSound,
                    needHaptic = clickableConfig.needHaptic
                ).invoke()
            }
        }
    ) {
        content?.invoke() ?: run {
            PocketText(
                config = config
            )
        }
    }
}