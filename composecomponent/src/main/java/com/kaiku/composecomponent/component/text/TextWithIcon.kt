package com.kaiku.composecomponent.component.text


import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.image.PocketAsyncImage
import com.kaiku.composecomponent.component.image.PocketAsyncImageConfig
import com.kaiku.composecomponent.model.ClickableConfig

/**
 * @property drawableRes Icon的Resource ID
 * @property imageVector imageVector
 * @property iconUrl Icon的URL
 * @property drawableSize icon 大小
 * @property drawableColor Icon的顏色
 * @property paddingToText Icon與文字的距離
 * @property clickableConfig Icon點擊效果
 * @property onClick Icon點擊事件
 */
data class TextWithIconConfig(
    @DrawableRes val drawableRes: Int? = null,
    val imageVector: ImageVector? = null,
    val iconUrl: String? = null,
    val drawableSize: Dp = 17.dp,
    val drawableColor: Color = Color.Unspecified,
    val paddingToText: Dp = 5.dp,
    val clickableConfig: ClickableConfig = ClickableConfig(needHaptic = true),
    val onClick: (() -> Unit)? = null
)

/**
 * Text 與 Icon 組合元件
 * @param topConfig 加入上方icon
 * @param startConfig 加入起始icon
 * @param bottomConfig 加入底部icon
 * @param endConfig 加入尾部icon
 * @param content 放入中間的text
 */

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    topConfig: TextWithIconConfig? = null,
    startConfig: TextWithIconConfig? = null,
    bottomConfig: TextWithIconConfig? = null,
    endConfig: TextWithIconConfig? = null,
    content: @Composable () -> Unit,
) {

    ConstraintLayout(
        modifier = modifier
    ) {
        val (refImgStart, refImgTop, refImgBottom, refImgEnd, refContent) = createRefs()
        Box(
            modifier = Modifier.constrainAs(refContent) {
                top.linkTo(topConfig?.drawableRes?.let { refImgTop.bottom } ?: parent.top)
                bottom.linkTo(bottomConfig?.drawableRes?.let { refImgBottom.top } ?: parent.bottom)
                start.linkTo(startConfig?.drawableRes?.let { refImgStart.end } ?: parent.start)
                end.linkTo(endConfig?.drawableRes?.let { refImgEnd.start } ?: parent.end)
            },
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        topConfig?.let {
            IconContent(
                modifier = Modifier
                    .constrainAs(refImgTop) {
                        top.linkTo(parent.top)
                        start.linkTo(refContent.start)
                        end.linkTo(refContent.end)
                    }
                    .padding(bottom = it.paddingToText),
                vs = it
            )
        }

        startConfig?.let {
            IconContent(
                modifier = Modifier
                    .constrainAs(refImgStart) {
                        top.linkTo(refContent.top)
                        bottom.linkTo(refContent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(end = it.paddingToText),
                vs = it,
            )
        }

        bottomConfig?.let {
            IconContent(
                modifier = Modifier
                    .constrainAs(refImgBottom) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(refContent.start)
                        end.linkTo(refContent.end)
                    }
                    .padding(top = it.paddingToText),
                vs = it,
            )
        }

        endConfig?.let {
            IconContent(
                modifier = Modifier
                    .constrainAs(refImgEnd) {
                        top.linkTo(refContent.top)
                        bottom.linkTo(refContent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(start = it.paddingToText),
                vs = it,
            )
        }
    }
}

@Composable
private fun IconContent(
    modifier: Modifier,
    vs: TextWithIconConfig
) {
    if (vs.onClick != null) {
        if (vs.iconUrl?.isNotBlank() == true) {
            PocketAsyncImage(
                modifier = modifier.size(vs.drawableSize),
                imageConfig = PocketAsyncImageConfig(
                    url = vs.iconUrl,
                    description = "",
                    errorDrawable = vs.drawableRes
                ),
                onClick = vs.onClick
            )
        } else {
            PocketIconButton(
                modifier = modifier,
                iconModifier = Modifier.size(vs.drawableSize),
                drawableRes = vs.drawableRes,
                imageVector = vs.imageVector,
                tint = vs.drawableColor,
                iconSize = vs.drawableSize,
                clickableConfig = vs.clickableConfig,
                onIconClick = {
                    vs.onClick.invoke()
                }
            )
        }
    } else {
        vs.drawableRes?.let {
            Icon(
                modifier = modifier.size(vs.drawableSize),
                painter = painterResource(id = it),
                contentDescription = null,
                tint = vs.drawableColor
            )
        } ?: vs.imageVector?.let { vector ->
            Icon(
                modifier = modifier.size(vs.drawableSize),
                imageVector = vector,
                contentDescription = null,
                tint = vs.drawableColor
            )
        }
    }
}