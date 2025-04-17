package com.kaiku.composecomponent.component.bottomsheet

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.kaiku.composecomponent.utils.animation.getAlphaAnimation
import com.kaiku.composecomponent.utils.basic.getScreenWidth
import com.kaiku.composecomponent.utils.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

/**
 * 口袋 bottom sheet 元件
 *
 * @param isVisible 是否展開
 * @param sheetState bottom sheet 狀態
 * @param config bottom sheet 設定
 * @param content bottom sheet UI
 * @param onDismiss export 收合事件
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketBottomSheet(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier.padding(horizontal = 20.sdp()),
    isVisible: Boolean = false,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    config: BottomSheetSceneConfig = BottomSheetSceneConfig(),
    titleRowContent: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit
) {
    var smoothVisibility by rememberSaveable { mutableStateOf(false) }
    var alphaVisibility by rememberSaveable { mutableStateOf(false) }
    val dismiss = remember {
        {
            onDismiss.invoke()
        }
    }

    LaunchedEffect(isVisible) {
        snapshotFlow { isVisible }.collectLatest {
            if (it) {
                smoothVisibility = true
                delay(50)
                sheetState.show()
            } else {
                sheetState.hide()
                delay(50)
                smoothVisibility = false
            }
        }
    }

    LaunchedEffect(isVisible) {
        delay(50)
        alphaVisibility = isVisible
    }

    if (smoothVisibility) {
        ModalBottomSheet(
            sheetMaxWidth = getScreenWidth(),
            sheetState = sheetState,
            dragHandle = {},
            onDismissRequest = dismiss,
            content = {
                BaseBottomSheetScene(
                    modifier = modifier,
                    columnModifier = columnModifier.alpha(
                        getAlphaAnimation(
                            visible = alphaVisibility,
                            animationSpec = config.contentAnimationSpec
                        ),
                    ),
                    titleModifier = titleModifier,
                    titleRowContent = titleRowContent,
                    config = config,
                    content = content,
                    onDismiss = dismiss
                )
            }
        )
    }
}