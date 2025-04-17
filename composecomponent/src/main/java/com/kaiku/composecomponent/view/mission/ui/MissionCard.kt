package com.kaiku.composecomponent.view.mission.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_discount_bg
import com.kaiku.composecomponent.color_gift_bg_tw
import com.kaiku.composecomponent.view.mission.data.CouponState
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionCategory
import com.kaiku.composecomponent.view.mission.data.MissionReward
import com.kaiku.composecomponent.view.mission.data.MissionStringRes
import com.kaiku.composecomponent.view.mission.data.MissionTarget
import com.kaiku.composecomponent.view.mission.ui.cardcontent.MgmMissionCardContent
import com.kaiku.composecomponent.view.mission.ui.cardcontent.NormalMissionCardContent
import com.kaiku.composecomponent.view.mission.ui.cardcontent.StepMissionCardContent
import com.kaiku.composecomponent.view.mission.ui.dim.DimContent
import com.kaiku.composecomponent.view.mission.ui.dim.DimContentWhenMissionFinished

/**
 * 基本的任務卡片元件
 *
 * @property cardState 保存於viewModel裡面的任務卡片狀態
 * @property aniConfig 控制卡片絲滑的進出動畫設定
 * @param onExpandedClick export 卡片展開事件
 * @property onRewardClick export 使用者點擊「領取獎勵」
 * @property onGotoMyCoupon export 使用者點擊「前往我的獎勵」
 */
@Composable
internal fun MissionCard(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    cardState: MissionCardState,
    aniConfig: MissionCardAnimationConfig,
    onExpandedClick: () -> Unit = {},
    onRewardClick: (MissionCardState) -> Unit = {},
    onGotoMyCoupon: (String) -> Unit = {}
) {
    BaseMissionCard(
        modifier = modifier,
        stringRes = stringRes,
        cardState = cardState,
        cardContent = {
            when(cardState.missionCategory) {
                MissionCategory.STEP -> StepMissionCardContent(cardState = cardState)
                MissionCategory.NORMAL -> NormalMissionCardContent(stringRes = stringRes, cardState = cardState)
                MissionCategory.MGM -> MgmMissionCardContent(stringRes = stringRes, cardState = cardState)
                MissionCategory.UNKNOWN -> Unit
            }
        },
        dimContent = {
            cardState.lastApportionTime?.let { lastApportionTime ->
                DimContentWhenMissionFinished(
                    date = lastApportionTime,
                    stringRes = stringRes,
                    cardState = cardState
                )
            } ?: run {
                DimContent(
                    isTw = cardState.isTw,
                    stringRes = stringRes,
                    onGotoMyCoupon = {
                        onGotoMyCoupon.invoke(cardState.couponType)
                    }
                )
            }
        },
        aniConfig = aniConfig,
        onExpandedClick = onExpandedClick,
        onRewardClick = onRewardClick
    )
}

private class ParamProvider : PreviewParameterProvider<MissionCardState> {
    val step = MissionCardState(
        missionCategory = MissionCategory.STEP,
        imageUrl = "fakeStepImageUrl",
        dialogImageId = R.drawable.pocket_ic_mission_gift,
        dialogImageBg = color_gift_bg_tw,
        targets = MissionTarget.PREVIEW_STEP2,
        rewards = MissionReward.PREVIEW_STEP
    )

    val normal = MissionCardState(
        missionCategory = MissionCategory.NORMAL,
        imageUrl = "fakeNormalImageUrl",
        targets = MissionTarget.PREVIEW_NORMAL,
        rewards = MissionReward.PREVIEW_NORMAL
    )

    val mgm = MissionCardState(
        missionCategory = MissionCategory.MGM,
        imageUrl = "fakeMgmImageUrl",
        dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
        dialogImageBg = color_discount_bg,
        mgmCount = 10,
        targets = MissionTarget.PREVIEW_MGM,
        rewards = MissionReward.PREVIEW_MGM
    )

    val mgm2 =  MissionCardState(
        missionCategory = MissionCategory.MGM,
        imageUrl = "fakeMgm2ImageUrl",
        dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
        dialogImageBg = color_discount_bg,
        mgmCount = 10,
        targets = MissionTarget.PREVIEW_MGM2,
        rewards = MissionReward.PREVIEW_MGM
    )

    override val values: Sequence<MissionCardState>
        get() = sequenceOf(step, normal, mgm, mgm2)
}

@Preview
@Composable
private fun MissionCardPreview(
    @PreviewParameter(ParamProvider::class) cardState: MissionCardState
) {
    MissionCard(
        stringRes = MissionStringRes.Default,
        cardState = cardState,
        aniConfig = MissionCardAnimationConfig.PREVIEW,
    )
}