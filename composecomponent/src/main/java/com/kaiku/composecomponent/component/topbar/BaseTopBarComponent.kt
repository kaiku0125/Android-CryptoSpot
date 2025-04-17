package com.kaiku.composecomponent.component.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.hoverColor
import com.kaiku.composecomponent.extension.withEffect
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text17Sp

/**
 * @param textConfig app bar 文字設定 (預設為文字並帶有資訊icon)
 * @param onInfoClick export 資訊icon點擊事件
 * @param onNavigationClick export navigation點擊事件
 * @param titleContent title內容(中間)
 * @param navContent nav內容(左側)
 * @param actionContent action內容(右側)
 * @param background 背景顏色
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopBarComponent(
    modifier: Modifier = Modifier,
    textConfig: PocketTextConfig = PocketTextConfig(),
    onInfoClick: (() -> Unit)? = null,
    onNavigationClick: (() -> Unit)? = null,
    titleContent: @Composable () -> Unit = {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PocketText(config = textConfig)
            Spacer(modifier = Modifier.width(5.sdp()))
            onInfoClick?.let {
                PocketIconButton(
                    drawableRes = R.drawable.preview_ic_information,
                    tint = MaterialTheme.colorScheme.background.hoverColor(),
                    iconSize = 22.sdp(),
                    onIconClick = {
                        onInfoClick.invoke()
                    }
                )
            }
        }
    },
    navContent: @Composable () -> Unit = {
        val context = LocalContext.current
        val haptic = LocalHapticFeedback.current

        IconButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = {
                onNavigationClick?.withEffect(
                    context = context,
                    haptic = haptic,
                    needSound = true,
                    needHaptic = false
                )?.invoke()
            },
            content = {
                Icon(
                    modifier = Modifier.size(48.sdp()),
                    painter = painterResource(id = R.drawable.lib_pocket_ic_back),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        )
    },
    actionContent: @Composable RowScope.() -> Unit = {},
    background: Color = MaterialTheme.colorScheme.background
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = titleContent,
        navigationIcon = navContent,
        actions = actionContent,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = background)
    )
}

/**
 * 口袋 top bar 基本元件
 *
 * @param title 標題
 * @param background 背景顏色
 * @param textColor 文字顏色
 * @param onInfoClick export icon 點擊事件
 * @param onBackClick export 返回點擊事件
 *
 */
@Composable
fun PocketAppBarWithBackNavigation(
    modifier: Modifier = Modifier,
    title: String = "",
    background: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onInfoClick: (() -> Unit)? = null,
    onBackClick: () -> Unit = {}
) {
    BaseTopBarComponent(
        modifier = modifier,
        textConfig = PocketTextConfig(
            value = title,
            style = text17Sp(600),
            textColor = textColor
        ),
        background = background,
        onInfoClick = onInfoClick,
        onNavigationClick = onBackClick
    )
}
