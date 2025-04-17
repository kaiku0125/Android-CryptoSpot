package com.kaiku.composecomponent.component.bottomsheet

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.component.button.PocketRotationIconButton
import com.kaiku.composecomponent.component.radio.TrailingRadioSelectedComponent
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.hoverColor
import com.kaiku.composecomponent.utils.sdp

/**
 * 口袋 bottom sheet 選單元件
 *
 * @param selectedIndex 當前選中的index
 * @param items 選單內容
 * @param shape 觸發區域形狀
 * @param backgroundColor 觸發區域背景顏色
 * @param border 觸發區域背景顏色
 * @param sheetState sheet狀態
 * @param sheetConfig sheet設定
 * @param onItemSelected export 所選內容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketBottomSheetSelector(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<PocketTextConfig>,
    shape: Shape = RoundedCornerShape(8.sdp()),
    @ColorRes backgroundColor: Color = MaterialTheme.colorScheme.surface,
    border: BorderStroke? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    sheetConfig: BottomSheetSceneConfig = BottomSheetSceneConfig(),
    titleContent: @Composable RowScope.() -> Unit = {
        AnimatedPocketText(
            aniModifier = Modifier.weight(1f, fill = false),
            config = items[selectedIndex]
        )
    },
    onItemSelected: (Int) -> Unit
) {
    var isSheetVisible by rememberSaveable { mutableStateOf(false) }

    val state = rememberLazyListState()

    LaunchedEffect(isSheetVisible) {
        if (isSheetVisible.not()) {
            state.scrollToItem(0)
        }
    }

    Surface(
        modifier = modifier.clickableEffectConfig { isSheetVisible = true },
        shape = shape,
        color = backgroundColor,
        border = border
    ) {
        Row(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            titleContent.invoke(this)

            Row {
                Spacer(modifier = Modifier.width(8.sdp()))
                PocketRotationIconButton(
                    isExpand = isSheetVisible,
                    imageVector = Icons.Default.KeyboardArrowDown,
                    tint = backgroundColor.hoverColor(),
                    iconSize = 24.sdp(),
                    onIconClick = { isSheetVisible = true }
                )
            }
        }
    }

    PocketBottomSheet(
        modifier = sheetConfig.screenRatio?.let {
            Modifier.nestedScroll(
                connection = rememberPocketBottomSheetConnection(
                    state = state,
                    isVisible = isSheetVisible
                )
            )
        } ?: Modifier,
        isVisible = isSheetVisible,
        sheetState = sheetState,
        config = sheetConfig,
        onDismiss = { isSheetVisible = false },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = state,
            ) {
                itemsIndexed(
                    items = items,
                    key = { index, _ -> index }
                ) { index, config ->
                    Column {
                        TrailingRadioSelectedComponent(
                            modifier = Modifier
                                .height(48.sdp())
                                .fillMaxWidth(),
                            isChecked = index == selectedIndex,
                            horizontalPadding = 20.sdp(),
                            background = sheetConfig.background,
                            onFieldClick = {
                                onItemSelected.invoke(index)
                                isSheetVisible = false
                            },
                            content = {
                                PocketText(config = config)
                            }
                        )
                    }

                    if (index != items.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 20.sdp()),
                            thickness = 1.sdp(),
                            color = color_252525
                        )
                    } else {
                        Spacer(modifier = Modifier.height(50.sdp()))
                    }
                }
            }
        }
    )
}

@Composable
private fun rememberPocketBottomSheetConnection(
    state: LazyListState,
    isVisible: Boolean,
): NestedScrollConnection {
    var accumulatedScroll by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(isVisible) { accumulatedScroll = 0f }

    return remember(state, isVisible, accumulatedScroll) {
        localCustomConnection(
            state = state,
            accumulatedScroll = accumulatedScroll,
            onAccumulate = { delta ->
                accumulatedScroll += delta
            },
            resetAccumulation = {
                accumulatedScroll = 0f
            }
        )
    }
}

private fun localCustomConnection(
    state: LazyListState,
    scrollThreshold: Float = 200f,
    accumulatedScroll: Float,
    onAccumulate: (Float) -> Unit,
    resetAccumulation: () -> Unit,
): NestedScrollConnection {
    return object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            val isTop = state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0
            val isLazyListConsuming = accumulatedScroll < scrollThreshold
            if (isTop && delta > 0) {
                // 處於列表第一個時 && 使用者往上滑
                onAccumulate.invoke(delta)

                if (isLazyListConsuming) {
                    return available
                }
            } else {
                resetAccumulation.invoke()
            }
            return Offset.Zero
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            val isTop = state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0
            val isLazyListConsuming = accumulatedScroll < scrollThreshold
            if (isTop && isLazyListConsuming) {
                // 若列表正在消耗中，則在此把剩餘的滾動事件消耗掉
                return available
            }
            return Offset.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            return Velocity.Zero
        }
    }
}