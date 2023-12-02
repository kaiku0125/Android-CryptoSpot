package com.kaiku.cryptospot.customView.text

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.extension.clickableEffectConfig
import com.kaiku.cryptospot.extension.data.ClickableConfig
import com.kaiku.cryptospot.presentation.theme.color_717071
import com.kaiku.cryptospot.presentation.theme.text_15sp_500weight
import com.kaiku.cryptospot.presentation.theme.text_19sp_500weight

/**
 * @sample SimpleText 用Box包裝Text來達成佈局上面的便捷性
 *
 * @param config SimpleTextConfig設定
 */

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config : SimpleTextConfig = SimpleTextConfig()
) {
    Box(
        modifier = modifier,
        contentAlignment = config.alignment
    ) {
        Text(
            modifier = textModifier,
            text = config.value,
            style = config.style,
            color = if (config.isEnable) {
                config.textColor
            } else {
                color_717071
            },
            maxLines = 1,
            letterSpacing = config.letterSpacing
        )
    }
}

@Composable
fun SimpleAnnotatedText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    style: TextStyle = MaterialTheme.typography.text_15sp_500weight
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style,
            maxLines = 1
        )
    }
}

/**
 * @sample SimpleTextWithClickEffect 擁有點擊相關特效的 SimpleText
 *
 * @param alignment 文字在區域中的位置
 * @param text 文字
 * @param style 文字樣式
 * @param textColor 文字顏色
 * @param onClick export點擊事件
 */

@Composable
fun SimpleTextWithClickEffect(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    text: String,
    style: TextStyle = MaterialTheme.typography.text_19sp_500weight,
    @ColorRes textColor: Color = Color.White,
    config: ClickableConfig = ClickableConfig(),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.clickableEffectConfig(
            config = config,
            onClick = remember {
                {
                    onClick.invoke()
                }
            }
        ),
        contentAlignment = alignment
    ) {
        Text(
            text = text,
            style = style,
            color = textColor,
            maxLines = 1
        )
    }
}

