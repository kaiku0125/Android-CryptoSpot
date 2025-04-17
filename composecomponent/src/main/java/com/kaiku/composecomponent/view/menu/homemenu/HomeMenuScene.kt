package com.kaiku.composecomponent.view.menu.homemenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.component.button.PocketDetailButton
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.view.menu.MenuItem
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuGroupStructure
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuIconType

@Composable
fun HomeMenuScene(
    modifier: Modifier = Modifier,
    menus: List<MenuGroupStructure>,
    countMap: HashMap<String, Int>? = null,
    onMenuClick: (MenuGroupStructure) -> Unit,
    onMenuAllClick: () -> Unit
) {
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = menus.isNotEmpty()
    ) {
        Column(modifier = modifier) {
            PocketSpacer(height = 8)
            MenuActionContent(
                modifier = Modifier.pocketPadding(hv = 12 to null),
                onMenuAllClick = onMenuAllClick
            )
            PocketSpacer(height = 8)

            val rows = menus.chunked(5)
            for (rowItems in rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (item in rowItems) {
                        key(item.key) {
                            MenuItem(
                                modifier = Modifier.weight(1f),
                                titleConfig = PocketTextConfig(
                                    value = item.title,
                                    style = text13Sp()
                                ),
                                menuIconType = MenuIconType(
                                    badgeType = item.menuIconType.badgeType,
                                    current = countMap?.get(item.key)
                                ),
                                drawableRes = item.iconResId,
                                iconUrl = item.iconUrl,
                                onClick = {
                                    onMenuClick.invoke(item)
                                }
                            )
                        }
                    }
                    if (rowItems.size <= 5) {
                        for (i in 0 until 5 - rowItems.size) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun MenuActionContent(
    modifier: Modifier = Modifier,
    onMenuAllClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PocketText(
            config = PocketTextConfig(
                value = "常用功能",
                style = text14Sp(500)
            )
        )

        PocketDetailButton(
            modifier = Modifier.heightIn(min = 22.sdp()),
            textConfig = PocketTextConfig(
                value = "所有功能",
                style = text13Sp(),
                textColor = Color.White
            ),
            onClick = onMenuAllClick
        )
    }
}