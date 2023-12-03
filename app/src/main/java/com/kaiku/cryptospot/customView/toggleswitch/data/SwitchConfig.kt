package com.kaiku.cryptospot.customView.toggleswitch.data

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @sample SwitchConfig 基本開關元件設定
 *
 * @param checked 開關狀態
 * @param size 元件高度
 * @param switchWidth 元件寬度(根據size判斷)
 * @param padding 按鈕 indicator 對外層的 padding(根據size判斷)
 * @param parentShape 外層形狀
 * @param toggleShape 內層toggle形狀
 * @param animationSpec 開關動畫
 * @param needIcons 開關是否需要icon
 * @param switchOnIcon 開關開啟時的icon
 * @param switchOffIcon 開關關閉時的icon
 * @param borderWidth 邊框寬度
 */

data class SwitchConfig(
    val checked: Boolean = false,
    val size: Dp = 50.dp,
    val switchWidth: Dp = size * 33 / 20,
    val padding: Dp = size / 20,
    val parentShape: RoundedCornerShape = CircleShape,
    val toggleShape: RoundedCornerShape = CircleShape,
    val animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    val needIcons: Boolean = false,
    val iconSize: Dp = size / 3,
    val switchOnIcon: ImageVector = Icons.Rounded.Done,
    val switchOffIcon: ImageVector = Icons.Rounded.Clear,
    val borderWidth: Dp = 1.dp,
)
