package com.kaiku.composecomponent.component.grid

import android.content.Context
import android.media.AudioManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.component.grid.data.HomeMenuGridBaseItem
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.utils.ObserveAsEvents
import com.kaiku.composecomponent.utils.isPreviewMode
import com.kaiku.composecomponent.utils.localDrawableProvider
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.view.menu.BaseMenuItem
import com.kaiku.composecomponent.view.menu.MenuItemBlank
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.ReorderableLazyGridState
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable

/**
 * 用 LocalHomeMenuReorderGrid 包裝preview裝態已提外部使用
 *
 * @param homeMenuGridBaseItemList 輸入menu list
 * @param updateSortAction 更新menu排序
 * @param onClick export點擊事件
 */
@Composable
fun LocalHomeMenuReorderGrid(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    homeMenuGridBaseItemList: List<HomeMenuGridBaseItem>,
    updateSortAction: (List<HomeMenuGridBaseItem>) -> Unit = {},
    onClick: (String) -> Unit = {}
) {
    if (isPreviewMode()) {
        HomeMenuReorderGrid(
            modifier = modifier,
            drawableProvider = drawableProvider,
            items = homeMenuGridBaseItemList,
        )
    } else {
        HomeMenuReorderGridRoot(
            modifier = modifier,
            drawableProvider = drawableProvider,
            homeMenuGridBaseItemList = homeMenuGridBaseItemList,
            updateSortAction = updateSortAction,
            onClick = onClick
        )
    }
}

/**
 * 擁有實際邏輯的HomeMenuReorderGrid
 *
 * @param drawableProvider drawableRes 提供者
 * @param homeMenuGridBaseItemList 輸入menu list
 * @param updateSortAction export更新menu排序action
 * @param onClick export 點擊並回傳 menu item id
 */
@Composable
private fun HomeMenuReorderGridRoot(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    homeMenuGridBaseItemList: List<HomeMenuGridBaseItem>,
    updateSortAction: (List<HomeMenuGridBaseItem>) -> Unit = {},
    onClick: (String) -> Unit = {}
) {
    val vm = viewModel { HomeMenuReorderGridViewModel(homeMenuGridBaseItemList) }

    val state = rememberReorderableLazyGridState(
        onMove = vm::moveMenu,
        canDragOver = vm::isMenuDragEnabled
    )

    LaunchedEffect(homeMenuGridBaseItemList) {
        vm.updateList(homeMenuGridBaseItemList)
    }

    ObserveAsEvents(
        flow = vm.viewEvent,
        onEvent = { event ->
            updateSortAction(event)
        }
    )

    HomeMenuReorderGrid(
        modifier = modifier,
        drawableProvider = drawableProvider,
        state = state,
        items = vm.menus,
        onClick = onClick
    )
}

/**
 * 僅作為preview使用
 */
@Composable
private fun HomeMenuReorderGrid(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    state: ReorderableLazyGridState = rememberReorderableLazyGridState(onMove = { _, _ -> }),
    items: List<HomeMenuGridBaseItem> = emptyList(),
    onClick: (String) -> Unit = {}
) {

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = modifier.reorderable(state),
        state = state.gridState,
        contentPadding = PaddingValues(vertical = 16.sdp(), horizontal = 12.sdp()),
        verticalArrangement = Arrangement.spacedBy(4.sdp()),
        horizontalArrangement = Arrangement.spacedBy(4.sdp())
    ) {
        itemsIndexed(
            items = items,
            key = { _, item ->
                item.id
            }
        ) { index, item ->
            if (item.isLocked) {
                Box(
                    modifier = Modifier.aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (item.name.isNotEmpty()) {
                        BaseMenuItem(
                            titleConfig = PocketTextConfig(
                                value = item.name,
                                style = text13Sp()
                            ),
                            menuRes = item.iconResId,
                            iconUrl = item.iconUrl,
                            withDash = false
                        )
                    } else {
                        MenuItemBlank(
                            modifier = Modifier.background(color_252525),
                            isActive = index == items.indexOfFirst { it.name.isEmpty() }
                        )
                    }
                }
            } else {
                ReorderableItem(state, item.id) { isDragging ->
                    var isItemDragging by remember { mutableStateOf(false) }
                    isItemDragging = isDragging

                    val elevation = animateDpAsState(if (isDragging) 8.sdp() else 0.dp, label = "")
                    val bg = animateColorAsState(
                        targetValue = if (isDragging) color_414141 else color_252525,
                        label = ""
                    )

                    LaunchedEffect(isItemDragging) {
                        if (isItemDragging) {
                            (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
                                .playSoundEffect(AudioManager.FX_KEY_CLICK)

                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    }

                    BaseMenuItem(
                        modifier = Modifier
                            .detectReorderAfterLongPress(state)
                            .shadow(elevation.value)
                            .aspectRatio(1f)
                            .background(bg.value),
                        titleConfig = PocketTextConfig(
                            value = item.name,
                            style = text13Sp()
                        ),
                        menuRes = item.iconResId,
                        iconUrl = item.iconUrl,
                        deleteRes = drawableProvider.iconDelete,
                        onClick = {
                            onClick.invoke(item.id)
                        }
                    )
                }
            }
        }
    }
}