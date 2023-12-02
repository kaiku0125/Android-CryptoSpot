package com.kaiku.cryptospot.extension.data

/**
 * @sample ClickableConfig 點擊compose設定
 *
 * @param needRipple 是否需要點擊效果
 * @param needSound 是否需要音效
 * @param needHaptic 是否需要震動
 */

data class ClickableConfig(
    val needRipple: Boolean = true,
    val needSound: Boolean = true,
    val needHaptic: Boolean = false
)
