package com.kaiku.cryptospot.customView.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.button.data.BaseButtonConfig
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.extension.ratioHeight
import com.kaiku.cryptospot.extension.withEffect
import com.kaiku.cryptospot.presentation.theme.color_9e9e9f

/**
 *  @sample BaseButton
 *
 *  @param btnConfig 按鈕設定
 *  @param onClick export點擊事件
 */

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    btnConfig: BaseButtonConfig = BaseButtonConfig(),
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val mModifier = if (btnConfig.height != null) {
        modifier.ratioHeight(btnConfig.height)
    } else {
        modifier
    }

    val color = if (btnConfig.isEnable) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = color_9e9e9f,
            contentColor = Color.White
        )
    }

    Button(
        modifier = mModifier,
        colors = color,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            if (btnConfig.isEnable) {
                onClick.withEffect(
                    context = context,
                    haptic = haptic,
                    needSound = true,
                    needHaptic = false
                ).invoke()
            }
        },
    ) {
        SimpleText(
            config = btnConfig.textConfig
        )
    }
}

@Preview
@Composable
private fun PocketPrimaryButtonPreview() {
    BaseButton(
        btnConfig = BaseButtonConfig(
            textConfig = SimpleTextConfig(
                value = "按鈕"
            )
        ),
        onClick = {

        }
    )
}