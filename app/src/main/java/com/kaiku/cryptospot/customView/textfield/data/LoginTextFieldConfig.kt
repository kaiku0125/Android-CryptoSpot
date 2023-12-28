package com.kaiku.cryptospot.customView.textfield.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig

/**
 * @sample LoginTextFieldConfig 登入頁面使用者輸入框
 *
 * @param leadingIcon 標題icon
 * @param title 標題
 * @param dividerColor 分隔線顏色
 * @param text 使用者輸入
 * @param hint 尚未輸入前的提示字元
 */

data class LoginTextFieldConfig(
    val leadingIcon: @Composable (() -> Unit)? = null,
    val title: SimpleTextConfig = SimpleTextConfig(
        value = "標題"
    ),
    val dividerColor : Color = Color.White,
    val text: TextFieldConfig = TextFieldConfig(),
    val hint: SimpleTextConfig = SimpleTextConfig()
)
