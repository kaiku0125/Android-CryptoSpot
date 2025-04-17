package com.kaiku.composecomponent.component.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.hoverColor
import com.kaiku.composecomponent.extension.screenHeight
import com.kaiku.composecomponent.utils.sdp

/**
 *  @property titleConfig 標題的設定
 *  @property shape bottom sheet的圓角設定
 *  @property background 背景
 *  @property screenRatio bottom sheet佔整個螢幕的比例 (null ➔ 自適應高度)
 *  @property isMin 輸入的螢幕比例為最大高度或是最小高度
 *  @property fixed 是否固定高度(與 screenRatio 搭配使用)
 *  @property contentAnimationSpec content的透明度動畫
 */
data class BottomSheetSceneConfig(
    val titleConfig: PocketTextConfig = PocketTextConfig(),
    val shape: RoundedCornerShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
    val background: Color = color_333333,
    val screenRatio: Float? = null,
    val isMin: Boolean = false,
    val fixed: Boolean = true,
    val contentAnimationSpec: AnimationSpec<Float>? = null
)


/**
 *  帶有標題與關閉元件的bottom sheet
 *  (主要是給 base on xml 的 bottom sheet 使用, compose 的話請使用 ➔ PocketBottomSheet)
 *
 *  @param config bottom sheet的設定檔
 *  @param titleRowContent title中間內容
 *  @param content compose內容
 *  @param onDismiss export dismiss 事件
 */
@Composable
fun BaseBottomSheetScene(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier.padding(horizontal = 20.sdp()),
    config: BottomSheetSceneConfig = BottomSheetSceneConfig(),
    titleRowContent: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = config.shape,
        color = config.background,
        shadowElevation = 16.sdp()
    ) {
        Column(
            modifier = columnModifier
                .fillMaxWidth()
                .screenHeight(
                    ratio = config.screenRatio,
                    isMin = config.isMin,
                    fixed = config.fixed
                )
                .background(config.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = titleModifier
                    .fillMaxWidth()
                    .height(44.sdp()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PocketText(config = config.titleConfig)
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    titleRowContent?.invoke(this@Box)
                }
                PocketIconButton(
                    modifier = Modifier.size(40.sdp()),
                    iconModifier = Modifier.size(24.sdp()),
                    imageVector = Icons.Default.Close,
                    tint = config.background.hoverColor(),
                    iconSize = 24.sdp(),
                    onIconClick = {
                        onDismiss.invoke()
                    }
                )
            }

            content.invoke(this)
        }

    }
}