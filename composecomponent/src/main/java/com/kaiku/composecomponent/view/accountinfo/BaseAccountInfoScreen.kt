package com.kaiku.composecomponent.view.accountinfo

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.topbar.PocketAppBarWithBackNavigation
import com.kaiku.composecomponent.utils.sdp

/**
 * base 帳務頁面
 *
 * @param topBarTitle 標題
 * @param topBarBackground 標題背景顏色
 * @param accountContent 帳號UI
 * @param searchContent 字串搜尋UI
 * @param calendarContent 時間篩選UI
 * @param summaryContent 總覽UI
 * @param listContent 列表UI
 * @param onBackPress export 返回點擊事件
 */
@Composable
fun BaseAccountInfoScreen(
    modifier: Modifier = Modifier,
    topBarTitle: String,
    topBarBackground: Color = MaterialTheme.colorScheme.background,
    accountContent: @Composable ColumnScope.() -> Unit,
    searchContent: @Composable (ColumnScope.() -> Unit)? = null,
    calendarContent: @Composable ColumnScope.() -> Unit,
    summaryContent: @Composable (ColumnScope.() -> Unit)? = null,
    listContent: @Composable ColumnScope.() -> Unit,
    onBackPress: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            PocketAppBarWithBackNavigation(
                modifier = Modifier.height(44.sdp()),
                title = topBarTitle,
                background = topBarBackground,
                onBackClick = onBackPress
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager.clearFocus() }
                    )
                }
        ) {
            accountContent.invoke(this)
            searchContent?.invoke(this)
            PocketSpacer(height = 12)
            calendarContent.invoke(this)
            summaryContent?.invoke(this)
            listContent.invoke(this)
        }
    }
}
