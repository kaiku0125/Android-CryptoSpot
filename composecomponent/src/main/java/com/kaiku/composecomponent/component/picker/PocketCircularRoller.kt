package com.kaiku.composecomponent.component.picker

import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.LocalProvider.LocalDebugTag
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.debug
import com.kaiku.composecomponent.extension.getColorAnimation
import com.kaiku.composecomponent.utils.animation.animateTextStyleAsState
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * PocketCircularRoller 滾動拉霸元件
 *
 * @param width 元件寬度
 * @param itemHeight 單一拉霸元件高度
 * @param numberOfDisplayedItems
 * @param items 列表
 * @param initialItem 初始值
 * @param isEnable [items]enable的狀態
 * @param isInfinite 是否循環
 * @param selectedTextStyle 選擇時的文案字型
 * @param unSelectedTextStyle 未選擇時的文案字型
 * @param selectedTextColor 選擇時的文案顏色
 * @param unSelectedTextColor 未選擇時的文案顏色
 * @param onScrollStateChanged export 是否在滾動狀態
 * @param displayText export 顯示樣式
 * @param onItemSelected export 所選item
 */
@Composable
fun <T> PocketCircularRoller(
    modifier: Modifier = Modifier,
    width: Dp,
    itemHeight: Dp,
    numberOfDisplayedItems: Int = 3,
    items: List<T>,
    initialItem: T,
    isEnable: List<Boolean> = items.map { true },
    isInfinite: Boolean = true,
    selectedTextStyle: TextStyle = text17Sp(600),
    unSelectedTextStyle: TextStyle = text15Sp(),
    selectedTextColor: Color = Color.White,
    unSelectedTextColor: Color = color_717071,
    onScrollStateChanged: ((Boolean) -> Unit)? = null,
    displayText: (index: Int, item: T?) -> String = { _, item ->
        item.toString()
    },
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    require(isEnable.any { true })
    val tag = LocalDebugTag.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    // 顯示數量奇偶數
    val isDisplayedItemsEven = remember(numberOfDisplayedItems) {
        derivedStateOf {
            numberOfDisplayedItems % 2 == 0
        }
    }

    // Divider需依據顯示的數量來計算應顯示的顏色
    val selectedDividerIndex = remember(isDisplayedItemsEven) {
        derivedStateOf {
            if (isDisplayedItemsEven.value) {
                numberOfDisplayedItems / 2 + 1
            } else {
                (numberOfDisplayedItems + 1) / 2
            }
        }
    }
    // 錯誤時修正滾動的offset (因為lazyColumn的 scroll to item 是對齊佈局左上角)
    val offsetIndex = remember(isDisplayedItemsEven) {
        require(numberOfDisplayedItems >= 2)
        derivedStateOf {
            if (isDisplayedItemsEven.value) {
                numberOfDisplayedItems / 2
            } else {
                (numberOfDisplayedItems - 1) / 2
            }
        }
    }.value

    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }

    // 根據是否循環決定列表是否補空
    val myItems by remember(items, isInfinite) {
        mutableStateOf(
            if (isInfinite) {
                items
            } else {
                mutableListOf<T?>().apply {
                    repeat(offsetIndex) {
                        add(null)
                    }
                    addAll(items)
                    repeat(
                        if (isDisplayedItemsEven.value) {
                            offsetIndex - 1
                        } else {
                            offsetIndex
                        }
                    ) {
                        add(null)
                    }
                }
            }
        )
    }

    // 根據是否循環決定enable是否補空
    val myEnables by remember(isEnable, isInfinite) {
        mutableStateOf(
            if (isInfinite) {
                isEnable
            } else {
                mutableListOf<Boolean>().apply {
                    repeat(offsetIndex) {
                        add(true)
                    }
                    addAll(isEnable)
                    repeat(
                        if (isDisplayedItemsEven.value) {
                            offsetIndex - 1
                        } else {
                            offsetIndex
                        }
                    ) {
                        add(true)
                    }
                }
            }
        )
    }

    // 根據是否循環決定列表大小
    val count = remember(isInfinite) {
        derivedStateOf {
            if (isInfinite) {
                Int.MAX_VALUE
            } else {
                myItems.size
            }
        }
    }.value

    val scrollState = rememberLazyListState(0)
    // 監聽滾動狀態
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { isScrolling ->
                onScrollStateChanged?.invoke(isScrolling)
            }
    }
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    var itemsState by remember { mutableStateOf(myItems) }

    // 添加一個狀態來追踪是否已經完成初始化
    var isInitialized by remember { mutableStateOf(false) }

    // 針對不合法的日期進行調整
    LaunchedEffect(myEnables, lastSelectedIndex, isInfinite, scrollState) {
        snapshotFlow {
            if (isInfinite) {
                myEnables[lastSelectedIndex % itemsState.size]
            } else {
                myEnables[lastSelectedIndex]
            } to
                    scrollState.isScrollInProgress
        }
            .filter { (_, isScrolling) -> isScrolling.not() }
            .debounce(100)
            .collectLatest { (selectionEnable, _) ->
                if (!selectionEnable) {
                    // 偵測到使用者選取非法的值並打印log
                    val selection = if (isInfinite) {
                        val listIndex = lastSelectedIndex % itemsState.size
                        displayText.invoke(listIndex, itemsState.getOrNull(listIndex))
                    } else {
                        displayText.invoke(lastSelectedIndex, itemsState.getOrNull(lastSelectedIndex))
                    }
                    tag.debug("[$selection] selection not enabled !!")

                    // 找到最近的enable值
                    val nearestValidIndex = if (isInfinite) {
                        val listIndex = lastSelectedIndex % itemsState.size
                        val validIndex = findNearestEnabledIndex(
                            list = myEnables,
                            currentIndex = listIndex
                        )?.minus(offsetIndex)
                        validIndex?.let { lastSelectedIndex - (listIndex - it) }
                    } else {
                        findNearestEnabledIndex(
                            list = myEnables,
                            currentIndex = lastSelectedIndex
                        )?.minus(offsetIndex)
                    }

                    // 找到之後滾動到該處
                    if (nearestValidIndex != null && nearestValidIndex >= 0) {
                        scope.launch {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            scrollState.animateScrollToItem(nearestValidIndex)
                        }
                    } else {
                        // 若有其他任何例外，則回到預設值
                        var targetIndex = myItems.indexOf(initialItem) - offsetIndex
                        if (isInfinite) {
                            targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
                        }
                        scope.launch {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            scrollState.animateScrollToItem(targetIndex)
                        }
                    }
                }
            }
    }

    // 修改初始化邏輯
    LaunchedEffect(myItems) {
        var targetIndex = myItems.indexOf(initialItem) - offsetIndex
        if (isInfinite) {
            targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        }
        itemsState = myItems
        lastSelectedIndex = targetIndex
        if (isInfinite) {
            scrollState.scrollToItem(targetIndex)
        } else {
            scope.launch {
                scrollState.animateScrollToItem(targetIndex)
            }
        }
        isInitialized = true
    }

    // 當有外部的強制輸入時，與外部同步
    LaunchedEffect(initialItem) {
        snapshotFlow {
            Triple(
                initialItem,
                if (isInfinite) {
                    itemsState[lastSelectedIndex % itemsState.size]
                } else {
                    itemsState[lastSelectedIndex]
                },
                scrollState.isScrollInProgress
            )
        }
            .filter { (_, _, isScrolling) -> isScrolling.not() }
            .debounce(200)
            .collectLatest { (initialItem, localIndex, _) ->
                if (localIndex != initialItem) {
                    var targetIndex = myItems.indexOf(initialItem) - offsetIndex
                    if (isInfinite) {
                        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
                    }
                    lastSelectedIndex = targetIndex
                    scope.launch {
                        scrollState.animateScrollToItem(targetIndex)
                    }
                }
            }
    }

    Box(
        modifier = modifier
            .width(width)
            .height(itemHeight * numberOfDisplayedItems)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(
                lazyListState = scrollState,
                snapPosition = if (isDisplayedItemsEven.value) SnapPosition.End else SnapPosition.Center
            )
        ) {
            items(
                count = count,
                itemContent = { i ->
                    val item = if (isInfinite) {
                        itemsState[i % itemsState.size]
                    } else {
                        itemsState[i]
                    }

                    PocketText(
                        modifier = Modifier
                            .height(itemHeight)
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                // 這裡會根據每多一個 offsetIndex 就相當於多一個itemHeight (itemHalfHeight * 2)
                                // 也就是說當顯示數量為 4||5時，offsetIndex = 2，因此會是減去1.5倍item的高度
                                val y = coordinates.positionInParent().y - itemHalfHeight * (2 * (offsetIndex - 1) + 1)
                                val parentHalfHeight = (coordinates.parentCoordinates?.size?.height ?: 0) / 2f
                                val isSelected =
                                    (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)


                                if (isSelected && lastSelectedIndex != i) {
                                    val isValidSelection = if (isInfinite) {
                                        myEnables[i % itemsState.size]
                                    } else {
                                        myEnables[i]
                                    }
                                    if (isInitialized) {
                                        item?.let {
                                            if (isInfinite) {
                                                onItemSelected(i % itemsState.size, item)
                                            } else {
                                                onItemSelected(i, item)
                                            }
                                        }
                                    }
                                    lastSelectedIndex = i
                                }
                            },
                        config = PocketTextConfig(
                            value = if (isInfinite) {
                                displayText.invoke(i % itemsState.size, item)
                            } else {
                                displayText.invoke(i, item)
                            },
                            style = animateTextStyleAsState(
                                targetValue = if (lastSelectedIndex == i && isInitialized) {
                                    selectedTextStyle
                                } else {
                                    unSelectedTextStyle
                                },
                                animationSpec = spring()
                            ).value,
                            textColor = getColorAnimation(
                                toColor = if (lastSelectedIndex == i && isInitialized) {
                                    selectedTextColor
                                } else {
                                    unSelectedTextColor
                                }
                            ),
                            disableTextColor = unSelectedTextColor,
                            isEnable = if (isInfinite) {
                                myEnables[lastSelectedIndex % itemsState.size]
                            } else {
                                myEnables[lastSelectedIndex]
                            }
                        )
                    )

                }
            )
        }

        repeat(numberOfDisplayedItems + 1) {
            if (it != 0) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 1.sdp())
                        .offset(y = itemHeight * it - 1.sdp()),
                    thickness = 1.sdp(),
                    color = if (it == selectedDividerIndex.value) {
                        Color.White
                    } else {
                        unSelectedTextColor
                    }
                )
            }
        }
    }
}

/**
 * 查找最近的可用索引
 * @param list 布爾值列表，表示每個位置是否可用
 * @param currentIndex 當前索引
 * @param searchForward 是否向後查找（當向前找不到時）
 * @param allowLoop 是否允許循環查找
 * @return 找到的索引，如果沒找到返回 null
 */
private fun findNearestEnabledIndex(
    list: List<Boolean>,
    currentIndex: Int,
    searchForward: Boolean = true,
    allowLoop: Boolean = false
): Int? {
    if (list.isEmpty()) return null

    // 向前查找
    for (offset in 1..list.size) {
        val prevIndex = (currentIndex - offset).let {
            if (allowLoop) {
                (it + list.size) % list.size
            } else {
                it
            }
        }

        if (prevIndex < 0) break
        if (list[prevIndex]) return prevIndex
    }

    // 如果允許向後查找且向前沒找到
    if (searchForward) {
        for (offset in 1..list.size) {
            val nextIndex = (currentIndex + offset).let {
                if (allowLoop) {
                    it % list.size
                } else {
                    it
                }
            }

            if (nextIndex >= list.size) break
            if (list[nextIndex]) return nextIndex
        }
    }

    return null
}