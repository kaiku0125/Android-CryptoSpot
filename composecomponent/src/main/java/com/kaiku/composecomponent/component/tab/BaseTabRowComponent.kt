package com.kaiku.composecomponent.component.tab

import androidx.annotation.ColorRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.kaiku.composecomponent.color_1E1E1E
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_484848
import com.kaiku.composecomponent.component.tab.data.TabType
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.ratioHeight
import com.kaiku.composecomponent.utils.sdp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 基本tabRow
 *
 * @param items 使用 TabType 物件類型的 list (請實作TabType)
 * @param tabWidth 單一tab寬度 (null ➔ fillMaxWidth)
 * @param shape tabRow 形狀
 * @param dividerThickness divider寬度
 * @param borderThickness tabRow 外框寬度
 * @param borderColor tabRow 外框顏色
 * @param componentBackground 元件背景顏色
 * @param dividerColor divider顏色
 * @param indicatorContent 移動元件UI
 */
@Composable
fun BaseTabRowComponent(
    modifier: Modifier = Modifier,
    dividerModifier: Modifier = Modifier,
    items: List<TabType>,
    tabWidth: Dp? = null,
    shape: Shape = RoundedCornerShape(8.sdp()),
    dividerThickness: Dp = 1.sdp(),
    borderThickness: Dp = 1.sdp(),
    borderColor: Color = color_414141,
    @ColorRes componentBackground: Color = color_1E1E1E,
    @ColorRes dividerColor: Color = color_484848,
    indicatorContent: @Composable (Dp) -> Unit,
    tabItemContent: @Composable (Dp, Int, TabType) -> Unit
) {

    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // 整個UI的寬度
    var wholeWidth by remember { mutableStateOf(20.dp) }

    // 以UI整個寬度變化當作key，並計算每個tab的寬度
    val localTabWith = remember(wholeWidth) {
        tabWidth ?: run {
            if (items.isEmpty()) {
                10.dp
            } else {
                // 可用空間 = (整個寬度 - (tab數量 - 1) * divider寬度) / tab數量
                (wholeWidth - (items.size - 1) * dividerThickness) / items.size
            }
        }
    }

    val calculate = remember<(LayoutCoordinates) -> Unit> {
        {
            scope.launch(Dispatchers.Default) {
                wholeWidth = with(density) {
                    it.size.width.toDp()
                }
            }
        }
    }

    Surface(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .onGloballyPositioned { calculate.invoke(it) },
        shape = shape
    ) {
        Box(
            modifier = Modifier.clipByShape(
                shape = shape,
                backgroundColor = componentBackground,
                border = BorderStroke(
                    width = borderThickness,
                    color = borderColor
                )
            )
        ) {
            indicatorContent.invoke(localTabWith)

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, type ->
                    Row {
                        if (index != 0) {
                            VerticalDivider(
                                modifier = dividerModifier,
                                thickness = dividerThickness,
                                color = dividerColor
                            )
                        }
                        tabItemContent.invoke(
                            localTabWith,
                            index,
                            type
                        )
                    }
                }
            }

        }
    }
}