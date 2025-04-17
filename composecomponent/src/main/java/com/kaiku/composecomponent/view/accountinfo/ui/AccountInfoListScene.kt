package com.kaiku.composecomponent.view.accountinfo.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.PullToRefreshState
import com.github.fengdai.compose.pulltorefresh.rememberPullToRefreshState
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.loading.PocketPullRefreshIndicator
import com.kaiku.composecomponent.component.text.PocketAutoSizeText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text12Sp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.view.accountinfo.data.AccountInfoListTitleState
import com.kaiku.composecomponent.view.accountinfo.data.FilterState
import timber.log.Timber

@Composable
fun AccountInfoListScene(
    modifier: Modifier = Modifier,
    isEmpty: Boolean,
    pullRefreshState: PullToRefreshState,
    listTitleStates: List<AccountInfoListTitleState>? = null,
    listItemContent: @Composable () -> Unit,
    emptyItemContent: @Composable () -> Unit = { EmptyListContent() },
    onTitleClick: (Int) -> Unit,
    onRefresh: () -> Unit
) {
    
    Column(modifier = modifier) {
        listTitleStates?.let {
            TitleContent(
                titles = it,
                onTitleClick = onTitleClick
            )
        }

        PullToRefresh(
            state = pullRefreshState,
            onRefresh = onRefresh,
            indicator = { s, trigger, offset ->
                PocketPullRefreshIndicator(
                    state = s,
                    refreshTriggerDistance = trigger,
                    refreshingOffset = offset
                )
            }
        ) {
            Crossfade(
                targetState = isEmpty,
                label = ""
            ) { empty ->
                if (empty) {
                    emptyItemContent.invoke()
                } else {
                    listItemContent.invoke()
                }
            }
        }
    }
}

@Composable
private fun TitleContent(
    modifier: Modifier = Modifier,
    titles: List<AccountInfoListTitleState>,
    onTitleClick: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.sdp()),
        color = color_333333
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 12.sdp(),
                    end = 34.sdp()
                )
        ) {
            titles.forEachIndexed { index, state ->
                AccountInfoBaseItem(
                    modifier = Modifier
                        .weight(state.weight)
                        .fillMaxHeight(),
                    textConfig = PocketTextConfig(
                        value = state.title,
                        style = text12Sp(),
                        textColor = color_9e9e9f,
                        maxLines = 5
                    ),
                    filterState = state.filterState,
                    onFieldClick = {
                        onTitleClick.invoke(index)
                    }
                )
            }
        }
    }
}

@Composable
fun AccountInfoBaseItem(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textConfig: PocketTextConfig,
    needAutoSize: Boolean = false,
    filterState: FilterState? = null,
    onFieldClick: (() -> Unit)? = null
) {

    val mModifier = filterState?.let {
        modifier.clickableEffectConfig { onFieldClick?.invoke() }
    } ?: modifier

    Row(
        modifier = mModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (needAutoSize) {
            PocketAutoSizeText(
                config = textConfig
            )
        } else {
            PocketText(
                textModifier = textModifier,
                config = textConfig
            )
        }
        Spacer(modifier = Modifier.width(4.sdp()))
        filterState?.let { filter ->
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.lib_pocket_ic_filter_ascend),
                    tint = if (filter == FilterState.ASCEND) Color.White else color_9e9e9f,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(4.sdp()))
                Icon(
                    painter = painterResource(id = R.drawable.lib_pocket_ic_filter_decend),
                    tint = if (filter == FilterState.DESCEND) Color.White else color_9e9e9f,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun EmptyListContent(modifier: Modifier = Modifier) {
    PocketText(
        modifier = modifier
            .fillMaxWidth()
            .height(72.sdp()),
        config = PocketTextConfig(
            value = "尚無紀錄",
            style = text14Sp()
        )
    )
}