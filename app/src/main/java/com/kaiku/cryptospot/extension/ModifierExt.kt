package com.kaiku.cryptospot.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.extension.data.ClickableConfig
import com.kaiku.cryptospot.presentation.theme.color_pocket_primary
import com.kaiku.cryptospot.presentation.theme.color_pocket_secondary

/**
 * @sample clickableEffectConfig onClick的點擊extension
 *
 * @param config 點擊設定
 * @param onClick export點擊事件
 */

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableEffectConfig(
    config: ClickableConfig = ClickableConfig(),
    onClick: () -> Unit
) = composed(

    factory = {
        val context = LocalContext.current
        val haptic = LocalHapticFeedback.current

        this.then(
            if (config.needRipple.not()) {
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onClick.withEffect(
                            context = context,
                            haptic = haptic,
                            needSound = config.needSound,
                            needHaptic = config.needHaptic
                        ).invoke()
                    }
                )
            } else {
                Modifier.clickable(
                    onClick = {
                        onClick.withEffect(
                            context = context,
                            haptic = haptic,
                            needSound = config.needSound,
                            needHaptic = config.needHaptic
                        ).invoke()
                    }
                )
            }

        )
    }
)

/**
 * @sample ratioHeight 自定義依照手機螢幕調整適當元件高度比例
 *
 * @param height 填入 FIGMA 給的高度
 * @param isUnderSizeConstant 當希望維持最小的大小 ➔ true, 希望自適應的狀態 ➔ false,
 */

fun Modifier.ratioHeight(
    height: Dp,
    isUnderSizeConstant: Boolean = true // 預設為維持最小大小, 類似 min Height的感覺
) = composed(
    factory = {
        val defaultHeight = 812 // figma 預設高度
        val configuration = LocalConfiguration.current

        val screenRatio by remember {
            mutableStateOf(
                (configuration.screenHeightDp.dp / defaultHeight).value
            )
        }

        this.then(
            Modifier.height(
                if (isUnderSizeConstant && screenRatio < 1) {
                    height
                } else {
                    height * screenRatio
                }
            )
        )
    }
)

fun Modifier.toStyleOfDigitalKeyBoard(
    height: Dp = 50.dp,
    background: Color = Color.Gray,
    shape : Shape = RoundedCornerShape(12.dp)
): Modifier {
    return this
        .height(height)
        .background(
            shape = shape,
            color = background
        )
        .clip(shape)
//        .border(
//            color = color_pocket_secondary,
//            width = 2.dp,
//            shape = shape
//        )
}

