package com.kaiku.composecomponent.view.mission.data

sealed class MissionViewAction {

    // 動畫onFinish的時候會call這個function
    data class AnimateCouponAction(
        val index: Int,
        val runState: Boolean
    ) : MissionViewAction()

    // 任務卡片展開
    data class MissionCardExpandAction(
        val index: Int
    ) : MissionViewAction()

    // 任務卡片滑進動畫
    data class MissionCardVisibleAction(
        val index: Int,
        val isVisible: Boolean
    ) : MissionViewAction()

    // 點擊領取獎勵
    data class OnRewardClickAction(
        val index: Int,
        val missionCardState: MissionCardState
    ) : MissionViewAction()

    // 點擊前往我的優惠
    data class OnGotoMyCouponAction(
        val couponType: String = CouponState.TYPE_DISCOUNT
    ) : MissionViewAction()

    // 關閉任務獎勵dialog
    data object WhenRewardDismissAction : MissionViewAction()

}