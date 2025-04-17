package com.kaiku.composecomponent.view.mission.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_discount_bg
import com.kaiku.composecomponent.color_gift_bg_tw
import com.kaiku.composecomponent.component.dialog.PocketComposeDialog
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionCategory
import com.kaiku.composecomponent.view.mission.data.MissionReward
import com.kaiku.composecomponent.view.mission.data.MissionStringRes
import com.kaiku.composecomponent.view.mission.data.MissionTarget

@Composable
fun ReceivedRewardDialog(
    times: Int = 1, // 累加任務才會>1
    ids: List<String>,
    stringRes: MissionStringRes = MissionStringRes.Default,
    cardState: MissionCardState,
    onCheckMyRewards: () -> Unit,
    onDismiss: () -> Unit
) {
    val mPrimaryColor = rememberUpdatedState(newValue = cardState.getPrimaryColor())
    val receivedRewards = cardState.rewards.filter {
        it.rewardId in ids
    }

    val rewardDescription = cardState.lastApportionTime?.let { time ->
        "獎勵將於 $time 前發送"
    } ?: stringRes.missionReceiveRewardDialogContentEnd

    PocketComposeDialog(
        primaryColor = mPrimaryColor.value,
        title = stringRes.missionReceiveRewardDialogTitle,
        negativeText = "關閉",
        onNegativeClick = { onDismiss.invoke() },
        negativeButton = {
            PocketTextWithClickEffect(
                modifier = Modifier
                    .height(35.sdp())
                    .clip(RoundedCornerShape(8.sdp()))
                    .background(Color.Transparent)
                    .border(1.sdp(), mPrimaryColor.value, RoundedCornerShape(8.sdp()))
                    .weight(1f),
                config = PocketTextConfig(
                    value = "關閉",
                    style = text17Sp(500),
                    textColor = mPrimaryColor.value,
                    maxLines = 1
                ),
                onClick = {
                    onDismiss.invoke()
                }
            )
        },
        onPositiveClick = {
            onCheckMyRewards.invoke()
        },
        positiveButton = {
            PocketTextWithClickEffect(
                modifier = Modifier
                    .height(35.sdp())
                    .clip(RoundedCornerShape(8.sdp()))
                    .background(cardState.getPrimaryColor())
                    .weight(1f),
                config = PocketTextConfig(
                    value = stringRes.missionReceiveRewardDialogContentButton,
                    style = text17Sp(500),
                    maxLines = 1
                ),
                onClick = {
                    onCheckMyRewards.invoke()
                }
            )
        },
        contentPaddingVertical = 28,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(60.sdp())
                        .clip(CircleShape)
                        .background(cardState.dialogImageBg),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(40.sdp()),
                        painter = painterResource(id = cardState.dialogImageId),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(12.sdp()))
                PocketText(
                    config = PocketTextConfig(
                        value = stringRes.missionReceiveRewardDialogContentTitle,
                        style = text15Sp()
                    )
                )
                Spacer(modifier = Modifier.height(16.sdp()))
                receivedRewards.forEach { reward ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PocketText(
                                modifier = Modifier.weight(1f, false),
                                config = PocketTextConfig(
                                    value = "${stringRes.missionReceiveRewardDialogContentGet}【${reward.name}】",
                                    style = text15Sp(),
                                    maxLines = Int.MAX_VALUE
                                )
                            )
                            Spacer(modifier = Modifier.width(20.sdp()))
                            if(times > 1) {
                                PocketText(
                                    config = PocketTextConfig(
                                        value = "${reward.number}\t${reward.unit}\tX\t$times",
                                        style = text15Sp(),
                                    )
                                )
                            } else {
                                PocketText(
                                    config = PocketTextConfig(
                                        value = "${reward.number}\t${reward.unit}",
                                        style = text15Sp(),
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.sdp()))
                        HorizontalDivider(
                            thickness = 1.sdp(),
                            color = color_414141
                        )
                        Spacer(modifier = Modifier.height(8.sdp()))
                    }
                }
                Spacer(modifier = Modifier.height(8.sdp()))
                PocketText(
                    config = PocketTextConfig(
                        value = rewardDescription,
                        style = text15Sp()
                    )
                )
                Spacer(modifier = Modifier.height(28.sdp()))
            }
        }
    )
}





private class ReceivedRewardDialogProvider : PreviewParameterProvider<MissionCardState> {
    val step = MissionCardState(
        missionCategory = MissionCategory.STEP,
        dialogImageId = R.drawable.pocket_ic_mission_gift,
        dialogImageBg = color_gift_bg_tw,
        targets = MissionTarget.PREVIEW_STEP,
        rewards = MissionReward.PREVIEW_STEP
    )

    val normal = MissionCardState(
        missionCategory = MissionCategory.NORMAL,
        dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
        dialogImageBg = color_discount_bg,
        targets = MissionTarget.PREVIEW_NORMAL,
        rewards = MissionReward.PREVIEW_NORMAL_CAN_RECEIVED,
        lastApportionTime = "2024/12/31"
    )

    override val values: Sequence<MissionCardState>
        get() = sequenceOf(step, normal)
}
@Preview
@Composable
private fun ReceivedRewardDialogPreview(
    @PreviewParameter(ReceivedRewardDialogProvider::class) state: MissionCardState
) {
    ReceivedRewardDialog(
        times = 1,
        cardState = state,
        onCheckMyRewards = {},
        ids = listOf("1", "2"),
        onDismiss = {}
    )
}
