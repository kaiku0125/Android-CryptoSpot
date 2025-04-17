package com.kaiku.composecomponent.component.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

/**
 * PocketParagraphText 帶有點點的段落分隔文本
 * @param config 文字設定
 * !! 注意 : 必須填入 lineHeight
 */
@Composable
fun PocketParagraphText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config: PocketTextConfig
) {

    if (config.value.contains("\n")) {
        val density = LocalDensity.current
        val dotFont = config.style.fontSize // 這裏把點點大小調整只有style的一半
        val dotSize = with(density) { dotFont.toDp() }

        // 對齊的地方是依據點點的left top，因此還要再往下位移dotFont，再加上dotFont自身高度的一半
        // 因此算出來的高度會是 = (1/2 + 1/2的1/2)
        val dotPadding = with(density) { (dotFont / 4 * 3).toDp() }

        val paragraphs = config.value.split("\n")

        Column(modifier = modifier) {
            paragraphs.forEachIndexed { index, paragraph ->
                if (paragraph.isNotEmpty()) {
                    Column {
                        Row {
                            Box(
                                modifier = Modifier.padding(end = dotSize / 2),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "●",
                                    color = Color.Transparent,
                                    fontSize = dotFont
                                )
                                Text(
                                    text = "●",
                                    color = Color.White,
                                    fontSize = dotFont / 2
                                )
                            }
                            PocketText(
                                textModifier = textModifier,
                                config = config.copy(
                                    value = paragraph
                                )
                            )
                        }
                        if (index != paragraphs.lastIndex) {
                            Text(
                                text = "",
                                lineHeight = if (config.lineHeight == TextUnit.Unspecified) {
                                    config.style.fontSize
                                } else {
                                    config.lineHeight / 2
                                }
                            )
                        }
                    }
                }
            }
        }
    } else {
        PocketText(
            modifier = modifier,
            textModifier = textModifier,
            config = config
        )
    }
}