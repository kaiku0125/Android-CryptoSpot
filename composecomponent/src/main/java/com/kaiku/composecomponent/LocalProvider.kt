package com.kaiku.composecomponent

import androidx.compose.runtime.compositionLocalOf

object LocalProvider {

    /**
     * 主要是給外部Debug使用
     */
    val LocalDebugTag = compositionLocalOf { "" }

    /**
     * 是否顯示Spacer與padding於Preview時的間隔距離
     */
    val LocalSpacerIndicator = compositionLocalOf { true }

}