package com.kaiku.composecomponent.component.popup

import androidx.annotation.ColorRes
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.color_1E1E1E
import com.kaiku.composecomponent.component.button.PocketRotationIconButton
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.ShadowConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.getColorAnimation
import com.kaiku.composecomponent.extension.hoverColor
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp
import kotlinx.coroutines.launch

/**
 * 口袋彈窗元件
 *
 * @param textConfig 選單所選文字設定
 * @param isPopup 選單是否展開
 * @param isEnable 是否可以下拉
 * @param shadowConfig 下拉選單陰影設定
 * @param itemPadding 下拉選單item padding 參數
 * @param popupBorder 下拉選單邊框
 * @param popupBackground 下拉選單背景顏色
 * @param aniSpec 選單內容改變動畫時間
 * @param content 下拉選單內容
 */
@Composable
fun PopupActivatorComponent(
    modifier: Modifier,
    textConfig: PocketTextConfig = PocketTextConfig(),
    isPopup: Boolean = false,
    isEnable: Boolean = true,
    shadowConfig: ShadowConfig = ShadowConfig(),
    itemPadding: PaddingValues = PaddingValues(start = 12.sdp(), end = 12.sdp()),
    activatorBorder: BorderStroke = BorderStroke(1.sdp(), Color.White),
    popupBorder: BorderStroke = BorderStroke(1.sdp(), Color.White),
    @ColorRes popupBackground: Color = color_1E1E1E,
    aniSpec: AnimationSpec<Color>? = null,
    content: @Composable ((() -> Unit)?) -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var spinnerWidth by remember { mutableStateOf(0.dp) }

    val popupState = remember(isPopup) { PopupState(isPopup) }

    val onSpinnerClick: () -> Unit = {
        popupState.isVisible = popupState.isVisible.not()
    }

    val calculate: (LayoutCoordinates) -> Unit = remember {
        {
            scope.launch {
                spinnerWidth = with(density) {
                    it.size.width.toDp()
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipByShape(
                shape = RoundedCornerShape(shadowConfig.corner),
                border = activatorBorder,
                backgroundColor = getColorAnimation(toColor = popupBackground)
            )
            .clickableEffectConfig { onSpinnerClick.invoke() }
            .onGloballyPositioned { calculate.invoke(it) },
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (text, icon) = createRefs()

            AnimatedPocketText(
                aniModifier = Modifier.constrainAs(text) {
                    start.linkTo(
                        anchor = parent.start,
                        margin = itemPadding.calculateLeftPadding(layoutDirection)
                    )
                    centerVerticallyTo(parent)
                },
                config = textConfig,
                colorKey = (textConfig.textColor to popupBackground),
                defaultColor = textConfig.textColor,
                aniSpec = aniSpec
            )

            if (isEnable) {
                PocketRotationIconButton(
                    modifier = Modifier.constrainAs(icon) {
                        end.linkTo(
                            anchor = parent.end,
                            margin = itemPadding.calculateEndPadding(layoutDirection)
                        )
                        centerVerticallyTo(parent)
                    },
                    tint = getColorAnimation(toColor = popupBackground.hoverColor()),
                    iconSize = 24.sdp(),
                    isExpand = popupState.isVisible,
                    clickableConfig = ClickableConfig(),
                    onIconClick = {
                        onSpinnerClick.invoke()
                    }
                )
            }

        }

        if (isEnable) {
            BasePopupComponent(
                modifier = Modifier
                    .width(spinnerWidth)
                    .background(popupBackground),
                popupState = popupState,
                shadowConfig = shadowConfig,
                border = popupBorder,
                onDismissRequest = {
                    popupState.isVisible = false
                },
                content = {
                    content.invoke {
                        onSpinnerClick.invoke()
                    }
                }
            )
        }

    }
}