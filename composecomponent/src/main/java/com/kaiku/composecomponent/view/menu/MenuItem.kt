package com.kaiku.composecomponent.view.menu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.color_ca1039
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.text.PocketAutoSizeText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.TextWithIcon
import com.kaiku.composecomponent.component.text.TextWithIconConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.rememberIconClickableConfig
import com.kaiku.composecomponent.pocket_color_ffffff
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text11Sp
import com.kaiku.composecomponent.view.menu.menuallfunction.data.BADGE_TYPE_INVISIBLE
import com.kaiku.composecomponent.view.menu.menuallfunction.data.BADGE_TYPE_NEW
import com.kaiku.composecomponent.view.menu.menuallfunction.data.BADGE_TYPE_UNRECEIVED
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuIconType


// 不需要dash的BaseMenuItem
@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    titleConfig: PocketTextConfig = PocketTextConfig(),
    @DrawableRes drawableRes: Int,
    iconUrl: String? = null,
    menuIconType: MenuIconType = MenuIconType(badgeType = BADGE_TYPE_INVISIBLE),
    onClick: (() -> Unit)? = null
) {
    BaseMenuItem(
        modifier = modifier,
        titleConfig = titleConfig,
        menuRes = drawableRes,
        iconUrl = iconUrl,
        onClick = onClick,
        menuIconType = menuIconType,
        withDash = false
    )
}

@Composable
fun BaseMenuItem(
    modifier: Modifier = Modifier,
    titleConfig: PocketTextConfig = PocketTextConfig(),
    @DrawableRes menuRes: Int,
    iconUrl: String? = null,
    menuIconType: MenuIconType = MenuIconType(badgeType = BADGE_TYPE_INVISIBLE),
    withDash: Boolean = true,
    @DrawableRes deleteRes: Int? = null,
    onClick: (() -> Unit)? = null
) {
    val countMarginBottom = 18.sdp()
    val countMarginRight = 6.sdp()

    BoxWithDash(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.sdp()))
            .clickableEffectConfig { onClick?.invoke() },
        withDash = withDash,
        content = {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (menuIcon, delete, count) = createRefs()

                TextWithIcon(
                    modifier = Modifier.constrainAs(menuIcon) {
                        centerTo(parent)
                    },
                    topConfig = TextWithIconConfig(
                        drawableRes = menuRes,
                        iconUrl = iconUrl,
                        drawableSize = 30.sdp(),
                        paddingToText = 3.sdp(),
                        clickableConfig = rememberIconClickableConfig(iconSize = 33.sdp()),
                        onClick = onClick
                    ),
                    content = {
                        PocketAutoSizeText(
                            Modifier.height(countMarginBottom),
                            config = titleConfig
                        )
                    }
                )

                val margin = 5.sdp()
                deleteRes?.let { mDeleteRes ->
                    PocketIconButton(
                        modifier = Modifier.constrainAs(delete) {
                            end.linkTo(parent.end, margin = margin)
                            top.linkTo(parent.top, margin = margin)
                        },
                        drawableRes = mDeleteRes,
                        iconSize = 14.sdp(),
                        onIconClick = {
                            onClick?.invoke()
                        }
                    )
                }

                if (menuIconType.badgeType == BADGE_TYPE_UNRECEIVED) {
                    CreateBadgeImage(
                        id = R.drawable.pocket_home_menu_badge_2,
                        ref = delete,
                        modifier = Modifier
                    )
                } else if (menuIconType.current != null && menuIconType.current > 0) {
                    Box(
                        modifier = Modifier
                            .constrainAs(count) {
                                centerHorizontallyTo(parent, 0.75f)
                                bottom.linkTo(menuIcon.bottom, margin = countMarginBottom)
                            }
                            .background(shape = RoundedCornerShape(100.sdp()), color = color_ca1039)
                            .padding(horizontal = 4.sdp()),
                        contentAlignment = Alignment.Center
                    ) {
                        val display =
                            if (menuIconType.max < menuIconType.current) "${menuIconType.max}+" else menuIconType.current
                        Text(
                            text = display.toString(),
                            color = pocket_color_ffffff,
                            style = text11Sp()
                        )
                    }
                } else if (menuIconType.badgeType == BADGE_TYPE_NEW) {
                    CreateBadgeImage(
                        id = R.drawable.pocket_home_menu_badge_1,
                        ref = delete,
                        modifier = Modifier
                    )
                }


            }
        }
    )
}

@Composable
private fun ConstraintLayoutScope.CreateBadgeImage(
    @DrawableRes id: Int,
    ref: ConstrainedLayoutReference,
    modifier: Modifier
) {
    val margin = 5.sdp()
    Image(
        painter = painterResource(id = id),
        modifier = modifier
            .width(32.sdp())
            .height(12.sdp())
            .constrainAs(ref) {
                end.linkTo(parent.end, margin = margin)
                top.linkTo(parent.top, margin = margin)
            },
        contentDescription = ""
    )
}

@Composable
fun MenuItemBlank(
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    BoxWithDash(
        modifier = modifier,
        content = {
            Icon(
                modifier = Modifier.size(24.sdp()),
                imageVector = Icons.Default.Add,
                tint = if (isActive) {
                    Color.White
                } else {
                    color_717071
                },
                contentDescription = ""
            )
        }
    )
}


@Composable
fun BoxWithDash(
    modifier: Modifier = Modifier,
    withDash: Boolean = true,
    content: @Composable () -> Unit = {
        PocketText(
            config = PocketTextConfig(
                value = "BTC",
                alignment = Alignment.Center,
                textColor = color_717071,
                maxLines = 1
            )
        )
    }
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(1f)
            .drawBehind {
                if (withDash) {
                    drawRoundRect(
                        color = Color.White,
                        style = stroke,
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        content.invoke()
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun BoxWithDashPreview() {
    BoxWithDash(
        modifier = Modifier.size(70.sdp())
    )
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun MenuItemDashPreview() {
    BaseMenuItem(
        modifier = Modifier.size(70.sdp()),
        menuRes = R.drawable.preview_ic_konbawa,
        titleConfig = PocketTextConfig(
            value = "空吧哇空吧哇"
        ),
        menuIconType = MenuIconType(
            badgeType = BADGE_TYPE_NEW,
            current = 99
        )
    )
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun MenuItemBlankPreview() {
    MenuItemBlank(
        modifier = Modifier.size(70.sdp()),
        isActive = true
    )
}