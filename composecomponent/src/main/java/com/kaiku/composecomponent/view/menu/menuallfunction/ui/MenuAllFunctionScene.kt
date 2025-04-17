package com.kaiku.composecomponent.view.menu.menuallfunction.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.component.topbar.BaseTopBarComponent
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.pocket_color_ffffff
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuAllFunctionStringRes
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuAllFunctionViewState
import com.kaiku.composecomponent.view.menu.menuallfunction.data.ScreenAction
import com.kaiku.composecomponent.view.menu.menuallfunction.data.ViewModelAction

@Composable
fun BaseMenuAllFunctionScene(
    action: (ScreenAction) -> Unit,
    viewModelAction: (ViewModelAction) -> Unit,
    viewState: MenuAllFunctionViewState,
    stringRes: MenuAllFunctionStringRes
) {
    Scaffold(
        topBar = {
            BaseTopBarComponent(
                modifier = Modifier.height(44.sdp()),
                textConfig = PocketTextConfig(
                    value = if (viewState.isLocked)
                        stringRes.menuAllFunctionTitle
                    else
                        stringRes.menuAllFunctionModify,
                    style = text17Sp(600),
                    textColor = Color.White,
                    maxLines = 1
                ),
                navContent = {
                    if (viewState.isLocked) {
                        IconButton(
                            onClick = { action.invoke(ScreenAction.Back) }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pocket_ic_back),
                                contentDescription = null,
                                modifier = Modifier.size(44.dp),
                            )
                        }
                    } else {
                        Row {
                            Spacer(modifier = Modifier.size(16.dp))
                            PocketTextWithClickEffect(
                                modifier = Modifier.fillMaxHeight(),
                                config = PocketTextConfig(
                                    value = "取消",
                                    textColor = pocket_color_ffffff,
                                    maxLines = 1
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
                                MaterialTheme.colorScheme.primary,
                            maxLines = 1
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
                    Spacer(modifier = Modifier.size(16.dp))
                },
                background = color_333333
            )
        },
    ) { scaffoldPadding ->
        Surface(modifier = Modifier.padding(scaffoldPadding)) {
            if (viewState.homeMenuGroupCompleteItemList.isNotEmpty()) {
                MenuAllFunctionContent(
                    viewState = viewState,
                    viewModelAction = viewModelAction
                )
            }
        }
    }
}

@Composable
fun MenuAllFunctionContent(
    viewState: MenuAllFunctionViewState,
    viewModelAction: (ViewModelAction) -> Unit,
) {
    var isExpand by rememberSaveable {
        mutableStateOf(false)
    }

    var isColumn by rememberSaveable {
        mutableStateOf(true)
    }

    Column(modifier = Modifier) {
        Crossfade(targetState = viewState.isLocked, label = "") { isLocked ->
            if (isLocked.not()) {
                MenuAllFunctionReorderGrid(viewState, viewModelAction)
            }
        }

        MenuAllFunctionPager(
            viewState = viewState,
            isExpand = isExpand,
            showAllTitleIcon = {
                isExpand = !isExpand
            },
            isColumn = isColumn,
            sortIconClick = {
                isColumn = !isColumn
            },
            viewModelAction = viewModelAction,
        )
    }
}

@Composable
@Preview
fun PreviewMenuAllFunctionContent() {
    BaseMenuAllFunctionScene(
        action = {},
        viewModelAction = {},
        viewState = MenuAllFunctionViewState(
            homeMenuGridBaseItemList = emptyList()
        ),
        stringRes = MenuAllFunctionStringRes.DEFAULT
    )
}