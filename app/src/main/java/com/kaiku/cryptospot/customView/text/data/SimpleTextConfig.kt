package com.kaiku.cryptospot.customView.text.data

import androidx.annotation.ColorRes
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

/**
 * @sample SimpleTextConfig SimpleText的設定
 *
 * @param value 值
 * @param isEnable enable狀態
 * @param alignment 文字在區域中的位置
 * @param style 文字樣式
 * @param letterSpacing 字與字之間的間隔
 * @param textColor 文字顏色
 */

data class SimpleTextConfig(
    val value: String = "",
    val isEnable: Boolean = true,
    val alignment: Alignment = Alignment.Center,
    val style: TextStyle = TextStyle.Default,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val overflow: TextOverflow = TextOverflow.Clip,
    @ColorRes val textColor: Color = Color.White
)
