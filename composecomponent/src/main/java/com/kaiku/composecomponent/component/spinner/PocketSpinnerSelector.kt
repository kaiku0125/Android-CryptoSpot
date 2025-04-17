package com.kaiku.composecomponent.component.spinner

import androidx.annotation.ColorRes
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_1E1E1E
import com.kaiku.composecomponent.color_shadow
import com.kaiku.composecomponent.component.popup.PopupActivatorComponent
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.extension.ShadowConfig
import com.kaiku.composecomponent.utils.sdp

/**
 * 口袋下拉選單元件
 *
 * @param titleConfig 選單標題文字設定
 * @param isPopup 選單是否展開 (狀態已經封裝好，無需複寫)
 * @param items 下拉選單內容
 * @param isEnable 是否可以下拉
 * @param shadowConfig 陰影設定
 * @param borderColor 選單標題與內容外框顏色
 * @param popupBackground 選單標題與內容背景顏色
 * @param onItemChange export 所選 index
 */
@Composable
fun PocketSpinnerSelector(
    modifier: Modifier,
    itemModifier: Modifier = Modifier.height(30.sdp()), // 預設高度 30dp
    itemTextModifier: Modifier = Modifier.padding(
        horizontal = 16.sdp(), // 預設水平 padding 16 dp
        vertical = 7.sdp() / 2 // 預設垂直 padding 3.5 dp
    ),
    spacerModifier: Modifier = Modifier.height(9.sdp() / 2), // 預設上下空白 padding 4.5 dp
    titleConfig: PocketTextConfig = PocketTextConfig(),
    items: List<PocketTextConfig>,
    isPopup: Boolean = false,
    isEnable: Boolean = true,
    shadowConfig: ShadowConfig = ShadowConfig(
        shadowColor = color_shadow,
        corner = 6.sdp()
    ),
    @ColorRes activatorBorderColor: Color = Color.White,
    @ColorRes borderColor: Color = Color.White,
    @ColorRes popupBackground: Color = color_1E1E1E,
    aniSpec: AnimationSpec<Color>? = null,
    onItemChange: (Int) -> Unit
) {
    PopupActivatorComponent(
        modifier = modifier,
        textConfig = titleConfig,
        isPopup = isPopup,
        isEnable = isEnable,
        shadowConfig = shadowConfig,
        itemPadding = PaddingValues(start = 12.sdp(), end = 12.sdp()),
        activatorBorder = BorderStroke(1.sdp(), activatorBorderColor),
        popupBorder = BorderStroke(1.sdp(), borderColor),
        popupBackground = popupBackground,
        aniSpec = aniSpec,
        content = { onItemClick ->
            items.forEachIndexed { index, config ->
                if (index == 0) {
                    Spacer(modifier = spacerModifier)
                }

                PocketTextWithClickEffect(
                    modifier = itemModifier.fillMaxWidth(),
                    textModifier = itemTextModifier,
                    config = config,
                    onClick = {
                        onItemClick?.invoke()
                        onItemChange.invoke(index)
                    }
                )
                if (index == items.lastIndex) {
                    Spacer(modifier = spacerModifier)
                }
            }
        }
    )

}