package com.kaiku.composecomponent.component.lazycolumn

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.kaiku.composecomponent.utils.animation.getAlphaAnimation
import com.kaiku.composecomponent.utils.isPreviewMode
import kotlinx.coroutines.flow.collectLatest

/**
 * 帶有淡入淡出效果的 LazyColumn item
 *
 * @param state LazyColumn 狀態
 * @param key 列表 item 之 unique key
 * @param animationSpec 淡入淡出動畫設定
 * @param content 顯示UI
 */
@Composable
fun AnimatedLazyListItem(
    modifier: Modifier = Modifier,
    state: LazyListState,
    key: Any,
    animationSpec: AnimationSpec<Float>? = null,
    content: @Composable (Modifier) -> Unit
) {
    val defaultVisibility = isPreviewMode()
    var itemVisible by remember { mutableStateOf(defaultVisibility) }

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo }.collectLatest {
            itemVisible = it.visibleItemsInfo.any { info ->
                info.key == key
            }
        }
    }

    content.invoke(
        modifier.alpha(
            alpha = getAlphaAnimation(
                visible = itemVisible,
                animationSpec = animationSpec
            )
        )
    )
}