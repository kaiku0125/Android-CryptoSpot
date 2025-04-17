package com.kaiku.composecomponent.utils.basic

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getScreenWidth(): Dp = LocalConfiguration.current.screenWidthDp.dp

@Composable
fun getScreenPx() : Float {
    val width = getScreenWidth()
    return with(LocalDensity.current) { width.toPx() }
}
