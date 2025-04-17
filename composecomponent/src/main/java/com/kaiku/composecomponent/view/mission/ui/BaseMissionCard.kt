package com.kaiku.composecomponent.view.mission.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.color_c8c9ca
import com.kaiku.composecomponent.component.button.PocketRotationIconButton
import com.kaiku.composecomponent.component.image.PocketAsyncImage
import com.kaiku.composecomponent.component.image.PocketAsyncImageConfig
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickableEffect
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionCategory
import com.kaiku.composecomponent.view.mission.data.MissionReward
import com.kaiku.composecomponent.view.mission.data.MissionStringRes
import com.kaiku.composecomponent.view.mission.ui.dim.DefaultDimContent
import com.kaiku.composecomponent.view.mission.ui.dim.DimContentWhenMissionFinished

internal data class MissionCardAnimationConfig(
    val isVisible: Boolean = true,
    val slideInOffset: Dp = 0.dp,
    val aniSpec: AnimationSpec<Dp> = tween(700)
) {
    companion object {
        val PREVIEW = MissionCardAnimationConfig(
            isVisible = true,
            slideInOffset = 0.dp
        )
    }
}

/**
 * @property cardState 保存於viewModel裡面的任務卡片狀態
 * @property cardContent 卡片內容
 * @property dimContent 任務完成或結束時，顯示之灰階畫面
 * @property aniConfig 控制卡片絲滑進出的動畫設定
 * @property onExpandedClick export 卡片展開事件
 * @property onRewardClick export 使用者點擊「領取獎勵」
 */
@Composable
internal fun BaseMissionCard(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    cardState: MissionCardState,
    cardContent: @Composable () -> Unit = {},
    dimContent: @Composable () -> Unit = { DefaultDimContent() },
    aniConfig: MissionCardAnimationConfig = MissionCardAnimationConfig(),
    onExpandedClick: () -> Unit = {},
    onRewardClick: (MissionCardState) -> Unit = {}
) {
    var offsetX by remember { mutableStateOf(aniConfig.slideInOffset) }
    val offsetAnimation = animateDpAsState(
        targetValue = offsetX,
        animationSpec = aniConfig.aniSpec,
        label = "offsetAnimation"
    ).value

    LaunchedEffect(aniConfig.isVisible) {
        if (aniConfig.isVisible) {
            offsetX = 0.dp
        }
    }

    Card(
        modifier = modifier
            .padding(horizontal = 16.sdp(), vertical = 6.sdp())
            .offset(x = offsetAnimation),
        shape = RoundedCornerShape(4.sdp()),
        colors = CardDefaults.cardColors(
            containerColor = color_333333
        )
    ) {
        ConstraintLayout {
            val (mCardContent, mDimContent) = createRefs()
            Column(
                modifier = Modifier
//                    .animateContentSize()
                    .constrainAs(mCardContent) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                CardTitle(
                    isShowDetail = cardState.isExpanded,
                    cardState = cardState,
                    onClick = {
                        onExpandedClick.invoke()
                    }
                )

                AnimatedVisibility(visible = cardState.isExpanded) {
                    Column {
                        HorizontalDivider(color = color_414141)
                        cardContent()

                        if (cardState.isShowDim.not()) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 12.sdp()),
                                color = color_414141
                            )
                            ConfirmField(
                                stringRes = stringRes,
                                cardState = cardState,
                                onRewardClick = {
                                    onRewardClick(cardState)
                                }
                            )
                        }

                    }
                }
            }

            if (cardState.isShowDim) {
                Box(
                    modifier = Modifier
                        .constrainAs(mDimContent) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                        .background(Color.Black.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .padding(vertical = 5.sdp()),
                        contentAlignment = Alignment.Center
                    ) {
                        dimContent.invoke()
                    }
                }
            }
        }
    }


}

@Composable
private fun CardTitle(
    modifier: Modifier = Modifier,
    isShowDetail: Boolean = false,
    cardState: MissionCardState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.sdp(), vertical = 8.sdp())
    ) {
        PocketAsyncImage(
            modifier = Modifier
                .size(40.sdp())
                .clip(RoundedCornerShape(4.sdp())),
            imageConfig = PocketAsyncImageConfig(
                url = cardState.imageUrl,
                errorDrawable = if (cardState.isStepMission()) {
                    if (cardState.isTw) {
                        R.drawable.pocket_ic_mission_gift_with_bg_tw
                    } else {
                        R.drawable.pocket_ic_mission_gift_with_bg_us
                    }
                } else {
                    R.drawable.pocket_ic_mission_coupon_with_bg
                },
                shape = RoundedCornerShape(4.sdp())
            ),
            clickConfig = ClickableConfig(
                needRipple = false,
                needSound = false,
            )
        )
        Spacer(modifier = Modifier.width(12.sdp()))

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val dynamicEnable = !cardState.isShowDim && !cardState.isStepMission()
                val dynamicMaxLines = if (cardState.isExpanded) {
                    Int.MAX_VALUE
                } else {
                    1
                }
                val dynamicStyle = if (dynamicMaxLines == 1) {
                    text17Sp(500)
                } else {
                    text17Sp(500).copy(
                        lineHeight = dimensionResource(id = com.intuit.ssp.R.dimen._18ssp).value.sp
                    )
                }
                PocketText(
                    modifier = Modifier
                        .weight(1f)
                        .clickableEffectConfig(
                            config = ClickableConfig(
                                needRipple = dynamicEnable,
                                needSound = dynamicEnable,
                            ),
                            onClick = {
                                if (dynamicEnable) {
                                    onClick.invoke()
                                }
                            }
                        ),
                    config = PocketTextConfig(
                        value = cardState.title,
                        style = dynamicStyle,
                        alignment = Alignment.CenterStart,
                        maxLines = dynamicMaxLines
                    )
                )
                if (cardState.isStepMission().not()) {
                    PocketRotationIconButton(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = Color.White,
                        iconSize = 24.sdp(),
                        isExpand = isShowDetail,
                        clickableConfig = ClickableConfig(
                            needRipple = !cardState.isShowDim,
                            needSound = !cardState.isShowDim,
                        ),
                        onIconClick = {
                            if (dynamicEnable) {
                                onClick.invoke()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.sdp()))

            AnimatedVisibility(visible = isShowDetail) {
                Column {
                    PocketText(
                        modifier = Modifier.padding(
                            end = if (cardState.isStepMission()) {
                                0.dp
                            } else {
                                24.sdp()
                            }
                        ),
                        config = PocketTextConfig(
                            value = cardState.description,
                            style = text14Sp().copy(
                                lineHeight = dimensionResource(id = com.intuit.ssp.R.dimen._15ssp).value.sp
                            ),
                            textColor = color_c8c9ca,
                            maxLines = Int.MAX_VALUE
                        )
                    )
                    Spacer(modifier = Modifier.height(4.sdp()))
                }
            }

            PocketText(
                modifier = Modifier.height(17.sdp()),
                config = PocketTextConfig(
                    value = cardState.validTime,
                    style = text13Sp(),
                    textColor = color_c8c9ca,
                    maxLines = 1
                )
            )
        }

    }
}

@Composable
private fun ConfirmField(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    cardState: MissionCardState,
    onRewardClick: () -> Unit = {}
) {
    val isMgm = cardState.missionCategory == MissionCategory.MGM
    val isRewardReachable = cardState.isRewardReachable
    val bgColor = remember(isRewardReachable) {
        if (isRewardReachable) {
            cardState.getPrimaryColor()
        } else {
            color_9e9e9f
        }
    }

    val text = remember(isRewardReachable) {
        if (isRewardReachable) {
            stringRes.missionListReceiveRewardButton
        } else {
            stringRes.missionListNotReceiveRewardButton
        }
    }

    Row(
        modifier = modifier
            .padding(horizontal = 12.sdp(), vertical = 8.sdp())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMgm) {
            val achieveTargetCount = cardState.rewards.firstOrNull()?.achieveCount.toNumberFormat(
                invalidText = "--"
            )

            val displayValue = cardState.targets.firstOrNull()?.limitMax?.let { limit ->
                "$achieveTargetCount/$limit"
            } ?: achieveTargetCount

            PocketText(
                config = PocketTextConfig(
                    value = "${stringRes.missionMgmLimitFieldTitle}：$displayValue",
                    style = text13Sp(),
                    maxLines = 1
                )
            )
        } else {
            Box{}
        }

        PocketTextWithClickableEffect(
            modifier = Modifier
                .widthIn(min = 80.sdp())
                .heightIn(min = 25.sdp()),
            shape = RoundedCornerShape(6.sdp()),
            color = bgColor,
            config = PocketTextConfig(
                value = text,
                style = text13Sp(),
                isEnable = isRewardReachable
            ),
            onClick = {
                if (isRewardReachable) {
                    onRewardClick.invoke()
                }
            }
        )
    }
}

private class BaseMissionCardParamProvider : PreviewParameterProvider<MissionCardState> {
    val defaultState = MissionCardState(
        missionCategory = MissionCategory.NORMAL,
        imageUrl = "imageUrl",
        isShowDim = false,
        isRewardReachable = false,
        isExpanded = true,
        title = "任務標題任務標題任務標題任務標題任務標題任務標題任務標題任務標題任務標題任務標題任務標題任務標題",
        description = "任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務"
    )

    val completed = MissionCardState(
        isShowDim = true,
        rewards = MissionReward.PREVIEW_STEP
    )
    override val values: Sequence<MissionCardState>
        get() = sequenceOf(defaultState, completed)
}

@Preview
@Composable
private fun BaseMissionCardPreview(
    @PreviewParameter(BaseMissionCardParamProvider::class)
    cardState: MissionCardState
) {
    BaseMissionCard(
        stringRes = MissionStringRes.Default,
        cardState = cardState,
        cardContent = {
            PocketText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                config = PocketTextConfig(
                    value = "卡片內容"
                )
            )
        },
        dimContent = {
            DimContentWhenMissionFinished(
                date = "2021/10/10",
                stringRes = MissionStringRes.Default,
                cardState = cardState
            )
//            DimContent(cardState = cardState)
        },
        aniConfig = MissionCardAnimationConfig()
    )
}