package com.kaiku.composecomponent.component.expander

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.component.button.PocketRotationIconButton
import com.kaiku.composecomponent.extension.clickableEffectConfig

/**
 * PocketExpander 點擊可以展開的元件
 * @param isExpand 展開收合狀態
 * @param iconTint 根據是否輸入顏色來當作是否需要顯示icon
 * @param titleContent 標題UI
 * @param expandableContent 展開內容UI
 * @param onExpandClick export展開事件
 */
@Composable
fun PocketExpander(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentModifier: Modifier = Modifier,
    isExpand: Boolean,
    iconTint: Color? = null,
    titleContent: @Composable () -> Unit,
    expandableContent: @Composable () -> Unit,
    onExpandClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Row(
            modifier = contentModifier.clickableEffectConfig(
                onClick = onExpandClick
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            titleContent.invoke()
            iconTint?.let { tint ->
                PocketRotationIconButton(
                    isExpand = isExpand,
                    tint = tint,
                    onIconClick = onExpandClick
                )
            }
        }

        AnimatedVisibility(visible = isExpand) {
            expandableContent.invoke()
        }
    }
}