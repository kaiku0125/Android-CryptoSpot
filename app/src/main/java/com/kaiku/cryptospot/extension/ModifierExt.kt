package com.kaiku.cryptospot.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.presentation.theme.color_pocket_primary
import com.kaiku.cryptospot.presentation.theme.color_pocket_secondary

/**
 * @param needEffect 是否需要點擊效果
 * @param onClick export點擊事件
 */

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableEffect(
    needEffect: Boolean,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            if (needEffect.not()) {
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onClick()
                    }
                )
            } else {
                Modifier.clickable(
                    onClick = {
                        onClick()
                    }
                )
            }

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

