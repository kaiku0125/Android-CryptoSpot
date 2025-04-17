package com.kaiku.composecomponent.component.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.pocket_color_ffffff
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * PocketVerticalMagnetPager 具有磁鐵效應的垂直分頁

 * @param state LazyListState lazyColumn的state
 * @param tabTitles tabRowTitle 若有tabTitle則視為需要上方tab
 * @param tabHeight tab高度
 * @param items 泛型list
 * @param keySelector lazyColumn的item key
 * @param itemContent lazyColumn的item UI
 * @param onMagnetToIndex 滑動後會根據判斷吸到哪個index的callback (貌似沒有什麼用處？)
 * @param onMeasurePagerHeight 計算pager的本體高度，為了讓最後一個item可以與之同高
 */
@Composable
fun <T> PocketVerticalMagnetPager(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    state: LazyListState,
    tabTitles: List<String>? = null,
    tabHeight: Dp = 40.sdp(),
    items: List<T>,
    keySelector: (index: Int, item: T) -> Any = { index, _ -> index },
    itemContent: @Composable (index: Int, item: T) -> Unit,
    onMagnetToIndex: ((Int) -> Unit)? = null,
    onMeasurePagerHeight: ((Dp) -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // 儲存當前顯示的item高度 (key to 高度)
    val countMap = remember { mutableStateMapOf<Any, Int>() }

    // 當前所選的頁籤
    val currentSelectedIndex = remember {
        derivedStateOf { state.firstVisibleItemIndex }
    }.value

    // tab有時候會跑位，不確定為什麼先這樣做(將tab顯示的index延後pager 100毫秒)
    var smoothIndex by remember { mutableIntStateOf(currentSelectedIndex) }

    // 偵測是否為pager滾動手勢
    var isPagerControlling by remember { mutableStateOf(false) }

    LaunchedEffect(currentSelectedIndex) {
        snapshotFlow { currentSelectedIndex }
            .distinctUntilChanged()
            .conflate()
            .onEach { delay(100L) }
            .collectLatest {
                smoothIndex = it
                onMagnetToIndex?.invoke(it)
            }
    }

    // 偵測滑動磁鐵邏輯
    LaunchedEffect(state.isScrollInProgress) {
        snapshotFlow { state.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    val firstVisibleItemScrollOffset = state.firstVisibleItemScrollOffset
                    if (firstVisibleItemScrollOffset != 0) {
                        val firstVisibleItemIndex = state.firstVisibleItemIndex
                        val key = keySelector(firstVisibleItemIndex, items[firstVisibleItemIndex])
                        val currentItemHeight = countMap.getOrElse(key) { 0 }

                        val index = if (firstVisibleItemScrollOffset > currentItemHeight / 2) {
                            (firstVisibleItemIndex + 1).coerceAtMost(items.lastIndex)
                        } else {
                            firstVisibleItemIndex
                        }

                        if (isPagerControlling) {
                            scope.launch {
                                state.animateScrollToItem(index)
                            }
                        }
                    }
                }
            }
    }

    Column(modifier = modifier) {
        tabTitles?.let { mTitles ->
            RowContent(
                modifier = rowModifier.fillMaxWidth(),
                selectedIndex = smoothIndex,
                titles = mTitles,
                tabHeight = tabHeight,
                onClick = { index ->
                    isPagerControlling = false
                    scope.launch {
                        state.animateScrollToItem(index)
                    }
                }
            )
        }

        Box(
            modifier = contentModifier.nestedScroll(
                rememberVerticalMagnetConnection(
                    onPreScroll = {
                        if (isPagerControlling.not()) isPagerControlling = true
                    }
                )
            )
        ) {
            LazyColumn(
                modifier = Modifier.onGloballyPositioned {
                    with(density) {
                        onMeasurePagerHeight?.invoke(it.size.height.toDp())
                    }
                },
                state = state
            ) {
                itemsIndexed(
                    items = items,
                    key = { index, item -> keySelector(index, item) }
                ) { index, item ->
                    Surface(
                        modifier = Modifier.onGloballyPositioned {
                            countMap[keySelector(index, item)] = it.size.height
                        }
                    ) {
                        itemContent.invoke(index, item)
                    }
                }
            }
        }

    }
}

@Composable
private fun RowContent(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    titles: List<String>,
    tabHeight: Dp = 40.sdp(),
    onClick: (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        containerColor = color_252525,
        selectedTabIndex = selectedIndex,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = MaterialTheme.colorScheme.primary
            )
        },
        edgePadding = 0.dp
    ) {

        titles.forEachIndexed { index, title ->
            val isSelected = selectedIndex == index
            Tab(
                modifier = Modifier
                    .requiredHeight(tabHeight)
                    .background(color_252525),
                selected = isSelected,
                onClick = {
                    onClick.invoke(index)
                },
                text = {
                    Text(
                        text = title,
                        style = if (isSelected) {
                            text15Sp(600)
                        } else {
                            text15Sp()
                        },
                        color = if (isSelected) {
                            Color.White
                        } else {
                            color_9e9e9f
                        }
                    )
                }
            )
        }
    }
}

// 觀察pager的滾動手勢
@Composable
private fun rememberVerticalMagnetConnection(onPreScroll: () -> Unit): NestedScrollConnection {
    return remember {
        object : NestedScrollConnection {
            override suspend fun onPreFling(available: Velocity): Velocity {
                onPreScroll.invoke()
                return super.onPreFling(available)
            }
        }
    }
}
