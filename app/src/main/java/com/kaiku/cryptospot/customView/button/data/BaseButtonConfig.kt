package com.kaiku.cryptospot.customView.button.data

import androidx.compose.ui.unit.Dp
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig

/**
 * @sample BaseButtonConfig
 *
 * @param textConfig 按鈕文字設定
 * @param isEnable  按鈕是否啟用
 * @param height 按鈕高度 (若有設定高度則會走)
 */

data class BaseButtonConfig(
    val textConfig: SimpleTextConfig = SimpleTextConfig(),
    val isEnable: Boolean = true,
    val height: Dp? = null
)
