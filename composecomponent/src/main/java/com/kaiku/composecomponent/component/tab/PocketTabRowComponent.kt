package com.kaiku.composecomponent.component.tab

import androidx.annotation.ColorRes
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.tab.data.TabType
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text17Sp

/**
 * 簡單 tab row元件
 *
 * @param indicatorPadding 移動元件padding
 * @param selectedItemIndex 當前index
 * @param items 『String』型態的列表
 * @param tabWidth 單一tab寬度 (null ➔ fillMaxWidth)
 * @param shape tabRow 形狀
 * @param textStyle 文字樣式
 * @param dividerThickness divider寬度
 * @param borderThickness tabRow 外框寬度
 * @param borderColor tabRow 外框顏色
 * @param indicatorColor 移動元件顏色
 * @param selectedTextColor 被選擇時文字顏色
 * @param unselectedTextColor 非選擇時文字顏色
 * @param componentBackground 元件背景顏色
 * @param dividerColor divider顏色
 * @param aniSpec 動畫設定
 * @param onClick export 點擊事件
 */
@Composable
fun PocketTabRowComponent(
    modifier: Modifier = Modifier,
    indicatorPadding: Dp = 2.sdp(),
    indicatorModifier: Modifier = Modifier
        .fillMaxHeight()
        .padding(indicatorPadding),
    dividerModifier: Modifier = Modifier,
    selectedItemIndex: Int,
    items: List<String>,
    isEnabled: List<Boolean> = items.map { true },
    tabWidth: Dp? = null,
    shape: Shape = RoundedCornerShape(8.sdp()),
    textStyle: TextStyle = text17Sp(),
    dividerThickness: Dp = 0.dp,
    borderThickness: Dp = 1.sdp(),
    borderColor: Color = Color.Transparent,
    @ColorRes indicatorColor: Color = Color.White,
    @ColorRes selectedTextColor: Color = Color.Black,
    @ColorRes unselectedTextColor: Color = Color.White,
    @ColorRes componentBackground: Color = color_414141,
    @ColorRes dividerColor: Color = Color.Transparent,
    @ColorRes disableTextColor: Color = color_9e9e9f,
    aniSpec: AnimationSpec<Dp> = tween(
        easing = LinearEasing,
        durationMillis = 200
    ),
    onClick: (index: Int) -> Unit
) {

    BaseTabRowComponent(
        modifier = modifier,
        dividerModifier = dividerModifier,
        items = items.mapIndexed { index, item ->
            item.toTabType(
                position = index,
                description = null,
                tag = item
            )
        },
        tabWidth = tabWidth,
        shape = shape,
        dividerThickness = dividerThickness,
        borderThickness = borderThickness,
        borderColor = borderColor,
        componentBackground = componentBackground,
        dividerColor = dividerColor,
        indicatorContent = { width ->
            val indicatorOffset: Dp by animateDpAsState(
                targetValue = (width + dividerThickness) * selectedItemIndex,
                animationSpec = aniSpec,
                label = "",
            )

            val indicatorWidth = width - indicatorPadding * 2
            if (indicatorWidth > 0.dp) {
                TabIndicator(
                    modifier = indicatorModifier,
                    indicatorWidth = indicatorWidth,
                    indicatorOffset = indicatorOffset,
                    indicatorColor = indicatorColor,
                    shape = shape
                )
            }
        },
        tabItemContent = { width, index, type ->
            val isSelected = index == selectedItemIndex
            val text = type.description?.let {
                stringResource(id = it)
            } ?: type.tag

            TabItem(
                modifier = Modifier.fillMaxHeight(),
                config = TabItemConfig(
                    isEnabled = isEnabled[index],
                    isSelected = if (isEnabled[index]) isSelected else false,
                    shape = shape,
                    tabWidth = width,
                    selectedTextColor = selectedTextColor,
                    disableTextColor = disableTextColor,
                    textConfig = PocketTextConfig(
                        value = text,
                        style = if (isSelected) {
                            textStyle.copy(fontWeight = FontWeight(600))
                        } else {
                            textStyle
                        },
                        textColor = unselectedTextColor,
                        isEnable = isEnabled[index]
                    )
                ),
                onClick = {
                    onClick.invoke(index)
                }
            )
        }
    )

}

private class LocalTabType(
    override val position: Int,
    override val description: Int?,
    override val tag: String
) : TabType

private fun String.toTabType(
    position: Int,
    description: Int?,
    tag: String
): TabType {
    return LocalTabType(
        position = position,
        description = description,
        tag = tag
    )
}