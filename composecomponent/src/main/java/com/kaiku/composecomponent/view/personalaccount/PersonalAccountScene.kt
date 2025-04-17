package com.kaiku.composecomponent.view.personalaccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.color_c8c9ca
import com.kaiku.composecomponent.color_fdd11a
import com.kaiku.composecomponent.component.button.PocketDetailButton
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.loading.CircularPercentageComponent
import com.kaiku.composecomponent.component.loading.CircularPercentageConfig
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.AnimatedPocketAutoSizeText
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketAutoSizeText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.TextWithIcon
import com.kaiku.composecomponent.component.text.TextWithIconConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberIconClickableConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewAction
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewState
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewState.Companion.TEXT_INVISIBLE
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewState.Companion.TEXT_INVISIBLE_LONG
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewState.Companion.TEXT_NONE

/**
 * 口袋台美股首頁帳戶卡片
 */
@Composable
fun BasePersonalAccountCard(
    modifier: Modifier = Modifier,
    cardColors: CardColors = CardDefaults.cardColors(containerColor = color_252525),
    viewState: PersonalAccountViewState,
    accountSelectScene: @Composable (RowScope.() -> Unit)? = null,
    eventScene: @Composable (ColumnScope.() -> Unit)? = null,
    action: (PersonalAccountViewAction) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PersonalAccountTitle(
            viewState = viewState,
            action = action
        )

        PocketSpacer(height = 8)

        Card(
            shape = RoundedCornerShape(8.sdp()),
            colors = cardColors
        ) {
            Column(
                modifier = Modifier.pocketPadding(
                    start = 12,
                    end = 12,
                    top = 6,
                    bottom = 8
                )
            ) {
                CardHeader(
                    viewState = viewState,
                    accountSelectScene = accountSelectScene,
                    action = action,
                )
                PocketSpacer(height = 8)
                CardContent(viewState = viewState)
                PocketSpacer(height = 8)
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.sdp()),
                    thickness = 1.sdp(),
                    color = color_414141
                )
                CardTrailing(
                    viewState = viewState,
                    action = action
                )
            }
        }

        eventScene?.invoke(this)
    }
}

@Composable
fun PersonalAccountTitle(
    modifier: Modifier = Modifier,
    viewState: PersonalAccountViewState,
    action: (PersonalAccountViewAction) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.sdp()),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            val m24dp = 24.sdp()
            val mEndConfig = remember(viewState.isVisitor, viewState.isVisible) {
                derivedStateOf {
                    if (viewState.isVisitor) {
                        null
                    } else {
                        TextWithIconConfig(
                            drawableRes = if (viewState.isVisible) {
                                R.drawable.ic_eye_alt
                            } else {
                                R.drawable.ic_eye_close
                            },
                            drawableSize = m24dp,
                            onClick = {
                                action.invoke(
                                    PersonalAccountViewAction.OnToggleVisibilityAction
                                )
                            }
                        )
                    }
                }
            }

            TextWithIcon(
                modifier = Modifier.padding(start = 2.sdp()),
                endConfig = mEndConfig.value,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PocketText(
                            config = PocketTextConfig(
                                value = "Hi, ",
                                textColor = Color.White,
                                style = text15Sp()
                            )
                        )
                        AnimatedPocketText(
                            config = PocketTextConfig(
                                value = viewState.accountName,
                                textColor = Color.White,
                                style = text15Sp(500)
                            )
                        )
                    }
                }
            )
        }

        if (viewState.isVisitor.not()) {
            Box(modifier = Modifier.pocketPadding(hv = (2 to null)),) {
                VerticalDivider(
                    modifier = Modifier.pocketPadding(hv = (null to 4)),
                    thickness = 1.sdp(),
                    color = color_414141
                )
            }

            PocketAutoSizeText(
                modifier = Modifier.weight(1f),
                config = PocketTextConfig(
                    value = "手續費${viewState.feeRate}${viewState.feeUnit}, 最低${viewState.minimum}${viewState.minimumUnit}",
                    style = text14Sp(),
                    textColor = color_c8c9ca,
                    maxLines = 1
                )
            )

            PocketSpacer(width = 2)

            TextWithIcon(
                modifier = Modifier.padding(end = 2.sdp()),
                endConfig = TextWithIconConfig(
                    drawableRes = R.drawable.lib_pocket_ic_qr_code,
                    drawableSize = 14.sdp(),
                    clickableConfig = rememberIconClickableConfig(iconSize = 10.sdp()),
                    onClick = {
                        action.invoke(
                            PersonalAccountViewAction.OnPromoteClickAction
                        )
                    }
                ),
                content = {
                    PocketText(
                        modifier = Modifier.clickableEffectConfig(
                            config = ClickableConfig(),
                            onClick = {
                                action.invoke(
                                    PersonalAccountViewAction.OnPromoteClickAction
                                )
                            }
                        ),
                        config = PocketTextConfig(
                            value = "開戶推薦碼",
                            textColor = color_fdd11a,
                            style = text13Sp(500)
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun CardHeader(
    viewState: PersonalAccountViewState,
    accountSelectScene: @Composable (RowScope.() -> Unit)? = null,
    action: (PersonalAccountViewAction) -> Unit
) {
    if (viewState.isVisitor.not()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            accountSelectScene?.invoke(this)

            PocketIconButton(
                drawableRes = R.drawable.lib_pocket_ic_refresh,
                iconSize = 24.sdp(),
                onIconClick = {
                    action.invoke(
                        PersonalAccountViewAction.OnRefreshAction
                    )
                }
            )
        }
    }

}

@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    viewState: PersonalAccountViewState
) {
    val mPercentage = if (viewState.isVisible) {
        viewState.remunerationValue
    } else {
        0f
    }

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(start = 10.sdp()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(35f),
            contentAlignment = Alignment.CenterStart
        ) {
            CircularPercentageComponent(
                modifier = Modifier.padding(
                    vertical = 6.sdp(),
                    horizontal = 8.sdp()
                ),
                config = CircularPercentageConfig(
                    radius = dimensionResource(id = com.intuit.sdp.R.dimen._61sdp) / 2,
                    percentage = mPercentage,
                    strokeWidth = dimensionResource(id = com.intuit.sdp.R.dimen._7sdp),
                    strokeColor = viewState.remunerationColor,
                    bg = color_9e9e9f
                ),
                centerContent = { event ->
                    val progressText = if (viewState.isVisitor.not()) {
                        if (viewState.isVisible) {
                            event.nowPercent.toNumberFormat(decimalPointFillZeroCount = 2) + "%"
                        } else {
                            TEXT_INVISIBLE
                        }
                    } else {
                        TEXT_NONE
                    }

                    val progressTextColor = if (viewState.isVisitor.not()) {
                        if (viewState.isVisible) {
                            viewState.remunerationColor
                        } else {
                            Color.White
                        }
                    } else {
                        Color.White
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PocketText(
                            config = PocketTextConfig(
                                value = "報酬率",
                                textColor = Color.White,
                                style = text13Sp()
                            )
                        )
                        PocketText(
                            config = PocketTextConfig(
                                value = progressText,
                                textColor = progressTextColor,
                                style = text13Sp()
                            )
                        )
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .weight(60f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            SummaryInfoField(viewState = viewState)
        }
    }
}

@Composable
private fun CardTrailing(
    modifier: Modifier = Modifier,
    viewState: PersonalAccountViewState,
    action: (PersonalAccountViewAction) -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        viewState.linkList.forEachIndexed { index, infoState ->
            PersonalAccountBottomItem(
                title = infoState.title,
                value = infoState.info,
                isVisible = viewState.isVisible,
                isVisitor = viewState.isVisitor,
                topSpacer = 8,
                bottomSpacer = 8,
                onClick = {
                    infoState.action?.let {
                        action.invoke(it)
                    }
                }
            )
            if (viewState.linkList.lastIndex != index) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.sdp()),
                    thickness = 1.sdp(),
                    color = color_414141
                )
            }
        }
    }
}

@Composable
private fun SummaryInfoField(
    modifier: Modifier = Modifier,
    viewState: PersonalAccountViewState
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        viewState.summaryList.forEachIndexed { index, infoState ->
            SummaryInfoItem(
                title = infoState.title,
                assets = infoState.info,
                assetsColor = infoState.color,
                isVisible = viewState.isVisible,
                isVisitor = viewState.isVisitor
            )
            if (viewState.summaryList.lastIndex != index) {
                PocketSpacer(height = 8)
            }
        }

    }
}

@Composable
private fun SummaryInfoItem(
    modifier: Modifier = Modifier,
    title: String,
    assets: String,
    assetsColor: Color = Color.White,
    isVisible: Boolean = true,
    isVisitor: Boolean = false
) {
    val mAssets = if (isVisitor) {
        TEXT_NONE
    } else {
        if (isVisible) {
            assets
        } else {
            TEXT_INVISIBLE_LONG
        }
    }

    val mAssetsColor = if (isVisitor || isVisible.not()) {
        Color.White
    } else {
        assetsColor
    }

    Row(
        modifier = modifier
            .heightIn(min = 22.sdp())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PocketText(
            modifier = Modifier,
            config = PocketTextConfig(
                value = title,
                style = text14Sp()
            )
        )
        PocketSpacer(width = 4)
        AnimatedPocketAutoSizeText(
            modifier = Modifier.heightIn(min = 22.sdp()),
            config = PocketTextConfig(
                value = mAssets,
                style = text15Sp(600),
                textColor = mAssetsColor,
                maxLines = 1,
                alignment = Alignment.CenterEnd,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun PersonalAccountBottomItem(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    title: String,
    value: String,
    isVisible: Boolean = true,
    isVisitor: Boolean = false,
    topSpacer: Int,
    bottomSpacer: Int,
    onClick: (() -> Unit)? = null
) {
    val displayValue = if (isVisitor) {
        TEXT_NONE
    } else {
        if (isVisible) {
            value
        } else {
            TEXT_INVISIBLE_LONG
        }
    }

    Column(
        modifier = modifier.clickableEffectConfig(
            config = ClickableConfig(
                needRipple = onClick != null,
                needSound = false,
            ),
            onClick = {
                onClick?.invoke()
            }
        )
    ) {
        PocketSpacer(height = topSpacer)
        Row(
            modifier = rowModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PocketText(
                modifier = Modifier,
                config = PocketTextConfig(
                    value = title,
                    style = text13Sp(),
                    alignment = Alignment.CenterStart
                )
            )
            PocketSpacer(width = 8)
            AnimatedPocketAutoSizeText(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .fillMaxWidth(),
                config = PocketTextConfig(
                    value = displayValue,
                    style = text15Sp(600),
                    alignment = Alignment.CenterStart,
                    textAlign = TextAlign.Start
                )
            )
            PocketSpacer(width = 12)

            onClick?.let {
                PocketDetailButton(
                    modifier = Modifier
                        .widthIn(min = 68.sdp())
                        .heightIn(min = 22.sdp()),
                    textConfig = PocketTextConfig(
                        value = "看詳情",
                        style = text13Sp()
                    ),
                    onClick = it
                )
            }
        }
        PocketSpacer(height = bottomSpacer)
    }
}