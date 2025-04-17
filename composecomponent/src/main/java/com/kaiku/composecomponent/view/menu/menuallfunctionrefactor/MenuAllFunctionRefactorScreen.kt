package com.kaiku.composecomponent.view.menu.menuallfunctionrefactor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.pager.PocketVerticalMagnetPager
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.component.topbar.BaseTopBarComponent
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.basic.getScreenHeight
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.menu.MenuItem
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuAllFunctionStringRes
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuAllFunctionViewState
import com.kaiku.composecomponent.view.menu.menuallfunction.data.ScreenAction
import com.kaiku.composecomponent.view.menu.menuallfunction.data.ViewModelAction
import com.kaiku.composecomponent.view.menu.menuallfunction.ui.MenuAllFunctionPager
import com.kaiku.composecomponent.view.menu.menuallfunction.ui.MenuAllFunctionReorderGrid

@Composable
fun BaseMenuAllFunctionRefactorScreen(
    modifier: Modifier = Modifier,
    action: (ScreenAction) -> Unit,
    viewModelAction: (ViewModelAction) -> Unit,
    viewState: MenuAllFunctionViewState,
    stringRes: MenuAllFunctionStringRes
) {
    Scaffold(
        topBar = {
            MenuTopBarContent(
                action = action,
                viewModelAction = viewModelAction,
                viewState = viewState,
                stringRes = stringRes
            )
        }
    ) { scaffoldPadding ->
        Surface(modifier = modifier.padding(scaffoldPadding)) {
            Column {
                AnimatedVisibility(
                    visible = viewState.isLocked.not(),
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                    label = ""
                ) {
                    MenuAllFunctionReorderGrid(viewState, viewModelAction)
                }

                Crossfade(targetState = viewState.isLocked, label = "") { isLocked ->
                    if (isLocked) {
                        MenuPagerContent(
                            viewModelAction = viewModelAction,
                            viewState = viewState,
                        )
                    } else {
                        var isExpand by rememberSaveable { mutableStateOf(false) }
                        Column {
                            MenuAllFunctionPager(
                                viewState = viewState,
                                isExpand = isExpand,
                                showAllTitleIcon = { isExpand = !isExpand },
                                isColumn = true,
                                viewModelAction = viewModelAction,
                                sortIconClick = {

                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun MenuTopBarContent(
    modifier: Modifier = Modifier,
    action: (ScreenAction) -> Unit,
    viewModelAction: (ViewModelAction) -> Unit,
    viewState: MenuAllFunctionViewState,
    stringRes: MenuAllFunctionStringRes
) {
    BaseTopBarComponent(
        modifier = modifier.height(44.sdp()),
        textConfig = PocketTextConfig(
            value = if (viewState.isLocked)
                stringRes.menuAllFunctionTitle
            else
                stringRes.menuAllFunctionModify,
            style = text17Sp(600),
            textColor = Color.White
        ),
        navContent = {
            Crossfade(
                targetState = viewState.isLocked,
                label = ""
            ) { isLocked ->
                if (isLocked) {
                    IconButton(
                        onClick = { action.invoke(ScreenAction.Back) }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pocket_ic_back),
                            contentDescription = null,
                            modifier = Modifier.size(44.sdp()),
                        )
                    }
                } else {
                    Row {
                        Spacer(modifier = Modifier.size(16.sdp()))
                        PocketTextWithClickEffect(
                            modifier = Modifier.fillMaxHeight(),
                            config = PocketTextConfig(
                                value = "取消",
                                textColor = Color.White
                            ),
                            clickableConfig = ClickableConfig(
                                needRipple = false
                            ),
                            onClick = {
                                viewModelAction.invoke(
                                    ViewModelAction.ManageMode(isSave = false)
                                )
                            }
                        )
                    }
                }
            }
        },
        actionContent = {
            PocketTextWithClickEffect(
                modifier = Modifier.fillMaxHeight(),
                config = PocketTextConfig(
                    value = if (viewState.isLocked)
                        stringRes.menuAllFunctionModify
                    else
                        stringRes.menuAllFunctionSave,
                    textColor = if (viewState.canBeSaved.not() && viewState.isLocked.not())
                        color_9e9e9f
                    else
                        MaterialTheme.colorScheme.primary
                ),
                clickableConfig = ClickableConfig(
                    needRipple = false
                ),
                onClick = {
                    if (viewState.canBeSaved.not() && viewState.isLocked.not()) {
                        return@PocketTextWithClickEffect
                    } else {
                        viewModelAction.invoke(
                            ViewModelAction.ManageMode(isSave = !viewState.isLocked)
                        )
                    }
                }
            )
            PocketSpacer(width = 16)
        },
        background = color_333333
    )
}

@Composable
fun MenuPagerContent(
    modifier: Modifier = Modifier,
    viewModelAction: (ViewModelAction) -> Unit,
    viewState: MenuAllFunctionViewState,
) {
    val screenHeight = getScreenHeight() + 20.sdp()
    var lastHeight by remember { mutableStateOf(screenHeight) }
    val completeItems = viewState.homeMenuGroupCompleteItemList

    PocketVerticalMagnetPager(
        modifier = modifier,
        state = rememberLazyListState(),
        tabTitles = completeItems.map { it.name },
        items = completeItems,
        keySelector = { _, item -> item.id },
        itemContent = { groupIndex, itemStructure ->
            Column(
                modifier = Modifier
                    .fillMaxSizeIfLast(
                        isLast = groupIndex == completeItems.lastIndex,
                        minHeight = lastHeight
                    )
                    .pocketPadding(hv = null to 4)
            ) {
                PocketText(
                    modifier = Modifier
                        .pocketPadding(start = 16, top = 8, bottom = 4)
                        .height(21.sdp()),
                    config = PocketTextConfig(
                        value = itemStructure.name,
                        style = text15Sp(600)
                    )
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.sdp()),
                    thickness = 1.sdp(),
                    color = color_333333
                )
                if (itemStructure.items.isEmpty()) {
                    PocketSpacer(height = 20)
                } else {
                    itemStructure.items.chunked(5).forEach { mStructures ->
                        Row {
                            mStructures.forEach { structure ->
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {

                                    MenuItem(
                                        titleConfig = PocketTextConfig(
                                            value = structure.title,
                                            style = text13Sp()
                                        ),
                                        menuIconType = structure.menuIconType,
                                        drawableRes = structure.iconResId,
                                        iconUrl = structure.iconUrl,
                                        onClick = {
                                            viewModelAction(ViewModelAction.ClickHomeMenuItem(structure.key))
                                        }
                                    )
                                }

                            }
                            repeat(5 - mStructures.size) {
                                Box(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        },
        onMeasurePagerHeight = { lazyColumnHeight ->
            lastHeight = lazyColumnHeight
        }
    )
}

private fun Modifier.fillMaxSizeIfLast(
    isLast: Boolean,
    minHeight: Dp
) = composed(
    factory = {
        if (isLast) {
            this.then(
                Modifier.requiredHeightIn(min = minHeight)
            )
        } else {
            this
        }
    }
)
