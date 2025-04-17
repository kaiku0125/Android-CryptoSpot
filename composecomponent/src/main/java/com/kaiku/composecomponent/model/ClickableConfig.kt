package com.kaiku.composecomponent.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * ClickableConfig 元件點擊設定
 *
 * @param needRipple 是否需要點擊漣漪效果
 * @param needSound 是否需要音效
 * @param needHaptic 是否需要震動 (預設關閉)
 * @param clickEffect 是否需要點擊縮放 (預設關閉)
 *                    若元件處在scrollable的父類元件中點擊效果會延遲是正常的行為(官方說法)
 * @param bounded 漣漪是否受元件大小限制
 * @param rippleRadius 漣漪半徑
 * @param rippleColor 漣漪顏色
 */
data class ClickableConfig(
    val needRipple: Boolean = true,
    val needSound: Boolean = true,
    val needHaptic: Boolean = false,
    val clickEffect: Boolean = false,
    val bounded: Boolean = true,
    val rippleRadius: Dp = Dp.Unspecified,
    val rippleColor: Color = Color.Unspecified,
) {
    companion object {
        // 最原始的點擊效果
        val DEFAULT = ClickableConfig()

        // 不要有任何的效果
        val SILENCE = ClickableConfig(
            needRipple = false,
            needSound = false,
            needHaptic = false,
            clickEffect = false,
            bounded = false,
            rippleColor = Color.Transparent
        )

        // 不管 icon 是方是圓，都能擁有圓形的漣漪特效
        val ROUNDED_ICON = ClickableConfig(
            needRipple = true,
            needSound = true,
            needHaptic = false,
            clickEffect = true,
            bounded = false,
            rippleRadius = Dp.Unspecified,
            rippleColor = Color.Unspecified
        )

        // 根據是否enable來決定點擊效果
        fun byState(
            isEnable: Boolean,
            iconSize: Dp = Dp.Unspecified,
            needHaptic: Boolean = false,
            rippleColor: Color = Color.Unspecified
        ): ClickableConfig = if (isEnable) {
            ROUNDED_ICON.copy(
                needHaptic = needHaptic,
                rippleRadius = iconSize * 0.8f,
                rippleColor = rippleColor
            )
        } else {
            SILENCE
        }
    }

}

@Composable
fun rememberClickableConfig(
    needRipple: Boolean = true,
    needSound: Boolean = true,
    needHaptic: Boolean = false,
    clickEffect: Boolean = false,
    bounded: Boolean = true,
    rippleRadius: Dp = Dp.Unspecified,
    rippleColor: Color = Color.Unspecified,
): ClickableConfig {
    return remember(needRipple, needSound, needHaptic, clickEffect, bounded, rippleRadius, rippleColor) {
        ClickableConfig(
            needRipple = needRipple,
            needSound = needSound,
            needHaptic = needHaptic,
            clickEffect = clickEffect,
            bounded = bounded,
            rippleRadius = rippleRadius,
            rippleColor = rippleColor
        )
    }
}

/**
 * 特別處理點擊Icon的相關設定
 *
 * @param isEnable 元件是否可被點擊
 * @param iconSize Icon大小
 * @param needHaptic 是否震動
 * @param clickEffect 是否縮放
 * @param rippleColor 漣漪顏色
 */
@Composable
fun rememberIconClickableConfig(
    isEnable: Boolean = true,
    iconSize: Dp,
    needHaptic: Boolean = false,
    clickEffect: Boolean = true,
    rippleColor: Color = Color.Unspecified
): ClickableConfig {
    return remember(isEnable, iconSize, needHaptic, rippleColor) {
        if (isEnable) {
            ClickableConfig.ROUNDED_ICON.copy(
                needHaptic = needHaptic,
                clickEffect = clickEffect,
                rippleRadius = iconSize * 0.8f,
                rippleColor = rippleColor
            )
        } else {
            ClickableConfig.SILENCE
        }
    }
}


/**
 * 取得屬於點擊Icon的相關設定
 *
 * @param iconSize Icon大小
 * @param needHaptic 是否震動
 * @param rippleColor 漣漪顏色
 */

@Deprecated("替換成 rememberIconClickableConfig")
@Composable
fun getIconClickableConfig(
    iconSize: Dp,
    needHaptic: Boolean = false,
    rippleColor: Color = Color.Unspecified
) = ClickableConfig.byState(
    isEnable = true,
    iconSize = iconSize,
    needHaptic = needHaptic,
    rippleColor = rippleColor
)