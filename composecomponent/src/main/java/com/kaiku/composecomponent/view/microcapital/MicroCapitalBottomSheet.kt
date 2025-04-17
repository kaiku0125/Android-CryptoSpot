package com.kaiku.composecomponent.view.microcapital

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.color_e60039
import com.kaiku.composecomponent.component.bottomsheet.BottomSheetSceneConfig
import com.kaiku.composecomponent.component.bottomsheet.PocketBottomSheet
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.click.SurfaceWithClickableEffect
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.orNA
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.view.microcapital.data.MicroCapitalViewAction
import com.kaiku.composecomponent.view.microcapital.data.MicroCapitalViewState
import com.kaiku.composecomponent.view.microcapital.data.MicroCapitalViewState.UserStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MicroCapitalBottomSheet(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier.pocketPadding(hv = 10 to null),
    isVisible: Boolean = false,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    viewState: MicroCapitalViewState,
    config: BottomSheetSceneConfig = BottomSheetSceneConfig(
        titleConfig = PocketTextConfig(
            value = viewState.stringRes.title,
            style = text15Sp(600)
        ),
        shape = RoundedCornerShape(topStart = 15.sdp(), topEnd = 15.sdp()),
        background = color_333333,
        screenRatio = null,
        fixed = false,
        contentAnimationSpec = tween(800)
    ),
    action: (MicroCapitalViewAction) -> Unit,
    onDismiss: () -> Unit
) {

    var isInfoDialogVisible by remember { mutableStateOf(false) }

    if (isInfoDialogVisible) {
        MicroCapitalInfoDialog(
            viewState = viewState,
            onPositiveClick = {
                isInfoDialogVisible = false
            }
        )
    }

    PocketBottomSheet(
        modifier = modifier,
        columnModifier = columnModifier,
        titleModifier = titleModifier,
        isVisible = isVisible,
        sheetState = sheetState,
        config = config,
        onDismiss = onDismiss,
        titleRowContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .pocketPadding(hv = 8 to null)
                        .clipByShape(
                            shape = RoundedCornerShape(14.sdp()),
                            backgroundColor = viewState.getStatusRes().backgroundColor,
                        )
                        .pocketPadding(all = 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = viewState.getStatusRes().resId),
                        contentDescription = ""
                    )
                    PocketSpacer(width = 5)
                    PocketText(
                        modifier = Modifier,
                        config = PocketTextConfig(value = viewState.getStatusRes().description)
                    )
                    PocketSpacer(width = 5)
                }
                PocketIconButton(
                    modifier = Modifier.size(40.sdp()),
                    iconModifier = Modifier.size(22.sdp()),
                    drawableRes = R.drawable.preview_ic_information,
                    tint = Color.White,
                    iconSize = 22.sdp(),
                    onIconClick = {
                        isInfoDialogVisible = true
                    }
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .pocketPadding(hv = 16 to 8)
            ) {
                RowGroup(
                    data = listOf(
                        RowGroupItem(
                            title = viewState.stringRes.titleUserStatus,
                            value = if (viewState.isReverseAction) {
                                viewState.getStatusRes().reverseDescription
                            } else {
                                viewState.getStatusRes().description
                            },
                            minHeight = 32.sdp(),
                            endContent = {
                                if (viewState.isCancelBtnVisible()) {
                                    PocketSpacer(width = 8)
                                    PocketPrimaryButton(
                                        height = 32.sdp(),
                                        config = PocketTextConfig(
                                            value = viewState.getStatusRes().reverseBtnText,
                                            style = text13Sp(500)
                                        ),
                                        border = BorderStroke(0.dp, color_e60039),
                                        primaryColor = color_e60039,
                                        contentPadding = PaddingValues(all = 8.sdp()),
                                        onClick = {
                                            action.invoke(MicroCapitalViewAction.CancelAction)
                                        }
                                    )
                                }
                            }
                        ),
                    )
                )

                HorizontalDivider(
                    modifier = Modifier.pocketPadding(hv = null to 8),
                    thickness = 1.sdp(),
                    color = color_717071
                )

                RowGroup(
                    data = listOf(
                        RowGroupItem(
                            title = viewState.stringRes.titleRegistrationDate,
                            value = viewState.registrationDate,
                            minHeight = 24.sdp()
                        ),
                        RowGroupItem(
                            title = viewState.stringRes.titleRegistrationDate,
                            value = viewState.effectiveDate,
                            minHeight = 24.sdp()
                        )
                    )
                )

                if (viewState.userStatus == UserStatus.PARTICIPATE) {
                    HorizontalDivider(
                        modifier = Modifier.pocketPadding(hv = null to 8),
                        thickness = 1.sdp(),
                        color = color_717071
                    )

                    RowGroup(
                        data = listOf(
                            RowGroupItem(
                                title = viewState.stringRes.titleTwTradeAmount,
                                hint = viewState.stringRes.titleHint,
                                value = viewState.twPlatformInfo.tradeAmount,
                                minHeight = 24.sdp()
                            ),
                            RowGroupItem(
                                title = viewState.stringRes.titleTwTradeCount,
                                hint = viewState.stringRes.titleHint,
                                value = viewState.twPlatformInfo.tradeCount,
                                minHeight = 24.sdp()
                            ),
                            RowGroupItem(
                                title = viewState.stringRes.titleTwTradeFee,
                                hint = viewState.stringRes.titleHint,
                                value = viewState.twPlatformInfo.tradeFee,
                                minHeight = 24.sdp()
                            )
                        )
                    )
                    HorizontalDivider(
                        modifier = Modifier.pocketPadding(hv = null to 8),
                        thickness = 1.sdp(),
                        color = color_717071
                    )
                    RowGroup(
                        data = listOf(
                            RowGroupItem(
                                title = viewState.stringRes.titleUsTradeAmount,
                                hint = viewState.stringRes.titleHint,
                                value = viewState.usPlatformInfo.tradeAmount,
                                minHeight = 24.sdp()
                            ),
                            RowGroupItem(
                                title = viewState.stringRes.titleUsTradeCount,
                                hint = viewState.stringRes.titleHint,
                                value = viewState.usPlatformInfo.tradeCount,
                                minHeight = 24.sdp()
                            ),
                            RowGroupItem(
                                title = viewState.stringRes.titleUsTradeFee,
                                hint = viewState.stringRes.titleHint,
                                value = viewState.usPlatformInfo.tradeFee,
                                minHeight = 24.sdp()
                            )
                        )
                    )
                }
            }

            if (viewState.showMoreInfo()) {
                HorizontalDivider(
                    modifier = Modifier.pocketPadding(hv = 16 to null),
                    thickness = 1.sdp(),
                    color = color_717071
                )
                PocketSpacer(height = 8)
                SurfaceWithClickableEffect(
                    modifier = Modifier
                        .pocketPadding(start = 16)
                        .align(Alignment.Start),
                    color = Color.Transparent,
                    clickableConfig = rememberClickableConfig(
                        needSound = true,
                        needRipple = false,
                        clickEffect = false
                    ),
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PocketText(
                                config = PocketTextConfig(
                                    value = viewState.stringRes.moreInfo,
                                    style = text15Sp(500),
                                    textColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            PocketSpacer(width = 4)
                            Image(
                                painter = painterResource(R.drawable.lib_pocket_ic_show_more),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                contentDescription = ""
                            )
                        }
                    },
                    onClick = {
                        action.invoke(MicroCapitalViewAction.ShowMoreAction)
                    }
                )
            }

            if (viewState.isReRegisterBtnVisible()) {
                PocketPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pocketPadding(hv = 16 to null),
                    height = 35.sdp(),
                    config = PocketTextConfig(
                        value = viewState.stringRes.reRegister,
                        style = text13Sp(500)
                    ),
                    onClick = {
                        action.invoke(MicroCapitalViewAction.ReRegisterAction)
                    }
                )
            }
            PocketSpacer(height = 20)
        }
    )
}

@Composable
private fun RowItem(
    modifier: Modifier = Modifier,
    figmaHeight: Dp = 24.sdp(),
    title: String,
    hint: String? = null,
    value: String? = null,
    endContent: @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = figmaHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            PocketText(
                config = PocketTextConfig(
                    value = title,
                    style = text15Sp(500)
                )
            )
            hint?.let {
                PocketText(
                    modifier = Modifier.widthIn(min = 52.sdp()),
                    config = PocketTextConfig(
                        value = it,
                        style = text13Sp(),
                        textColor = color_9e9e9f
                    )
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            PocketText(
                config = PocketTextConfig(
                    value = value.orNA(),
                    style = text15Sp(500),
                    textColor = color_9e9e9f
                )
            )
            endContent?.invoke(this)
        }
    }
}


data class RowGroupItem(
    val title: String,
    val hint: String? = null,
    val value: String? = null,
    val minHeight: Dp = 24.dp,
    val endContent: (@Composable (RowScope.() -> Unit))? = null
)

@Composable
private fun ColumnScope.RowGroup(data: List<RowGroupItem>) {
    data.forEachIndexed { index, item ->
        RowItem(
            title = item.title,
            hint = item.hint,
            value = item.value,
            figmaHeight = item.minHeight,
            endContent = item.endContent
        )
        if (index != data.lastIndex) {
            PocketSpacer(height = 8)
        }
    }
}