package com.kaiku.composecomponent.view.mission.data

data class MissionStringRes(
    var missionTopBarTitle: String = "口袋任務",
    var missionDiscount: String = "可用折抵金",
    var missionRaffle: String = "可用抽獎券",
    var missionExchange: String = "可用兌換券",
    var missionEmptyList: String = "口袋任務不定期更新\n目前尚無新任務",
    var missionCheckRewardDialogTitle: String = "任務獎勵",
    var missionCheckRewardDialogContentStart: String = "此任務獎勵為",
    var missionCheckRewardDialogContentEnd: String = "請繼續完成任務",
    var missionReceiveRewardDialogTitle: String = "領取獎勵",
    var missionReceiveRewardDialogContentTitle: String = "恭喜您完成任務！",
    var missionReceiveRewardDialogContentGet: String = "獲得",
    var missionReceiveRewardDialogContentEnd: String = "請至我的優惠查看獎勵",
    var missionReceiveRewardDialogContentButton: String = "前往我的優惠",
    var missionListReward: String = "任務獎勵",
    var missionListReceiveRewardButton: String = "領取獎勵",
    var missionListNotReceiveRewardButton: String = "尚未達成",
    var missionCompleted: String = "任務達成",
    var missionEventEnd: String = "任務活動結束",
    var missionCompletedSendCoupon: String = "我的優惠",
    var missionCompletedSendBeforeDate: String = "獎勵將於 %1s 前發放完畢",
    var missionMgmMatchCountTitle: String = "符合條件人數",
    var missionMgmAchieveCountTitle: String = "達成目標人數",
    var missionMgmReceivedCountTitle: String = "已領獎勵",
    var missionMgmLimitFieldTitle: String = "達成次數"
) {
    companion object {
        val Default = MissionStringRes()
    }
}
