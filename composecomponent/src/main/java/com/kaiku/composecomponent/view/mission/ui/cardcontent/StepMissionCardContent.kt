package com.kaiku.composecomponent.view.mission.ui.cardcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionReward
import com.kaiku.composecomponent.view.mission.data.RewardStatus
import com.kaiku.composecomponent.view.mission.ui.CheckRewardDialog
import com.kaiku.composecomponent.view.mission.ui.DiffRatioHorizontalProgress

@Composable
internal fun StepMissionCardContent(
    modifier: Modifier = Modifier,
    cardState: MissionCardState
) {
    // 獲得目前進度
    val now = cardState.targets.firstOrNull()?.nowStep.toNumberFormat(
        isShowThousandsSign = true,
        invalidText = "--"
    )
    val intervalEnd = findIntervalEnd(cardState).toNumberFormat(
        isShowThousandsSign = true,
        invalidText = "--"
    )

    // 獲得獎勵階層
    val rewards = cardState.rewards
    val currentStep = cardState.targets.firstOrNull()?.nowStep ?: 0
    val stepList = rewards.map {
        it.step?.toInt() ?: -1
    }
    // 獎勵狀態
    val rewardStatus = rewards.map {
        it.status
    }

    var mReward by remember { mutableStateOf<MissionReward?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(horizontal = 12.sdp(), vertical = 8.sdp())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PocketText(
                config = PocketTextConfig(
                    value = "目前達成：",
                    style = text13Sp(),
                )
            )
            PocketText(
                config = PocketTextConfig(
                    value = now,
                    style = text13Sp(),
                    textColor = Color.White,
                )
            )
            PocketText(
                config = PocketTextConfig(
                    value = "\t/  ",
                    style = text13Sp(),
                )
            )
            PocketText(
                config = PocketTextConfig(
                    value = intervalEnd,
                    style = text13Sp(),
                    textColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(8.sdp()))
        PocketText(
            modifier = Modifier.height(18.sdp()),
            config = PocketTextConfig(
                value = "※ ${cardState.rewardDescription}",
                style = text13Sp(),
                textColor = color_9e9e9f
            )
        )
        Spacer(modifier = Modifier.height(10.sdp()))
        DiffRatioHorizontalProgress(
            modifier = Modifier.background(color_333333),
            isTw = cardState.isTw,
            mascotUrl = cardState.mascotUrl,
            stepList = listOf(0) + stepList,
            receivedStatusList = listOf(RewardStatus.UNKNOWN) + rewardStatus,
            currentStep = currentStep,
            onClick = { step ->
                mReward = cardState.rewards.find {
                    it.step == step
                }
                isDialogVisible = true
            }
        )

        if (mReward != null && isDialogVisible) {
            CheckRewardDialog(
                cardState = cardState,
                reward = mReward!!,
                onDismiss = {
                    isDialogVisible = false
                }
            )
        }
    }
}

private fun findIntervalEnd(state: MissionCardState): Int? {
    val errorCode = -1
    val stepList = state.targets.map { it.totalStep }
    val currentStep = state.targets.firstOrNull()?.nowStep ?: errorCode
    val firstStep = state.targets.firstOrNull()?.totalStep ?: errorCode
    val lastStep = state.targets.lastOrNull()?.totalStep ?: errorCode
    if (currentStep != errorCode) {
        for (i in 0 until stepList.size - 1) {
            val start = stepList[i]
            val end = stepList[i + 1]

            if (currentStep in start..end) {
                return end
            }
        }
    }

    if (firstStep != errorCode && currentStep != errorCode && currentStep < firstStep) {
        return firstStep
    }

    if (lastStep != errorCode && currentStep != errorCode && currentStep > lastStep) {
        return lastStep
    }

    return null
}
