package com.kaiku.composecomponent.view.mgm

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_292929
import com.kaiku.composecomponent.color_3e3a39
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.image.PocketAsyncImage
import com.kaiku.composecomponent.component.image.PocketAsyncImageConfig
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithBottomLine
import com.kaiku.composecomponent.component.topbar.PocketAppBarWithBackNavigation
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.utils.text19Sp
import com.kaiku.composecomponent.view.mgm.data.MgmStringRes
import com.kaiku.composecomponent.view.mgm.data.MgmViewAction
import com.kaiku.composecomponent.view.mgm.data.MgmViewState

@Composable
fun BaseMgmScreen(
    modifier: Modifier = Modifier,
    stringRes: MgmStringRes = MgmStringRes.DEFAULT,
    viewState: MgmViewState,
    action: (MgmViewAction) -> Unit = {}
) {
    Scaffold(
        topBar = {
            PocketAppBarWithBackNavigation(
                modifier = Modifier.height(44.sdp()),
                title = stringRes.topBarTitleText,
                background = viewState.topBarBackground,
                onBackClick = {
                    action.invoke(MgmViewAction.CloseAction)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            PocketAsyncImage(
                imageConfig = PocketAsyncImageConfig(
                    url = viewState.bannerUrl,
                    ratio = 375 / 100f,
                    shape = RoundedCornerShape(0.dp),
                    contentScale = ContentScale.FillWidth
                )
            )

            PocketSpacer(height = 12)

            TitleContent(
                res = stringRes,
                viewState = viewState,
                onGotoPocketEvent = {
                    action.invoke(MgmViewAction.GotoPocketEventAction)
                }
            )

            PocketSpacer(height = 12)

            QrCodeContent(
                res = stringRes,
                viewState = viewState,
                onCopyCode = {
                    action.invoke(MgmViewAction.CopyCodeAction)
                },
                onShareLink = {
                    action.invoke(MgmViewAction.ShareLinkAction)
                }
            )

            PocketSpacer(height = 12)

            GotoMissionContent(
                res = stringRes,
                onGotoMission = {
                    action.invoke(MgmViewAction.GotoMissionAction)
                }
            )
        }
    }

}

@Composable
private fun TitleContent(
    modifier: Modifier = Modifier,
    res: MgmStringRes,
    viewState: MgmViewState,
    onGotoPocketEvent: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.sdp())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.sdp())
                    .clip(RoundedCornerShape(1.sdp()))
                    .background(Color(0xFFD9D9D9))
            )
            PocketSpacer(width = 8)
            PocketText(
                config = PocketTextConfig(
                    value = viewState.missionTitle,
                    style = text17Sp(500),
                    maxLines = 2
                )
            )
        }
        Spacer(modifier = Modifier.height(4.sdp()))
        PocketText(
            config = PocketTextConfig(
                value = viewState.missionContent,
                style = text15Sp(),
                maxLines = Int.MAX_VALUE,
                lineHeight = dimensionResource(id = com.intuit.sdp.R.dimen._16sdp).value.sp
            )
        )
        Spacer(modifier = Modifier.height(4.sdp()))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PocketTextWithBottomLine(
                config = PocketTextConfig(
                    value = res.pocketEventDetailText,
                    style = text13Sp(),
                    textColor = color_9e9e9f,
                    maxLines = 1
                ),
                onClick = onGotoPocketEvent
            )
            PocketIconButton(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                iconSize = 20.sdp(),
                tint = color_9e9e9f,
                clickableConfig = ClickableConfig(),
                onIconClick = onGotoPocketEvent
            )
        }
    }
}


@Composable
private fun QrCodeContent(
    modifier: Modifier = Modifier,
    res: MgmStringRes,
    viewState: MgmViewState,
    onCopyCode: () -> Unit,
    onShareLink: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.sdp()),
        shape = RoundedCornerShape(10.sdp()),
        border = BorderStroke(1.sdp(), color_3e3a39),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 21.sdp(),
                    vertical = 16.sdp()
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(130.sdp()),
                    color = Color.Transparent,
                    shape = RoundedCornerShape(10.sdp()),
                    border = BorderStroke(
                        width = 1.sdp() / 2,
                        color = Color.Transparent
                    )
                ) {
                    Crossfade(
                        targetState = viewState.qrCode.asImageBitmap(),
                        label = ""
                    ) { bitmap ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            bitmap = bitmap,
                            contentDescription = ""
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.sdp()))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    PocketText(
                        config = PocketTextConfig(
                            value = res.promoteTitleText,
                            style = text15Sp(),
                            maxLines = 1
                        )
                    )

                    PocketSpacer(height = 4)

                    PocketPrimaryButton(
                        modifier = modifier.fillMaxWidth(),
                        height = 40.sdp(),
                        border = BorderStroke(1.sdp(), color_3e3a39),
                        primaryColor = color_292929,
                        contentPadding = PaddingValues(0.dp),
                        onClick = onCopyCode,
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.sdp()),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                PocketText(
                                    config = PocketTextConfig(
                                        value = viewState.code,
                                        style = text19Sp(500),
                                        maxLines = 1
                                    )
                                )

                                PocketIconButton(
                                    drawableRes = R.drawable.pocket_ic_copy,
                                    iconSize = 20.sdp(),
                                    tint = Color.White,
                                    onIconClick = onCopyCode
                                )
                            }
                        }
                    )

                    PocketSpacer(height = 12)

                    PocketPrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        config = PocketTextConfig(
                            value = res.shareBtnText,
                            style = text15Sp(500),
                            maxLines = 1
                        ),
                        height = 40.sdp(),
                        onClick = onShareLink
                    )
                }
            }
        }

        HorizontalDivider(
            thickness = 1.sdp(),
            color = color_3e3a39
        )

        Row(
            modifier = Modifier
                .height(79.sdp())
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MgmBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 16.sdp(),
                        end = 12.sdp(),
                        top = 14.sdp(),
                        bottom = 14.sdp()
                    ),
                contentConfig = PocketTextConfig(
                    value = "${viewState.countTw} 名",
                    style = text17Sp(),
                    alignment = Alignment.BottomCenter,
                    maxLines = 1
                ),
                hintConfig = PocketTextConfig(
                    value = res.twMgmCountText,
                    style = text13Sp(),
                    alignment = Alignment.TopCenter,
                    maxLines = 1
                )
            )
            VerticalDivider(
                modifier = Modifier.padding(vertical = 12.sdp()),
                thickness = 1.sdp(),
                color = color_3e3a39
            )
            MgmBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 12.sdp(),
                        end = 16.sdp(),
                        top = 14.sdp(),
                        bottom = 14.sdp()
                    ),
                contentConfig = PocketTextConfig(
                    value = "${viewState.countSb} 名",
                    style = text17Sp(),
                    alignment = Alignment.BottomCenter,
                    maxLines = 1
                ),
                hintConfig = PocketTextConfig(
                    value = res.sbMgmCountText,
                    style = text13Sp(),
                    alignment = Alignment.TopCenter,
                    maxLines = 1
                ),
            )
        }
    }
}

@Composable
private fun GotoMissionContent(
    modifier: Modifier = Modifier,
    res: MgmStringRes,
    onGotoMission: () -> Unit
) {
    PocketPrimaryButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.sdp()),
        height = 40.sdp(),
        border = BorderStroke(1.sdp(), color_3e3a39),
        primaryColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
        onClick = onGotoMission,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.sdp()),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PocketText(
                    config = PocketTextConfig(
                        value = res.gotoMissionBtnText,
                        style = text15Sp(500),
                        maxLines = 1
                    )
                )

                PocketIconButton(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    iconSize = 24.sdp(),
                    tint = Color.White,
                    clickableConfig = ClickableConfig(),
                    onIconClick = onGotoMission
                )
            }
        }
    )
}

@Composable
private fun MgmBottomItem(
    modifier: Modifier = Modifier,
    contentConfig: PocketTextConfig,
    hintConfig: PocketTextConfig
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PocketText(
            modifier = Modifier.weight(1f),
            config = contentConfig
        )
        PocketSpacer(height = 8)
        PocketText(
            modifier = Modifier.weight(1f),
            config = hintConfig
        )
    }
}


